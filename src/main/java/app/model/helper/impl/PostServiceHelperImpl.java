package app.model.helper.impl;

import app.model.entity.Post;
import app.model.entity.PostDetails;
import app.model.entity.User;
import app.model.enums.PostType;
import app.model.helper.PostServiceHelper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class PostServiceHelperImpl implements PostServiceHelper {
    @Override
    @SneakyThrows
    public void createDirectories(Path path){
        if(!Files.exists(path)){
            Files.createDirectory(path);
        }
    }

    public Post buildPost(User user,String parent,String desc,String fileId){
        return Post.builder()
                .postType(PostType.IMAGE)
                .postPath(parent)
                .fileId(fileId)
                .description(desc)
                .user(user)
                .postDetails(PostDetails.builder().build())
                .build();
    }
}
