package app.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailDto {
    private String to;
    private String subject;
    private String text;
}
