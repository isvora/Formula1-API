package formula1.api.controllers;

import formula1.api.assembler.DriverModelAssembler;
import formula1.api.entities.Driver;
import formula1.api.exceptions.DriverNotFoundException;
import formula1.api.repositories.DriverRepository;
import formula1.api.types.DobOrderEnum;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DriverController {

    private final DriverRepository driverRepository;
    private final DriverModelAssembler driverModelAssembler;

    public DriverController(DriverRepository driverRepository, DriverModelAssembler driverModelAssembler) {
        this.driverRepository = driverRepository;
        this.driverModelAssembler = driverModelAssembler;
    }

    @GetMapping("/api/drivers")
    public CollectionModel<EntityModel<Driver>> getAllDrivers() {
        List<EntityModel<Driver>> driverEntityModels = driverRepository.findAll().stream()
                .map(driverModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                driverEntityModels,
                linkTo(methodOn(DriverController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/drivers/{id}")
    public EntityModel<Driver> getDriverById(@PathVariable Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found for id " + id));

        return driverModelAssembler.toModel(driver);
    }

    @GetMapping("/api/drivers/")
    public EntityModel<Driver> getDriverByRef(@RequestParam(value="ref") String ref) {
        Driver driver = driverRepository.findDriverByRef(ref);
        return driverModelAssembler.toModel(driver);
    }

    @GetMapping("/api/drivers/bydob/")
    public CollectionModel<EntityModel<Driver>> getDriversByAge(@RequestParam(value="sort") DobOrderEnum dobOrderEnum) {
        List<Driver> drivers = driverRepository.findAllDriversByDob(dobOrderEnum);
        List<EntityModel<Driver>> driverEntityModels = drivers.stream()
                .map(driverModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                driverEntityModels,
                linkTo(methodOn(DriverController.class)).withSelfRel()
        );
    }

    @GetMapping("api/drivers/nationality/{nationality}")
    public CollectionModel<EntityModel<Driver>> getDriversByNationality(@PathVariable String nationality) {
        List<Driver> drivers = driverRepository.findAllDriversByNationality(nationality);
        List<EntityModel<Driver>> driverEntityModels = drivers.stream()
                .map(driverModelAssembler::toModel)
                .collect(Collectors.toList());

        if (driverEntityModels.isEmpty()) {
            throw new DriverNotFoundException("Driver not found for nationality " + nationality);
        }

        return CollectionModel.of(
                driverEntityModels,
                linkTo(methodOn(DriverController.class)).withSelfRel()
        );
    }
}
