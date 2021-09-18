package formula1.api.exceptions.constructor.results;

public class ConstructorResultsNotFoundForConstructorException extends RuntimeException {

    public ConstructorResultsNotFoundForConstructorException(Long id) {
        super("Could not find constructor results for constructor " + id);
    }
}
