package formula1.api.repositories;

import formula1.api.entities.LapTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface LapTimeRepository extends JpaRepository<LapTime, Long> {

    default List<LapTime> findAllLapTimesByDriver(Long driverId) {
        return this.findAll().stream()
                .filter(lapTime -> Objects.equals(lapTime.getDriverId(), driverId))
                .collect(Collectors.toList());
    }

    default List<LapTime> findAllLapTimesByRace(Long raceId) {
        return this.findAll().stream()
                .filter(lapTime -> Objects.equals(lapTime.getRaceId(), raceId))
                .collect(Collectors.toList());
    }

    default List<LapTime> findAllLapTimesByADriverInARace(Long driverId, Long raceId) {
        return this.findAll().stream()
                .filter(lapTime -> Objects.equals(lapTime.getRaceId(), raceId) && Objects.equals(lapTime.getDriverId(), driverId))
                .collect(Collectors.toList());
    }

    default List<LapTime> findSpecificLapTimeByADriverInARace(Long driverId, Long raceId, int lap) {
        return this.findAll().stream()
                .filter(lapTime -> Objects.equals(lapTime.getRaceId(), raceId) && Objects.equals(lapTime.getDriverId(), driverId) && Objects.equals(lapTime.getLap(), lap))
                .collect(Collectors.toList());
    }

    default Optional<LapTime> findFastestLapTimeByADriverInARace(Long driverId, Long raceId) {
        return this.findAll().stream()
                .filter(lapTime -> Objects.equals(lapTime.getRaceId(), raceId) && Objects.equals(lapTime.getDriverId(), driverId)).min(Comparator.comparing(LapTime::getMiliseconds));
    }
}
