package formula1.api.advice;

import formula1.api.exceptions.constructor.results.ConstructorResultsNotFoundByRaceIdException;
import formula1.api.exceptions.constructor.results.ConstructorResultsNotFoundException;
import formula1.api.exceptions.constructor.results.ConstructorResultsNotFoundForConstructorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConstructorResultsNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ConstructorResultsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructorResultsNotFound(ConstructorResultsNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstructorResultsNotFoundByRaceIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructorResultsByRaceNotFound(ConstructorResultsNotFoundByRaceIdException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstructorResultsNotFoundForConstructorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructorResultsForConstructorNotFound(ConstructorResultsNotFoundForConstructorException exception) {
        return exception.getMessage();
    }
}
