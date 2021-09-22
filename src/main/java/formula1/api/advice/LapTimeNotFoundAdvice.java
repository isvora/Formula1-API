package formula1.api.advice;

import formula1.api.exceptions.LapTimeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LapTimeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(LapTimeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String lapTimeNotFoundAdvice(LapTimeNotFoundException exception) {
        return exception.getMessage();
    }
}
