package formula1.api.repositories;

import formula1.api.assembler.DriverModelAssembler;
import formula1.api.entities.Driver;
import formula1.api.exceptions.DriverNotFoundException;
import formula1.api.types.DobOrderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    default List<Driver> findAllDriversByDob(DobOrderEnum dobOrderEnum) {
        // If ageOrderType is OTY (Oldest to Youngest) we sort the list by Date of Birth. Otherwise we reverse the list.
        if (dobOrderEnum == DobOrderEnum.OTY) {
            return this.findAll().stream()
                    .sorted(Comparator.comparing(Driver::getDob))
                    .collect(Collectors.toList());
        } else {
            return this.findAll().stream()
                    .sorted(Comparator.comparing(Driver::getDob).reversed())
                    .collect(Collectors.toList());
        }
    }

    default List<Driver> findAllDriversByNationality(String nationality) {
        return this.findAll().stream()
                .filter(driver -> Objects.equals(driver.getNationality(), nationality))
                .collect(Collectors.toList());
    }

    default Driver findDriverByRef(String ref) {
        Optional<Driver> driver = this.findAll()
                .stream()
                .filter(driver1 -> Objects.equals(driver1.getDriverRef(), ref))
                .findAny();

        if (driver.isPresent()) {
            return driver.get();
        } else {
            throw new DriverNotFoundException("Driver not found for ref " + ref);
        }
    }
}
