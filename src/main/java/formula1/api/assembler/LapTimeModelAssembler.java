package formula1.api.assembler;

import formula1.api.controllers.LapTimeController;
import formula1.api.entities.LapTime;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LapTimeModelAssembler implements RepresentationModelAssembler<LapTime, EntityModel<LapTime>> {
    @Override
    public EntityModel<LapTime> toModel(LapTime lapTime) {
        return EntityModel.of(
                lapTime,
                linkTo(methodOn(LapTimeController.class).getLapTimesById(lapTime.getId())).withSelfRel(),
                linkTo(methodOn(LapTimeController.class).getAllLapTimes()).withRel("lap-times")
        );
    }
}
