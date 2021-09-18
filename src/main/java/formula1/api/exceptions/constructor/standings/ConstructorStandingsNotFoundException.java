package formula1.api.exceptions.constructor.standings;

public class ConstructorStandingsNotFoundException extends RuntimeException {
    public ConstructorStandingsNotFoundException(Long id) {
        super("Could not find constructor standings for id " + id);
    }
}
