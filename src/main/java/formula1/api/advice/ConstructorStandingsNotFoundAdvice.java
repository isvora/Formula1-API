package formula1.api.advice;

import formula1.api.exceptions.constructor.standings.ConstructorStandingsNotFoundException;
import formula1.api.exceptions.constructor.standings.ConstructorStandingsNotFoundForConstructorException;
import formula1.api.exceptions.constructor.standings.ConstructorStandingsNotFoundForRaceException;
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

    @ResponseBody
    @ExceptionHandler(ConstructorStandingsNotFoundForConstructorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructorStandingsNotFoundForConstructorException(ConstructorStandingsNotFoundForConstructorException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstructorStandingsNotFoundForRaceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructorStandingsNotFoundForRaceException(ConstructorStandingsNotFoundForRaceException exception) {
        return exception.getMessage();
    }
}
