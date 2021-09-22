package formula1.api.controllers;

import formula1.api.assembler.LapTimeModelAssembler;
import formula1.api.entities.Driver;
import formula1.api.entities.LapTime;
import formula1.api.exceptions.DriverNotFoundException;
import formula1.api.exceptions.LapTimeNotFoundException;
import formula1.api.repositories.LapTimeRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class LapTimeController {

    private final LapTimeRepository lapTimeRepository;
    private final LapTimeModelAssembler lapTimeModelAssembler;

    public LapTimeController(LapTimeRepository lapTimeRepository, LapTimeModelAssembler lapTimeModelAssembler) {
        this.lapTimeRepository = lapTimeRepository;
        this.lapTimeModelAssembler = lapTimeModelAssembler;
    }

    @GetMapping("/api/lap-times")
    public CollectionModel<EntityModel<LapTime>> getAllLapTimes() {
        List<EntityModel<LapTime>> lapTimeEntityModels = lapTimeRepository.findAll().stream()
                .map(lapTimeModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                lapTimeEntityModels,
                linkTo(methodOn(LapTimeController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/lap-times/{id}")
    public EntityModel<LapTime> getLapTimesById(@PathVariable Long id) {
        LapTime lapTime = lapTimeRepository.findById(id)
                .orElseThrow(() -> new LapTimeNotFoundException("Lap time not found for id " + id));

        return lapTimeModelAssembler.toModel(lapTime);
    }

    @GetMapping("/api/lap-times/driverId/{driverId}")
    public CollectionModel<EntityModel<LapTime>>  getLapTimesByDriver(@PathVariable Long driverId) {
        List<EntityModel<LapTime>> lapTimeEntityModels = lapTimeRepository.findAllLapTimesByDriver(driverId).stream()
                .map(lapTimeModelAssembler::toModel)
                .collect(Collectors.toList());

        if (lapTimeEntityModels.isEmpty()) {
            throw new LapTimeNotFoundException("Lap times not found for driverId " + driverId);
        }

        return CollectionModel.of(
                lapTimeEntityModels,
                linkTo(methodOn(LapTimeController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/lap-times/race/{raceId}")
    public CollectionModel<EntityModel<LapTime>>  getLapTimesByRace(@PathVariable Long raceId) {
        List<EntityModel<LapTime>> lapTimeEntityModels = lapTimeRepository.findAllLapTimesByRace(raceId).stream()
                .map(lapTimeModelAssembler::toModel)
                .collect(Collectors.toList());

        if (lapTimeEntityModels.isEmpty()) {
            throw new LapTimeNotFoundException("Lap times not found for raceId " + raceId);
        }

        return CollectionModel.of(
                lapTimeEntityModels,
                linkTo(methodOn(LapTimeController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/lap-times/driver-race")
    public CollectionModel<EntityModel<LapTime>>  getAllLapTimesForADriverInARace(@RequestParam(value="driverId") Long driverId, @RequestParam(value="raceId") Long raceId) {
        List<EntityModel<LapTime>> lapTimeEntityModels = lapTimeRepository.findAllLapTimesByADriverInARace(driverId, raceId).stream()
                .map(lapTimeModelAssembler::toModel)
                .collect(Collectors.toList());

        if (lapTimeEntityModels.isEmpty()) {
            throw new LapTimeNotFoundException("Lap times not found for driverId " + driverId + " and raceId " + raceId);
        }

        return CollectionModel.of(
                lapTimeEntityModels,
                linkTo(methodOn(LapTimeController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/lap-times/driver-race-lap")
    public CollectionModel<EntityModel<LapTime>>  getSpecificLapTimeForADriverInARace(@RequestParam(value="driverId") Long driverId, @RequestParam(value="raceId") Long raceId, @RequestParam(value="lap") int lap) {
        List<EntityModel<LapTime>> lapTimeEntityModels = lapTimeRepository.findSpecificLapTimeByADriverInARace(driverId, raceId, lap).stream()
                .map(lapTimeModelAssembler::toModel)
                .collect(Collectors.toList());

        if (lapTimeEntityModels.isEmpty()) {
            throw new LapTimeNotFoundException("Lap times not found for driverId " + driverId + " and raceId " + raceId + " and lap " + lap);
        }

        return CollectionModel.of(
                lapTimeEntityModels,
                linkTo(methodOn(LapTimeController.class)).withSelfRel()
        );
    }
}
