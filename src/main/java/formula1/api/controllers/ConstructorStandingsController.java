package formula1.api.controllers;

import formula1.api.assembler.ConstructorStandingsModelAssembler;
import formula1.api.entities.ConstructorStandings;
import formula1.api.exceptions.constructor.standings.ConstructorStandingsNotFoundException;
import formula1.api.exceptions.constructor.standings.ConstructorStandingsNotFoundForConstructorException;
import formula1.api.exceptions.constructor.standings.ConstructorStandingsNotFoundForRaceException;
import formula1.api.repositories.ConstructorStandingsRepository;
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
public class ConstructorStandingsController {

    private ConstructorStandingsRepository constructorStandingsRepository;
    private ConstructorStandingsModelAssembler constructorStandingsModelAssembler;

    public ConstructorStandingsController(ConstructorStandingsRepository constructorStandingsRepository, ConstructorStandingsModelAssembler constructorStandingsModelAssembler) {
        this.constructorStandingsRepository = constructorStandingsRepository;
        this.constructorStandingsModelAssembler = constructorStandingsModelAssembler;
    }

    @GetMapping("/api/constructor-standings")
    public CollectionModel<EntityModel<ConstructorStandings>> getAllConstructorStandings() {
        List<EntityModel<ConstructorStandings>> constructorStandingsEntityModelList = constructorStandingsRepository.findAll().stream()
                .map(constructorStandingsModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                constructorStandingsEntityModelList,
                linkTo(methodOn(ConstructorStandingsController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/constructor-standings/{id}")
    public EntityModel<ConstructorStandings> getConstructorStandingsById(@PathVariable Long id) {
        ConstructorStandings constructorStandings = constructorStandingsRepository.findById(id)
                .orElseThrow(() -> new ConstructorStandingsNotFoundException(id));

        return constructorStandingsModelAssembler.toModel(constructorStandings);
    }

    @GetMapping("/api/constructor-standings/constructor/{constructorId}")
    public CollectionModel<EntityModel<ConstructorStandings>> getConstructorStandingsForConstructor(@PathVariable Long constructorId) {
        List<EntityModel<ConstructorStandings>> constructorStandingsEntityModelList = constructorStandingsRepository.findConstructorsForConstructor(constructorId)
                .stream()
                .map(constructorStandingsModelAssembler::toModel)
                .collect(Collectors.toList());

        if (constructorStandingsEntityModelList.isEmpty()) {
            throw new ConstructorStandingsNotFoundForConstructorException(constructorId);
        }

        return CollectionModel.of(
                constructorStandingsEntityModelList,
                linkTo(methodOn(ConstructorStandingsController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/constructor-standings/race/{raceId}")
    public CollectionModel<EntityModel<ConstructorStandings>> getConstructorStandingsForRace(@PathVariable Long raceId) {
        List<EntityModel<ConstructorStandings>> constructorStandingsEntityModelList = constructorStandingsRepository.findConstructorsForRace(raceId)
                .stream()
                .map(constructorStandingsModelAssembler::toModel)
                .collect(Collectors.toList());

        if (constructorStandingsEntityModelList.isEmpty()) {
            throw new ConstructorStandingsNotFoundForRaceException(raceId);
        }

        return CollectionModel.of(
                constructorStandingsEntityModelList,
                linkTo(methodOn(ConstructorStandingsController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/constructor-standings/most-wins")
    public EntityModel<ConstructorStandings> getConstructorStandingsWithMostWins() {
        ConstructorStandings constructorStandings = constructorStandingsRepository.findConstructorStandingsWithMostWins();
        return constructorStandingsModelAssembler.toModel(constructorStandings);
    }

    @GetMapping("/api/constructor-standings/most-points")
    public EntityModel<ConstructorStandings> getConstructorStandingsWithMostPoints() {
        ConstructorStandings constructorStandings = constructorStandingsRepository.findConstructorStandingsWithMostPoints();
        return constructorStandingsModelAssembler.toModel(constructorStandings);
    }
}
