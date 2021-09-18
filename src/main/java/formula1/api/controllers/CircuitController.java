package formula1.api.controllers;

import formula1.api.assembler.CircuitModelAssembler;
import formula1.api.entities.Circuit;
import formula1.api.exceptions.CircuitNotFoundException;
import formula1.api.repositories.CircuitRepository;
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
public class CircuitController {

    private final CircuitRepository circuitRepository;
    private final CircuitModelAssembler circuitModelAssembler;

    public CircuitController(CircuitRepository circuitRepository, CircuitModelAssembler circuitModelAssembler) {
        this.circuitRepository = circuitRepository;
        this.circuitModelAssembler = circuitModelAssembler;
    }

    @GetMapping("/api/circuits")
    public CollectionModel<EntityModel<Circuit>> getAllCircuits() {
        List<EntityModel<Circuit>> circuitEntityModels = circuitRepository.findAll().stream()
                .map(circuitModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                circuitEntityModels,
                linkTo(methodOn(CircuitController.class)).withSelfRel()
        );
    }

    @GetMapping("/api/circuits/{id}")
    public EntityModel<Circuit> getCircuitById(@PathVariable Long id) {
        Circuit circuit = circuitRepository.findById(id)
                .orElseThrow(() -> new CircuitNotFoundException("Circuit not found for id " + id));

        return circuitModelAssembler.toModel(circuit);
    }

    @GetMapping("/api/circuits/")
    public EntityModel<Circuit> getCircuitByRef(@RequestParam(value="ref") String ref) {
        Circuit circuit = circuitRepository.findCircuitByRef(ref);
        return circuitModelAssembler.toModel(circuit);
    }

    @GetMapping("/api/circuits/country/{country}")
    public CollectionModel<EntityModel<Circuit>> getCircuitsByCountry(@PathVariable String country) {
        List<EntityModel<Circuit>> circuitEntityModels = circuitRepository.findCircuitsByCountry(country)
                .stream()
                .map(circuitModelAssembler::toModel)
                .collect(Collectors.toList());

        if (circuitEntityModels.isEmpty()) {
            throw new CircuitNotFoundException("Circuit not found for country " + country);
        }

        return CollectionModel.of(
                circuitEntityModels,
                linkTo(methodOn(CircuitController.class)).withSelfRel()
        );
    }

}
