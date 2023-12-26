package app.service.impl;

import app.model.dto.response.MailDto;
import app.model.entity.User;
import app.model.helper.MailServiceHelper;
import app.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final MailServiceHelper mailServiceHelper;

    @Override
    @SneakyThrows
    public void sendmail(MailDto mailDto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setSubject(mailDto.getSubject());
        mimeMessageHelper.setTo(mailDto.getTo());
        mimeMessageHelper.setText(mailDto.getText(),true);

        javaMailSender.send(message);
    }

    @Override
    public void createAndSendToMailForConfirmation(User user,String token) {
        MailDto mailDto = mailServiceHelper.buildMailDtoForConfirm(user, token);
        sendmail(mailDto);
    }

    @Override
    public void createAndSendToMailForRefresh(User user, String token) {
        MailDto mailDto = mailServiceHelper.buildMailDtoForRenewPassword(user, token);
        sendmail(mailDto);
    }
}
