package formula1.api.exceptions.constructor.standings;

public class ConstructorStandingsNotFoundForConstructorException extends RuntimeException {
    public ConstructorStandingsNotFoundForConstructorException(Long constructorId) {
        super("Constructor standings not found for constructor: " + constructorId);
    }
}
