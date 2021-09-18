package formula1.api.advice;

import formula1.api.exceptions.constructor.ConstructorNotFoundByCountryException;
import formula1.api.exceptions.constructor.ConstructorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ConstructorNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ConstructorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructorNotFoundHandler(ConstructorNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ConstructorNotFoundByCountryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructorNotFoundForCountryHandler(ConstructorNotFoundByCountryException exception) {
        return exception.getMessage();
    }
}
