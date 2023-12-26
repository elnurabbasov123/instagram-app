package app.service.impl;

import app.model.dto.request.CommentRequestDto;
import app.model.dto.response.MessageResponseDto;
import app.model.entity.*;
import app.model.enums.Exceptions;
import app.model.enums.Message;
import app.model.enums.TokenType;
import app.model.exception.ApplicationException;
import app.model.exception.NotFoundException;
import app.model.helper.PostServiceHelper;
import app.repository.CommentRepository;
import app.repository.LikeRepository;
import app.repository.PostRepository;
import app.service.PostService;
import app.service.TokenService;
import app.service.UserService;
import app.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserService userService;
    private final TokenService tokenService;
    private final PostRepository postRepository;
    private final MessageUtil messageUtil;
    private final PostServiceHelper postServiceHelper;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Override
    @SneakyThrows
    public ResponseEntity<MessageResponseDto> sharing(MultipartFile file, String description, String tokenValue) {

        Token token = tokenService.getByNameAndTokenType(tokenValue, TokenType.ACCESS);
        User user = token.getUser();

        Path newPath = Paths.get("posts\\" + user.getUsername());

        postServiceHelper.createDirectories(newPath);


        String fileOriginal = file.getOriginalFilename();
        String[] split = fileOriginal.split("\\.");
        String format = split[1];

        String fileName = createFileName(format);

        Files.write(newPath.resolve(fileName), file.getBytes());

        Post post = postServiceHelper.buildPost(user, fileName, description, fileName.split("\\.")[0]);

        user.getUserDetails().setPostSize(user.getUserDetails().getPostSize() + 1);

        userService.save(user);
        postRepository.save(post);

        return new ResponseEntity<>(MessageResponseDto.builder()
                .message(messageUtil.getMessage(Message.POST_SHARED_MESSAGE.getMessage()))
                .build(), HttpStatus.OK);
    }

    public String createFileName(String format) {
        StringBuilder append = new StringBuilder().append(UUID.randomUUID().toString().substring(0, 4))
                .append(".")
                .append(format);

        return append.toString();
    }

    @Override
    @SneakyThrows
    public ResponseEntity<Resource> download(String tokenValue, String filename) {

        Token token = tokenService.getByNameAndTokenType(tokenValue, TokenType.ACCESS);
        String username = token.getUser().getUsername();

        Resource resource = new UrlResource(Path.of("posts\\" + username).toUri().resolve(filename));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", filename);

        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> like(String tokenValue, Long id) {
        Token token = tokenService.getByNameAndTokenType(tokenValue, TokenType.ACCESS);
        User user = token.getUser();

        Post post = postRepository.findById(user, id).orElseThrow(() -> new NotFoundException(Exceptions.NOT_FOUND, id));

        if (likeRepository.findLikeByUser(user,post).isPresent()){
            throw new ApplicationException(Exceptions.ALREADY_HAVE_LIKE_EXCEPTION);
        }

        Like like = Like.builder()
                .user(user)
                .build();

        like.setPost(post);
        like.setUser(user);

        likeRepository.save(like);

        post.getPostDetails().setLikeSize(post.getPostDetails().getLikeSize() + 1);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<Long, String>> getAll(String tokenValue) {

        Token token = tokenService.getByNameAndTokenType(tokenValue, TokenType.ACCESS);
        User user = token.getUser();

        List<Post> posts = postRepository.getAll(user);
        Map<Long, String> postsLinks = new HashMap<>();

        posts.forEach(post -> postsLinks.put(post.getId(), "localhost:8080/posts/download?filePath=" + post.getPostPath()));

        return new ResponseEntity<>(postsLinks, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> comment(String tokenValue, CommentRequestDto requestDto, Long id) {

        System.out.println(requestDto.getComment());
        System.out.println(requestDto.getComment());
        System.out.println(requestDto.getComment());


        Token token = tokenService.getByNameAndTokenType(tokenValue, TokenType.ACCESS);
        User user = token.getUser();

        Post post = postRepository.findById(user, id).orElseThrow(() -> new NotFoundException(Exceptions.NOT_FOUND, id));

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(requestDto.getComment())
                .build();

        post.getPostDetails().setCommentSize(post.getPostDetails().getCommentSize() + 1);
        commentRepository.save(comment);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
