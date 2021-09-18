package formula1.api.repositories;

import formula1.api.entities.Circuit;
import formula1.api.entities.Driver;
import formula1.api.exceptions.circuit.CircuitNotFoundException;
import formula1.api.exceptions.driver.DriverNotFoundByCodeException;
import formula1.api.exceptions.driver.DriverNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    default Driver findDriverByRef(String ref) {
        Optional<Driver> driver = this.findAll()
                .stream()
                .filter(driver1 -> Objects.equals(driver1.getDriverRef(), ref))
                .findAny();

        if (driver.isPresent()) {
            return driver.get();
        } else {
            throw new DriverNotFoundException(ref);
        }
    }

    default Driver findDriverByCode(String code) {
        Optional<Driver> driver = this.findAll()
                .stream()
                .filter(driver1 -> Objects.equals(driver1.getCode(), code))
                .findAny();

        if (driver.isPresent()) {
            return driver.get();
        } else {
            throw new DriverNotFoundByCodeException(code);
        }
    }
}
