package school.hei.haapi.endpoint.rest.validator;

import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.CreateCourse;
import school.hei.haapi.model.User;
import school.hei.haapi.model.exception.BadRequestException;

import java.util.List;
import java.util.function.Consumer;

@Component
public class CreateCourseValidator implements Consumer<CreateCourse> {
    @Override
    public void accept(CreateCourse createCourse) {
        if (createCourse.getCredits() == null) {
            throw new BadRequestException("credits is mandatory");
        }
    }
}
