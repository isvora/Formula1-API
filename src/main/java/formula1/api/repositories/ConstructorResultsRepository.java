package formula1.api.repositories;

import formula1.api.entities.ConstructorResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public interface ConstructorResultsRepository extends JpaRepository<ConstructorResults, Long> {

    default List<ConstructorResults> findConstructorResultsForRace(Long raceId) {
        return this.findAll().stream()
                .filter(constructorResults -> Objects.equals(constructorResults.getRaceId(), raceId))
                .collect(Collectors.toList());
    }

    default List<ConstructorResults> findConstructorsResultsForConstructor(Long constructorId) {
        return this.findAll().stream()
                .filter(constructorResults -> Objects.equals(constructorResults.getConstructorId(), constructorId))
                .collect(Collectors.toList());
    }

    default ConstructorResults findConstructorResultsWithMostWins() {
        List<ConstructorResults> constructorResults = this.findAll().stream()
                .sorted(Comparator.comparing(ConstructorResults::getPoints))
                .collect(Collectors.toList());

        // return the last element since the list is sorted by wins, last = most wins
        return constructorResults.get(constructorResults.size() - 1);
    }
}
