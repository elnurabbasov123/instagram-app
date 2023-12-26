package app.service;

import app.model.entity.Token;
import app.model.entity.User;
import app.model.enums.TokenType;

public interface TokenService {

    Token generateToken(TokenType tokenType);

    void confirmUser(String token);

    Token getByNameAndTokenType(String token, TokenType tokenType);

    void deleteAllTokenByType(User user, TokenType tokenType);

    void save(Token token);
}
