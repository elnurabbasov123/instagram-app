package app.model.helper.impl;

import app.model.dto.response.MailDto;
import app.model.entity.User;
import app.model.helper.MailServiceHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class MailServiceHelperImpl implements MailServiceHelper {
    private static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    @Override
    public MailDto buildMailDtoForConfirm(User user,String token) {

        String url = "http://" + "localhost" + ":" + getRequest().getServerPort() + "/users/confirmation?token=" + token;
        System.out.println(url);

        return MailDto.builder()
                .to(user.getEmail())
                .subject("Registration")
                .text("<p> Hi, " + user.getUsername() + ", <p>" +
                        "<p>Thank you for registering with us," +
                        "Please, follow the link below to complete your registration.<p>" +
                        "<a href=\"" + url + "\">Verify your email to active your account<a>" +
                        "<p> Thank you <br> Users Registration Portal Service")
                .build();
    }

    @Override
    public MailDto buildMailDtoForRenewPassword(User user,String token) {
        String url = "http://" + "localhost" + ":" + getRequest().getServerPort() + "/users/reset-password?token="+ token;


        return MailDto.builder()
                .to(user.getEmail())
                .subject("Reset Password")
                .text("<p> Hi, " + user.getUsername() + ", <p>" +
                        "<p>Thank you for change password with us," +
                        "Please, follow the link below to complete your change password.<p>" +
                        "<a href=\"" + url + "\">Verify your email to active your account<a>" +
                        "<p> Thank you <br> Users change password Portal Service")
                .build();
    }
}
