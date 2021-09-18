package formula1.api.assembler;

import formula1.api.controllers.CircuitController;
import formula1.api.entities.Circuit;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CircuitModelAssembler implements RepresentationModelAssembler<Circuit, EntityModel<Circuit>> {

    @Override
    public EntityModel<Circuit> toModel(Circuit circuit) {
        return EntityModel.of(
                circuit,
                linkTo(methodOn(CircuitController.class).getCircuitById(circuit.getId())).withSelfRel(),
                linkTo(methodOn(CircuitController.class).getCircuitByRef(circuit.getCircuitRef())).withSelfRel(),
                linkTo(methodOn(CircuitController.class).getAllCircuits()).withRel("circuits")
        );
    }
}
