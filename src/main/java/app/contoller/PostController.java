package app.contoller;

import app.model.dto.request.CommentRequestDto;
import app.model.dto.response.MessageResponseDto;
import app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(value = "/sharing")
    public ResponseEntity<MessageResponseDto> sharing(@RequestHeader String token,
                                                      @RequestParam("description") String description,
                                                      @RequestPart(value = "requestFile") MultipartFile file) {

        return postService.sharing(file, description, token);
    }

    @GetMapping
    public ResponseEntity<Map<Long, String>> getAll(@RequestHeader String token) {
        return postService.getAll(token);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestHeader String token,
                                             @RequestParam("filePath") String filename) {
        return postService.download(token, filename);
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<Void> like(@RequestHeader String token,
                                     @PathVariable Long id) {

        return postService.like(token, id);
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<Void> comment(@RequestHeader String token,
                                                      @RequestBody @Valid CommentRequestDto requestDto,
                                                      @PathVariable("id") Long id) {
        return postService.comment(token, requestDto, id);
    }


}
