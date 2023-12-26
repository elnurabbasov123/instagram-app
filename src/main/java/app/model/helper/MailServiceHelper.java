package app.model.helper;

import app.model.dto.response.MailDto;
import app.model.entity.User;

public interface MailServiceHelper {
    MailDto buildMailDtoForConfirm(User user,String token);
    MailDto buildMailDtoForRenewPassword(User user,String token);

}
