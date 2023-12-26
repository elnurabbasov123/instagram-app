package app.model.helper.impl;

import app.model.dto.response.TokenResponseDto;
import app.model.dto.response.UserResponseDto;
import app.model.entity.Token;
import app.model.entity.User;
import app.model.enums.TokenType;
import app.model.helper.UserServiceHelper;
import app.util.cryptography.BCryptPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
@RequiredArgsConstructor
public class UserServiceHelperImpl implements UserServiceHelper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String encode(String pass) {
        return bCryptPasswordEncoder.encode(pass);
    }

    @Override
    public boolean matches(String currentPas, String hashedPass) {
        return bCryptPasswordEncoder.matches(currentPas,hashedPass);
    }

    @Override
    public UserResponseDto generateUserResponseDto(User user) {
        return UserResponseDto.builder()
                .username(user.getUsername())
                .isPrivate(user.getUserSituation().isPrivate())
                .posts(user.getUserDetails().getPostSize())
                .followers(user.getUserDetails().getFollowersSize())
                .following(user.getUserDetails().getFollowingSize())
                .build();
    }

    public TokenResponseDto buildTokenResponseDto(Token token) {
        return TokenResponseDto.builder()
                .token(token.getName())
                .tokenType(TokenType.ACCESS)
                .build();
    }

}
