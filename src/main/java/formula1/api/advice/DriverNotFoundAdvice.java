package formula1.api.advice;

import formula1.api.exceptions.driver.DriverNotFoundByCodeException;
import formula1.api.exceptions.driver.DriverNotFoundByNationalityException;
import formula1.api.exceptions.driver.DriverNotFoundException;
import formula1.api.exceptions.misc.UnknownEnumValueException;
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

    @ResponseBody
    @ExceptionHandler(DriverNotFoundByCodeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructDriverNotFoundByCodeException(DriverNotFoundByCodeException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DriverNotFoundByNationalityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String constructDriverNotFoundByNationalityException(DriverNotFoundByNationalityException exception) {
        return exception.getMessage();
    }
}
