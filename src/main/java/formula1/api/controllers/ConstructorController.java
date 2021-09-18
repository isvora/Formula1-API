package formula1.api.controllers;

import formula1.api.assembler.ConstructorModelAssembler;
import formula1.api.entities.Constructor;
import formula1.api.exceptions.circuit.CircuitNotFoundException;
import formula1.api.exceptions.circuit.CircuitNotFoundForNationalityException;
import formula1.api.repositories.ConstructorRepository;
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
public class ConstructorController {

    private final ConstructorRepository constructorRepository;
    private final ConstructorModelAssembler constructorModelAssembler;

    public ConstructorController(ConstructorRepository constructorRepository, ConstructorModelAssembler constructorModelAssembler) {
        this.constructorRepository = constructorRepository;
        this.constructorModelAssembler = constructorModelAssembler;
    }

    @GetMapping("/api/constructors")
    public CollectionModel<EntityModel<Constructor>> getAllConstructors() {
        List<EntityModel<Constructor>> constructorEntityModels = constructorRepository.findAll().stream()
                .map(constructorModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                constructorEntityModels,
                linkTo(methodOn(ConstructorController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/constructors/{id}")
    public EntityModel<Constructor> getConstructorById(@PathVariable Long id) {
        Constructor constructor = constructorRepository.findById(id)
                .orElseThrow(() -> new CircuitNotFoundException(id));

        return constructorModelAssembler.toModel(constructor);
    }

    @GetMapping("/api/constructors/")
    public EntityModel<Constructor> getConstructorByRef(@RequestParam(value="ref") String ref) {
        Constructor constructor = constructorRepository.findByRef(ref);
        return constructorModelAssembler.toModel(constructor);
    }

    @GetMapping("/api/constructors/nationality/{nationality}")
    public CollectionModel<EntityModel<Constructor>> getConstructorByNationality(@PathVariable String nationality) {
        List<EntityModel<Constructor>> constructorEntityModels = constructorRepository.findCircuitsByNationality(nationality)
                .stream()
                .map(constructorModelAssembler::toModel)
                .collect(Collectors.toList());

        if (constructorEntityModels.isEmpty()) {
            throw new CircuitNotFoundForNationalityException(nationality);
        }

        return CollectionModel.of(
                constructorEntityModels,
                linkTo(methodOn(ConstructorController.class)).withSelfRel()
        );
    }

}
