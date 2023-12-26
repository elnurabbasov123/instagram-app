package app.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@Getter

public enum TokenType {
    ACCESS
    ,REFRESH
    ,CONFIRMATION;
}
