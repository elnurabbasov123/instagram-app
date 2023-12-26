package app.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "exception.not-blank")
    private String comment;
}
