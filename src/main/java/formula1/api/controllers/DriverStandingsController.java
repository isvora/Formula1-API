package formula1.api.controllers;

import formula1.api.assembler.DriverStandingsModelAssembler;
import formula1.api.entities.DriverStandings;
import formula1.api.exceptions.DriverStandingsNotFoundException;
import formula1.api.repositories.DriverStandingsRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DriverStandingsController {

    public final DriverStandingsRepository driverStandingsRepository;
    public final DriverStandingsModelAssembler driverStandingsModelAssembler;

    public DriverStandingsController(DriverStandingsRepository driverStandingsRepository, DriverStandingsModelAssembler driverStandingsModelAssembler) {
        this.driverStandingsRepository = driverStandingsRepository;
        this.driverStandingsModelAssembler = driverStandingsModelAssembler;
    }

    @GetMapping("/api/driver-standings")
    public CollectionModel<EntityModel<DriverStandings>> getAllDriverStandings() {
        List<EntityModel<DriverStandings>> driverStandingsEntityModels = driverStandingsRepository.findAll()
                .stream()
                .map(driverStandingsModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                driverStandingsEntityModels,
                linkTo(methodOn(DriverStandingsController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/driver-standings/{driverStandingsId}")
    public EntityModel<DriverStandings> getDriverStandingsById(@PathVariable Long driverStandingsId) {
        DriverStandings driverStandings = driverStandingsRepository.findById(driverStandingsId)
                .orElseThrow(() -> new DriverStandingsNotFoundException("Driver standings not found for driverStandingsId " + driverStandingsId));
        return driverStandingsModelAssembler.toModel(driverStandings);
    }

    @GetMapping("/api/driver-standings/race/{raceId}")
    public CollectionModel<EntityModel<DriverStandings>> getAllDriverStandingsByRace(@PathVariable Long raceId) {
        List<DriverStandings> driverStandingsList = driverStandingsRepository.findDriverStandingsByRace(raceId);
        List<EntityModel<DriverStandings>> driverStandingsEntityModels = driverStandingsList.stream()
                .map(driverStandingsModelAssembler::toModel)
                .collect(Collectors.toList());

        if (driverStandingsEntityModels.isEmpty()) {
            throw new DriverStandingsNotFoundException("Driver standings not found for raceId " + raceId);
        }

        return CollectionModel.of(
                driverStandingsEntityModels,
                linkTo(methodOn(DriverStandingsController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/driver-standings/driver/{driverId}")
    public CollectionModel<EntityModel<DriverStandings>> getAllDriverStandingsByDriverId(@PathVariable Long driverId) {
        List<DriverStandings> driverStandingsList = driverStandingsRepository.findDriverStandingsByDriver(driverId);
        List<EntityModel<DriverStandings>> driverStandingsEntityModels = driverStandingsList.stream()
                .map(driverStandingsModelAssembler::toModel)
                .collect(Collectors.toList());

        if (driverStandingsEntityModels.isEmpty()) {
            throw new DriverStandingsNotFoundException("Driver standings not found for driverId " + driverId);
        }

        return CollectionModel.of(
                driverStandingsEntityModels,
                linkTo(methodOn(DriverStandingsController.class)).withSelfRel()
        );
    }
}
