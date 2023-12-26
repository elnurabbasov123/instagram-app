package app.model.helper;

import app.model.dto.response.TokenResponseDto;
import app.model.dto.response.UserResponseDto;
import app.model.entity.Token;
import app.model.entity.User;

public interface UserServiceHelper {
    String encode(String pass);
    boolean matches(String encodePass,String pass);

    TokenResponseDto buildTokenResponseDto(Token token);

    UserResponseDto generateUserResponseDto(User user);
}
