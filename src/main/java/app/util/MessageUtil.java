package app.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageUtil {
    private final MessageSource messageSource;

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
    }

    public String getMessage(String messageCode, Object... dynamicKeys) {
        try {
            return messageSource.getMessage(messageCode, dynamicKeys, getRequest().getLocale());
        } catch (NoSuchMessageException exception) {
            log.error("localize: {}", messageCode);
        }

        return messageCode;

    }
}
