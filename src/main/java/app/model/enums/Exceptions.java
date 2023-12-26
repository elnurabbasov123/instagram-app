package app.model.enums;

import lombok.Getter;

@Getter
public enum Exceptions {
    ALREADY_USE_TOKEN("exception.already-use-token",400),
    NOT_SAME_PASSWORD("exception.not-same-password",400),
    PASSWORD_CANNOT_BE_SAME_AS_BEFORE("exception.not-same-password-as-before",400),
    NOT_FOUND("exception.not-found",404),
    USERNAME_OR_PASSWORD_IS_WRONG_EXCEPTION("exception.wrong-username-or-password", 400),
    ALREADY_HAVE_LIKE_EXCEPTION("already have like", 403),
    ALREADY_SUBSCRIBED_MESSAGE("exception.already-subscribed",400);
    private final String message;
    private final int code;

    Exceptions(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
