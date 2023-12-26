package app.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "_followings")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "instagram")
public class Followings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//1  2
    @OneToOne
    private User user;//1
    @OneToOne
    private User following;//2
}




