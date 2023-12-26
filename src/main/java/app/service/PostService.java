package app.service;

import app.model.dto.request.CommentRequestDto;
import app.model.dto.response.MessageResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PostService {
    ResponseEntity<MessageResponseDto> sharing(MultipartFile file, String description, String token);

    ResponseEntity<Resource> download(String token, String filename);

    ResponseEntity<Void> like(String token, Long id);

    ResponseEntity<Map<Long, String>> getAll(String token);

    ResponseEntity<Void> comment(String token, CommentRequestDto requestDto, Long id);
}
