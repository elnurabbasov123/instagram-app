package app.service;


import app.model.dto.response.MailDto;
import app.model.entity.User;

public interface MailService {
    void sendmail(MailDto mailDto);

    void createAndSendToMailForConfirmation(User user,String token);
    void createAndSendToMailForRefresh(User user,String token);
}
