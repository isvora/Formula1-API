package formula1.api.repositories;

import formula1.api.entities.ConstructorStandings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public interface ConstructorStandingsRepository extends JpaRepository<ConstructorStandings, Long> {

    default List<ConstructorStandings> findConstructorsForConstructor(Long constructorId) {
        return this.findAll().stream()
                .filter(constructorStandings -> Objects.equals(constructorStandings.getConstructorId(), constructorId))
                .collect(Collectors.toList());
    }

    default List<ConstructorStandings> findConstructorsForRace(Long raceId) {
        return this.findAll().stream()
                .filter(constructorStandings -> Objects.equals(constructorStandings.getRaceId(), raceId))
                .collect(Collectors.toList());
    }

    default ConstructorStandings findConstructorStandingsWithMostWins() {
        List<ConstructorStandings> constructorStandings = this.findAll().stream()
                .sorted(Comparator.comparing(ConstructorStandings::getPoints))
                .collect(Collectors.toList());

        // return the last element since the list is sorted by wins, last = most wins
        return constructorStandings.get(constructorStandings.size() - 1);
    }

    default ConstructorStandings findConstructorStandingsWithMostPoints() {
        List<ConstructorStandings> constructorStandings = this.findAll().stream()
                .sorted(Comparator.comparing(ConstructorStandings::getPoints))
                .collect(Collectors.toList());

        // return the last element since the list is sorted by wins, last = most wins
        return constructorStandings.get(constructorStandings.size() - 1);
    }
}
