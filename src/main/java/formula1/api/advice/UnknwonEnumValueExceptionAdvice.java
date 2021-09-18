package formula1.api.advice;

import formula1.api.exceptions.UnknownEnumValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UnknwonEnumValueExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(UnknownEnumValueException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructUnknownEnumValueException(UnknownEnumValueException exception) {
        return exception.getMessage();
    }
}
