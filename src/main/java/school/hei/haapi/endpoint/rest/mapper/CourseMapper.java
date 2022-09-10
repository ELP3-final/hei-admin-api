package school.hei.haapi.endpoint.rest.mapper;

import org.springframework.stereotype.Component;
import school.hei.haapi.model.Course;

@Component
public class CourseMapper {

    public school.hei.haapi.endpoint.rest.model.Course toRestCourse(Course course) {
        var restCourse = new school.hei.haapi.endpoint.rest.model.Course();
        restCourse.setId(course.getId());
        restCourse.setName(course.getName());
        restCourse.setRef(course.getRef());
        restCourse.setCredits(course.getCredits());
        restCourse.setTotalHours(course.getTotalHours());
        return restCourse;
    }

    public Course toDomainCourse(school.hei.haapi.endpoint.rest.model.Course restCourse) {
        return Course.builder()
                .id(restCourse.getId())
                .name(restCourse.getName())
                .ref(restCourse.getRef())
                .credits(restCourse.getCredits())
                .totalHours(restCourse.getTotalHours())
                .build();
    }
}
