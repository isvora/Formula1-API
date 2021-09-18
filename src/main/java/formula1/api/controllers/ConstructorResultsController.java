package formula1.api.controllers;

import formula1.api.assembler.ConstructorResultsModelAssembler;
import formula1.api.entities.ConstructorResults;
import formula1.api.exceptions.constructor.results.ConstructorResultsNotFoundByRaceIdException;
import formula1.api.exceptions.constructor.results.ConstructorResultsNotFoundException;
import formula1.api.repositories.ConstructorResultsRepository;
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
public class ConstructorResultsController {

    private final ConstructorResultsRepository constructorResultsRepository;
    private final ConstructorResultsModelAssembler constructorResultsModelAssembler;

    public ConstructorResultsController(ConstructorResultsRepository constructorResultsRepository, ConstructorResultsModelAssembler constructorResultsModelAssembler) {
        this.constructorResultsRepository = constructorResultsRepository;
        this.constructorResultsModelAssembler = constructorResultsModelAssembler;
    }

    @GetMapping("/api/constructor-results")
    public CollectionModel<EntityModel<ConstructorResults>> getAllConstructorResults() {
        List<EntityModel<ConstructorResults>> constructorResultsEntityModels = constructorResultsRepository.findAll().stream()
                .map(constructorResultsModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                constructorResultsEntityModels,
                linkTo(methodOn(ConstructorResultsController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/constructor-results/{id}")
    public EntityModel<ConstructorResults> getConstructorResultsById(@PathVariable Long id) {
        ConstructorResults constructorResults = constructorResultsRepository.findById(id)
                .orElseThrow(() -> new ConstructorResultsNotFoundException(id));

        return constructorResultsModelAssembler.toModel(constructorResults);
    }

    @GetMapping("/api/constructor-results/race/{raceId}")
    public CollectionModel<EntityModel<ConstructorResults>> getConstructorResultsForRace(@PathVariable Long raceId) {
        List<EntityModel<ConstructorResults>> constructorResultsEntityModels = constructorResultsRepository.findConstructorResultsForRace(raceId)
                .stream()
                .map(constructorResultsModelAssembler::toModel)
                .collect(Collectors.toList());

        if (constructorResultsEntityModels.isEmpty()) {
            throw new ConstructorResultsNotFoundByRaceIdException(raceId);
        }

        return CollectionModel.of(
                constructorResultsEntityModels,
                linkTo(methodOn(ConstructorResultsController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/constructor-results/constructor/{constructorId}")
    public CollectionModel<EntityModel<ConstructorResults>> getConstructorResultsForConstructor(@PathVariable Long constructorId) {
        List<EntityModel<ConstructorResults>> constructorResultsEntityModels = constructorResultsRepository.findConstructorsResultsForConstructor(constructorId)
                .stream()
                .map(constructorResultsModelAssembler::toModel)
                .collect(Collectors.toList());

        if (constructorResultsEntityModels.isEmpty()) {
            throw new ConstructorResultsNotFoundByRaceIdException(constructorId);
        }

        return CollectionModel.of(
                constructorResultsEntityModels,
                linkTo(methodOn(ConstructorResultsController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/constructor-results/most-wins")
    public EntityModel<ConstructorResults> getConstructorResultsWithMostWins() {
        ConstructorResults constructorResults = constructorResultsRepository.findConstructorResultsWithMostWins();
        return constructorResultsModelAssembler.toModel(constructorResults);
    }
}
