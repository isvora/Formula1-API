package formula1.api.exceptions.constructor.results;

public class ConstructorResultsNotFoundByRaceIdException extends RuntimeException {

    public ConstructorResultsNotFoundByRaceIdException(Long id) {
        super("Could not find constructor results for race " + id);
    }
}
