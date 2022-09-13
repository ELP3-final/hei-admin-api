package school.hei.haapi.model.validator;

import org.springframework.stereotype.Component;
import school.hei.haapi.model.Course;
import school.hei.haapi.model.exception.BadRequestException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class CourseValidator implements Consumer<Course> {
    public void accept(List<Course> courses) {
        courses.forEach(this::accept);
    }

    @Override
    public void accept(Course course) {
        Set<String> violationMessages = new HashSet<>();
        if (course.getCredits() == null) {
            violationMessages.add("credits is mandatory");
        }
        if (course.getTotalHours() < 0) {
            violationMessages.add("total must be positive");
        }
        if (course.getCredits() < 0) {
            violationMessages.add("credits must be positive");
        }
        if (!violationMessages.isEmpty()) {
            String formattedViolationMessages = violationMessages.stream()
                    .map(String::toString)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(formattedViolationMessages);
        }
    }
}
