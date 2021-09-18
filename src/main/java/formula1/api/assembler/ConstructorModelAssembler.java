package formula1.api.assembler;

import formula1.api.controllers.ConstructorController;
import formula1.api.entities.Constructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConstructorModelAssembler implements RepresentationModelAssembler<Constructor, EntityModel<Constructor>> {

    @Override
    public EntityModel<Constructor> toModel(Constructor constructor) {
        return EntityModel.of(
                constructor,
                linkTo(methodOn(ConstructorController.class).getConstructorById(constructor.getConstructorId())).withSelfRel(),
                linkTo(methodOn(ConstructorController.class).getConstructorByRef(constructor.getConstructorRef())).withSelfRel(),
                linkTo(methodOn(ConstructorController.class).getAllConstructors()).withRel("constructors")
        );
    }
}