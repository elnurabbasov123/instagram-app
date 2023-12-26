package app.model.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "_comments")
@Builder
@Table(schema = "instagram")
public class Comment {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
}
