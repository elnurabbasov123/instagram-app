package app.model.handle;

import app.model.exception.ApplicationException;
import app.model.exception.NotFoundException;
import app.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalHandler extends DefaultErrorAttributes {
    private final MessageUtil messageUtil;
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handle(ApplicationException applicationException, WebRequest webRequest){
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        errorAttributes.put("error",messageUtil.getMessage(applicationException.getExceptions().getMessage()));
        errorAttributes.put("status",applicationException.getExceptions().getCode());
        errorAttributes.put("path",((ServletWebRequest)webRequest).getRequest().getRequestURI());
        log.error("Exception -> {}",errorAttributes);
        return new ResponseEntity<>(errorAttributes, HttpStatus.valueOf(applicationException.getExceptions().getCode()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handle(MethodArgumentNotValidException exception, WebRequest webRequest){

        Map<String , Object> invalidFields = new HashMap<>();
        exception.getFieldErrors()
                .forEach(fieldError -> invalidFields.put(fieldError.getField(), messageUtil.getMessage(fieldError.getDefaultMessage())));
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        errorAttributes.put("error",invalidFields);
        errorAttributes.put("status",HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("path",((ServletWebRequest)webRequest).getRequest().getRequestURI());
        log.error("Exception -> {}",errorAttributes);
        return new ResponseEntity<>(errorAttributes, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(NotFoundException applicationException, WebRequest webRequest){
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        errorAttributes.put("error",messageUtil.getMessage(applicationException.getExceptions().getMessage(), applicationException.getDynamicKey()));
        errorAttributes.put("status",applicationException.getExceptions().getCode());
        errorAttributes.put("path",((ServletWebRequest)webRequest).getRequest().getRequestURI());
        log.error("Exception -> {}",errorAttributes);
        return new ResponseEntity<>(errorAttributes, HttpStatus.valueOf(applicationException.getExceptions().getCode()));
    }


}
