package formula1.api.exceptions.constructor.standings;

public class ConstructorStandingsNotFoundForRaceException extends RuntimeException {
    public ConstructorStandingsNotFoundForRaceException(Long raceId) {
        super("Constructors standings not found for race " + raceId);
    }
}
