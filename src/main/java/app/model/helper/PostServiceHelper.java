package app.model.helper;

import app.model.entity.Post;
import app.model.entity.User;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public interface PostServiceHelper {
    void createDirectories(Path path);
    Post buildPost(User user, String parent, String desc,String fileId);
}
