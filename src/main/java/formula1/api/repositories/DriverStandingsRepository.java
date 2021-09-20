package formula1.api.repositories;

import formula1.api.entities.DriverStandings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public interface DriverStandingsRepository extends JpaRepository<DriverStandings, Long> {

    default List<DriverStandings> findDriverStandingsByRace(Long raceId) {
        return this.findAll().stream()
                .filter(driverStandings -> Objects.equals(driverStandings.getRaceId(), raceId))
                .collect(Collectors.toList());
    }

    default List<DriverStandings> findDriverStandingsByDriver(Long driverId) {
        return this.findAll().stream()
                .filter(driverStandings -> Objects.equals(driverStandings.getDriverId(), driverId))
                .collect(Collectors.toList());
    }

    default List<DriverStandings> findDriverStandingsByWins() {
        return this.findAll().stream()
                .sorted(Comparator.comparing(DriverStandings::getWins).reversed())
                .collect(Collectors.toList());
    }

    default List<DriverStandings> findDriverStandingsByPoints() {
        return this.findAll().stream()
                .sorted(Comparator.comparing(DriverStandings::getPoints).reversed())
                .collect(Collectors.toList());
    }
}
