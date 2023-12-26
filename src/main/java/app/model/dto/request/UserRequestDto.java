package app.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "exception.not-blank")
    private String name;
    @NotBlank(message = "exception.not-blank")
    private String username;
    @Pattern(regexp = "[a-zA-Z0-9]{6,10}",message = "exception.regex-pattern")
    @NotBlank(message = "exception.not-blank")
    private String password;
    @NotBlank(message = "exception.not-blank")
    @Pattern(regexp = "[+0-9]{11,13}",message = "exception.regex-pattern")
    private String phoneNumber;
    @NotBlank(message = "exception.not-blank")
    @Email(message = "exception.email-not-valid")
    private String email;

    public UserRequestDto(String name, String username, String password, String phoneNumber, String email) {
        this.name = name;
        setUsername(username);
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
}
