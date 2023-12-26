package app.model.dto.response;

import app.model.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenResponseDto {
    private TokenType tokenType;
    private String token;
}
