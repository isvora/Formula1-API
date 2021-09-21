package formula1.api.assembler;

import formula1.api.controllers.ConstructorStandingsController;
import formula1.api.entities.ConstructorStandings;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConstructorStandingsModelAssembler implements RepresentationModelAssembler<ConstructorStandings, EntityModel<ConstructorStandings>> {
    @Override
    public EntityModel<ConstructorStandings> toModel(ConstructorStandings constructorStandings) {
        return EntityModel.of(
                constructorStandings,
                linkTo(methodOn(ConstructorStandingsController.class).getConstructorStandingsById(constructorStandings.getId())).withSelfRel(),
                linkTo(methodOn(ConstructorStandingsController.class).getAllConstructorStandings()).withRel("constructors-standings")
        );
    }
}
