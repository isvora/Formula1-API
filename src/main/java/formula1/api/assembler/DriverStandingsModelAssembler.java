package formula1.api.assembler;

import formula1.api.controllers.DriverStandingsController;
import formula1.api.entities.DriverStandings;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DriverStandingsModelAssembler implements RepresentationModelAssembler<DriverStandings, EntityModel<DriverStandings>> {

    @Override
    public EntityModel<DriverStandings> toModel(DriverStandings driverStandings) {
        return EntityModel.of(
                driverStandings,
                linkTo(methodOn(DriverStandingsController.class).getDriverStandingsById(driverStandings.getDriverStandingsId())).withSelfRel(),
                linkTo(methodOn(DriverStandingsController.class).getAllDriverStandings()).withRel("driver-standings")
        );
    }
}
