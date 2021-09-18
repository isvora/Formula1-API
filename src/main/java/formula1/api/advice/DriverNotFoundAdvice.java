package formula1.api.advice;

import formula1.api.exceptions.DriverNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DriverNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DriverNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructDriverNotFoundException(DriverNotFoundException exception) {
        return exception.getMessage();
    }
}
