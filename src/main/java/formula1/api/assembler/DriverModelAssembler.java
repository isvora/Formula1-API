package formula1.api.assembler;

import formula1.api.controllers.DriverController;
import formula1.api.entities.Driver;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DriverModelAssembler implements RepresentationModelAssembler<Driver, EntityModel<Driver>> {

    @Override
    public EntityModel<Driver> toModel(Driver driver) {
        return EntityModel.of(
                driver,
                linkTo(methodOn(DriverController.class).getDriverById(driver.getDriverId())).withSelfRel(),
                linkTo(methodOn(DriverController.class).getDriverByRef(driver.getDriverRef())).withSelfRel(),
                linkTo(methodOn(DriverController.class).getAllDrivers()).withRel("drivers")
        );
    }
}
