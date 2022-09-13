package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.Course;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.model.validator.CourseValidator;
import school.hei.haapi.repository.CourseRepository;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@AllArgsConstructor
@CrossOrigin
public class CourseService {
    private final CourseRepository repository;

    private final CourseValidator courseValidator;

    public List<Course> getAll(String name, String ref) {
        return repository.getByCriteria(
                name,
                ref
        );
    }

    public Course getById(String id) {
        return repository.getById(id);
    }

    public Course save(Course course) {
        courseValidator.accept(course);
        return repository.save(course);
    }
}
