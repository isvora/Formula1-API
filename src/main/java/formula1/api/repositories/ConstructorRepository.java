package formula1.api.repositories;

import formula1.api.entities.Constructor;
import formula1.api.exceptions.circuit.CircuitNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface ConstructorRepository extends JpaRepository<Constructor, Long>  {

    default Constructor findByRef(String ref) {
        Optional<Constructor> constructor = this.findAll()
                .stream()
                .filter(constructor1 -> constructor1.getConstructorRef().equals(ref))
                .findAny();

        if (constructor.isPresent()) {
            return constructor.get();
        } else {
            throw new CircuitNotFoundException(ref);
        }
    }

    default List<Constructor> findCircuitsByNationality(String nationality) {
        return this.findAll()
                .stream()
                .filter(circuit1 -> circuit1.getNationality().equals(nationality))
                .collect(Collectors.toList());
    }
}
