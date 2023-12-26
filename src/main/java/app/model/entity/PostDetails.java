package app.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity(name = "_post_details")
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "instagram")
public class PostDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long likeSize;
    private long commentSize;
}
