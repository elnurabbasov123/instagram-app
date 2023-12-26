package app.service.impl;

import app.model.entity.Token;
import app.model.entity.User;
import app.model.enums.Exceptions;
import app.model.enums.TokenType;
import app.model.exception.NotFoundException;
import app.repository.TokenRepository;
import app.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    @Value(value = "${token.time.refresh}")
    private  Long timeRefresh;
    @Value(value = "${token.time.other}")
    private  Long timeOther;

    @Override
    public Token generateToken(TokenType tokenType) {
            return Token.builder()
                .expiredDate(tokenType.equals(TokenType.REFRESH)
                        ? LocalDateTime.now().plusSeconds(timeRefresh)
                        : LocalDateTime.now().plusDays(timeOther))
                .name(UUID.randomUUID().toString())
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void confirmUser(String tokenValue) {
        Token token = tokenRepository.getTokenByType(tokenValue,TokenType.CONFIRMATION)
                .orElseThrow(() -> new NotFoundException(Exceptions.NOT_FOUND, tokenValue));

        User user = token.getUser();
        user.getUserSituation().setStatus(true);
        token.setStatus(false);
    }

    @Override
    public Token getByNameAndTokenType(String token, TokenType tokenType) {
        return tokenRepository.getTokenByType(token,tokenType)
                .orElseThrow(()->new NotFoundException(Exceptions.NOT_FOUND,token));
    }

    @Override
    public void deleteAllTokenByType(User user, TokenType tokenType) {
         tokenRepository.deleteAllTokenByType(user,tokenType);
    }

    @Override
    @Transactional
    public void save(Token token) {
        tokenRepository.save(token);
    }
}
