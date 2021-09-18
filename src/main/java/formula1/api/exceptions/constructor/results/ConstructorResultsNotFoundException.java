package formula1.api.exceptions.constructor.results;

public class ConstructorResultsNotFoundException  extends RuntimeException {

    public ConstructorResultsNotFoundException(Long id) {
        super("Could not find constructor results with id " + id);
    }
}
