package app.model.exception;

import app.model.enums.Exceptions;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final Exceptions exceptions;

    public ApplicationException(Exceptions exceptions) {
        super(exceptions.getMessage());
        this.exceptions = exceptions;
    }
}
