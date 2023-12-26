package app.model.dto.request;

import app.model.enums.Exceptions;
import app.model.exception.ApplicationException;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Getter
public class PasswordRequestDto {
    @Pattern(regexp = "[a-zA-Z0-9]{6,10}",message = "exception.regex-pattern")
    @NotBlank(message = "exception.not-blank")
    private String password;
    @NotBlank(message = "exception.not-blank")
    @Pattern(regexp = "[a-zA-Z0-9]{6,10}",message = "exception.regex-pattern")
    private String repeatPassword;

    public PasswordRequestDto(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)){
            throw new ApplicationException(Exceptions.NOT_SAME_PASSWORD);
        }
        this.password = password;
        this.repeatPassword = repeatPassword;
    }
}
