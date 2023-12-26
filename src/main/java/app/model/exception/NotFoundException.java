package app.model.exception;

import app.model.enums.Exceptions;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private final Exceptions exceptions;
    private final Object dynamicKey;

    public NotFoundException(Exceptions exceptions,Object dynamicKey) {
        super(exceptions.getMessage());
        this.exceptions = exceptions;
        this.dynamicKey = dynamicKey;
    }
}
