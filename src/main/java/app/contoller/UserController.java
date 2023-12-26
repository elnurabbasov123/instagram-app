package app.contoller;

import app.model.dto.request.AuthenticationRequestDto;
import app.model.dto.request.PasswordRequestDto;
import app.model.dto.request.UserRequestDto;
import app.model.dto.response.MessageResponseDto;
import app.model.dto.response.TokenResponseDto;
import app.model.dto.response.UserResponseDto;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<MessageResponseDto> registration(@RequestBody @Valid UserRequestDto request){
        return userService.register(request);
    }

    @PostMapping("/authentication")
    public ResponseEntity<TokenResponseDto> authentication(@RequestBody AuthenticationRequestDto request){
        return userService.authentication(request);
    }

    @PostMapping("/renew-password")
    public ResponseEntity<TokenResponseDto> renewPassword(@RequestParam("username") String username){
        return userService.renewPassword(username);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDto> resetPassword(@RequestBody @Valid PasswordRequestDto request,
                                                            @RequestParam String token){

        return userService.resetPassword(request,token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id,
                                                   @RequestHeader() String token){
        return userService.getById(id,token);
    }

    @PatchMapping("/privacy-change")
    public ResponseEntity<MessageResponseDto> changePrivacy(@RequestHeader String token){

        return userService.changePrivacy(token);
    }

    @PostMapping("/send-following/{id}")
    public ResponseEntity<MessageResponseDto> sendFollow(@PathVariable Long id,
                                                         @RequestHeader String token){

        return userService.sendFollowing(id,token);
    }

    @GetMapping("/confirmation")
    public ResponseEntity<MessageResponseDto> confirmation(@RequestParam("token") String token){
        return userService.confirm(token);
    }
}
