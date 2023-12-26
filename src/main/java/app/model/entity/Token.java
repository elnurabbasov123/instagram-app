package app.model.entity;

import app.model.enums.TokenType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "_tokens")
@Table(schema = "instagram")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime expiredDate;
    @Builder.Default
    private boolean status = true;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

}
