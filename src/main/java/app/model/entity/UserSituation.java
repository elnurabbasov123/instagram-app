package app.model.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "_user_situation")
@Builder
@Table(schema = "instagram")
public class UserSituation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isPrivate;
    private boolean status;
    private boolean isBlocked;
}
