package formula1.api.assembler;

import formula1.api.controllers.ConstructorResultsController;
import formula1.api.entities.ConstructorResults;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConstructorResultsModelAssembler implements RepresentationModelAssembler<ConstructorResults, EntityModel<ConstructorResults>> {

    @Override
    public EntityModel<ConstructorResults> toModel(ConstructorResults constructorResults) {
        return EntityModel.of(
                constructorResults,
                linkTo(methodOn(ConstructorResultsController.class).getConstructorResultsById(constructorResults.getConstructorResultsId())).withSelfRel(),
                linkTo(methodOn(ConstructorResultsController.class).getAllConstructorResults()).withRel("constructors-results")
        );
    }
}
