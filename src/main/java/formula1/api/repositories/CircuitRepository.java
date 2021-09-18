package formula1.api.repositories;

import formula1.api.entities.Circuit;
import formula1.api.exceptions.CircuitNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Long>  {

    default Circuit findCircuitByRef(String ref) {
        Optional<Circuit> circuit = this.findAll()
                .stream()
                .filter(circuit1 -> circuit1.getCircuitRef().equals(ref))
                .findAny();

        if (circuit.isPresent()) {
            return circuit.get();
        } else {
            throw new CircuitNotFoundException("Circuit not found for ref " + ref);
        }
    }

    default List<Circuit> findCircuitsByCountry(String country) {
        return this.findAll()
                .stream()
                .filter(circuit1 -> circuit1.getCountry().equals(country))
                .collect(Collectors.toList());
    }
}
