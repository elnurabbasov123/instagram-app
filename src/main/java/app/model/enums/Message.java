package app.model.enums;

import lombok.Getter;

@Getter
public enum Message {
    REGISTER_SUCCESSFULLY_MESSAGE("message.register-successfully"),
    OTP_SENT_MESSAGE("message.sent-otp"),
    PASSOWRD_RESTED_MESSAGE("message.password-rested"),
    OPEN_STATUS_MESSAGE("message.account-status-open"),
    CONFIRM_USER_MESSAGE("message.mail.for-confirm"),
    REFRESH_TOKEN_SENT_MESSAGE("message.mail.for-refresh"),
    PRIVACY_CHANGED_MESSAGE("message.privacy-changed"),
    USER_FOLLOWED_MESSAGE("message.user-followed"),
    POST_SHARED_MESSAGE("message.post.file-shared");
    private final String message;

    Message(String message) {
        this.message = message;
    }
}
