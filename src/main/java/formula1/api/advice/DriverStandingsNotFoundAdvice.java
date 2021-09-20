package formula1.api.advice;

import formula1.api.exceptions.DriverStandingsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DriverStandingsNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DriverStandingsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructUnknownDriverStandingsException(DriverStandingsNotFoundException exception) {
        return exception.getMessage();
    }
}
