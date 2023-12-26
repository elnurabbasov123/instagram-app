package app.model.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private String username;
    private boolean isPrivate;
    private Long followers;
    private Long following;
    private Long posts;
}
