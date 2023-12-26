package app.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "_users")
@Table(schema = "instagram")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    @Builder.Default
    private LocalDateTime createdAt=now();
    @Builder.Default
    private LocalDateTime updatedAt=now();
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private UserDetails userDetails;
    @OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private UserSituation userSituation;
    @OneToMany(mappedBy = "user",cascade = CascadeType.MERGE)
    private List<Token> token;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
