package formula1.api.advice;

import formula1.api.exceptions.ConstructorStandingsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConstructorStandingsNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ConstructorStandingsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructorStandingsNotFoundException(ConstructorStandingsNotFoundException exception) {
        return exception.getMessage();
    }
}
