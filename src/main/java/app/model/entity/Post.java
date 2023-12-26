package app.model.entity;

import app.model.enums.PostType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "_posts")
@Table(schema = "instagram")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String postPath;
    private String fileId;
    private String description;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    @OneToMany(mappedBy = "post")
    private List<Like> likes;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private PostDetails postDetails;
}
