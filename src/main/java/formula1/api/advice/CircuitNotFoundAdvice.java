package formula1.api.advice;

import formula1.api.exceptions.CircuitNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CircuitNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CircuitNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String circuitNotFoundHandler(CircuitNotFoundException exception) {
        return exception.getMessage();
    }
}
