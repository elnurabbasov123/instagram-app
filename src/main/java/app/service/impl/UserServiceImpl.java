package app.service.impl;

import app.mapper.UserMapper;
import app.model.dto.request.AuthenticationRequestDto;
import app.model.dto.request.PasswordRequestDto;
import app.model.dto.request.UserRequestDto;
import app.model.dto.response.MessageResponseDto;
import app.model.dto.response.TokenResponseDto;
import app.model.dto.response.UserResponseDto;
import app.model.entity.*;
import app.model.enums.Exceptions;
import app.model.enums.Message;
import app.model.enums.TokenType;
import app.model.exception.ApplicationException;
import app.model.exception.NotFoundException;
import app.model.helper.UserServiceHelper;
import app.repository.FollowersRepository;
import app.repository.FollowingsRepository;
import app.repository.UserRepository;
import app.service.MailService;
import app.service.TokenService;
import app.service.UserService;
import app.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final MailService mailService;
    private final UserServiceHelper userServiceHelper;
    private final FollowersRepository followersRepository;
    private final FollowingsRepository followingsRepository;
    private final MessageUtil messageUtil;

    @Override
    @Transactional
    public ResponseEntity<MessageResponseDto> register(UserRequestDto request) {
        User user = userMapper.map(request);
        user.setPassword(userServiceHelper.encode(user.getPassword()));
        UserSituation userSituation = UserSituation.builder().build();
        UserDetails userDetails = UserDetails.builder().build();
        user.setUserSituation(userSituation);
        user.setUserDetails(userDetails);
        userRepository.save(user);


        Token token = tokenService.generateToken(TokenType.CONFIRMATION);
        token.setUser(user);
        tokenService.save(token);


        mailService.createAndSendToMailForConfirmation(user, token.getName());
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message(messageUtil.getMessage(Message.REGISTER_SUCCESSFULLY_MESSAGE.getMessage()))
                .build(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<TokenResponseDto> authentication(AuthenticationRequestDto request) {

        User user = userRepository.getByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ApplicationException(Exceptions.USERNAME_OR_PASSWORD_IS_WRONG_EXCEPTION));

        boolean matches = userServiceHelper.matches(request.getPassword(), user.getPassword());

        if (!matches){
            throw new ApplicationException(Exceptions.USERNAME_OR_PASSWORD_IS_WRONG_EXCEPTION);
        }

        tokenService.deleteAllTokenByType(user, TokenType.ACCESS);

        Token token = tokenService.generateToken(TokenType.ACCESS);

        token.setUser(user);
        tokenService.save(token);

        TokenResponseDto tokenResponseDto = userServiceHelper.buildTokenResponseDto(token);

        return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<TokenResponseDto> renewPassword(String username) {
        User user = userRepository.getByUsername(
                        username.toLowerCase())
                .orElseThrow(() -> new NotFoundException(Exceptions.NOT_FOUND, username));
        tokenService.deleteAllTokenByType(user, TokenType.REFRESH);

        Token token = tokenService.generateToken(TokenType.REFRESH);
        token.setUser(user);
        tokenService.save(token);

        mailService.createAndSendToMailForRefresh(user, token.getName());

        TokenResponseDto responseDto = TokenResponseDto.builder()
                .token(token.getName())
                .tokenType(TokenType.REFRESH).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponseDto> resetPassword(PasswordRequestDto request, String tokenValue) {
        Token token = tokenService.getByNameAndTokenType(tokenValue, TokenType.REFRESH);
        token.setStatus(false);
        tokenService.save(token);

        User user = token.getUser();
        user.setPassword(userServiceHelper.encode(request.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>(MessageResponseDto.builder()
                .message(messageUtil.getMessage(Message.PASSOWRD_RESTED_MESSAGE.getMessage()))
                .build(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDto> getById(Long id, String tokenValue) {

        tokenService.getByNameAndTokenType(tokenValue, TokenType.ACCESS);

        User user = userRepository.getByIdUs(id)
                .orElseThrow(() -> new NotFoundException(Exceptions.NOT_FOUND, id));

        UserResponseDto userResponseDto = userServiceHelper.generateUserResponseDto(user);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageResponseDto> changePrivacy(String tokenValue) {

        Token token = tokenService.getByNameAndTokenType(tokenValue, TokenType.ACCESS);

        User user = token.getUser();

        user.getUserSituation().setPrivate(!user.getUserSituation().isPrivate());

        userRepository.save(user);

        return new ResponseEntity<>(MessageResponseDto.builder()
                .message(messageUtil.getMessage(Message.PRIVACY_CHANGED_MESSAGE.getMessage()))
                .build(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponseDto> sendFollowing(Long id, String tokenValue) {

        User user = tokenService.getByNameAndTokenType(tokenValue, TokenType.ACCESS).getUser();

        User followingUser = userRepository.getByIdUs(id)
                .orElseThrow(() -> new NotFoundException(Exceptions.NOT_FOUND, id));

        Optional<Followings> followed = userRepository.checkFollowing(user,followingUser);

        if (followed.isPresent() || user.getId() == followingUser.getId()){
            throw new ApplicationException(Exceptions.ALREADY_SUBSCRIBED_MESSAGE);
        }

        Followings followings = Followings.builder()
                .user(user)
                .following(followingUser)
                .build();

        Followers followers = Followers.builder()
                .user(followingUser)
                .follower(user)
                .build();

        user.getUserDetails().setFollowingSize(user.getUserDetails().getFollowingSize() + 1);
        followingUser.getUserDetails().setFollowersSize(followingUser.getUserDetails().getFollowersSize() + 1);

        followersRepository.save(followers);
        followingsRepository.save(followings);

        return new ResponseEntity<>(MessageResponseDto.builder()
                .message(messageUtil.getMessage(Message.USER_FOLLOWED_MESSAGE.getMessage()))
                .build(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponseDto> confirm(String token) {
        tokenService.confirmUser(token);
        return new ResponseEntity<>(MessageResponseDto.builder()
                .message(messageUtil.getMessage(Message.CONFIRM_USER_MESSAGE.getMessage()))
                .build(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}
