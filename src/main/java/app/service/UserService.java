package app.service;

import app.model.dto.request.AuthenticationRequestDto;
import app.model.dto.request.PasswordRequestDto;
import app.model.dto.request.UserRequestDto;
import app.model.dto.response.MessageResponseDto;
import app.model.dto.response.TokenResponseDto;
import app.model.dto.response.UserResponseDto;
import app.model.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<MessageResponseDto> register(UserRequestDto request);

    ResponseEntity<TokenResponseDto> authentication(AuthenticationRequestDto request);

    ResponseEntity<TokenResponseDto> renewPassword(String username);

    ResponseEntity<MessageResponseDto> resetPassword(PasswordRequestDto request, String token);

    ResponseEntity<UserResponseDto> getById(Long id, String token);

    ResponseEntity<MessageResponseDto> changePrivacy(String token);

    ResponseEntity<MessageResponseDto> sendFollowing(Long id, String token);

    ResponseEntity<MessageResponseDto> confirm(String token);
    void save(User user);
}
