package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.CourseMapper;
import school.hei.haapi.endpoint.rest.model.Course;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.service.CourseService;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CourseController {
    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @GetMapping("/courses")
    public List<Course> getCourses(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "ref", required = false, defaultValue = "") String ref
    ) {
        return courseService.getAll(name, ref).stream()
                .map(courseMapper::toRestCourse)
                .collect(toUnmodifiableList());
    }

    @GetMapping("/courses/{id}")
    public Course getCourseById(@PathVariable("id") String id) {
        return courseMapper.toRestCourse(courseService.getById(id));
    }

    @PutMapping(value = "/courses")
    public Course createOrUpdateCourse(@RequestBody Course toWrite) {
        var saved = courseService.save(courseMapper.toDomainCourse(toWrite));
        return courseMapper.toRestCourse(saved);
    }
}
