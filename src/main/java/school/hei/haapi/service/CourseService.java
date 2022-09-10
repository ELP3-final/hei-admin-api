package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.Course;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.model.validator.CourseValidator;
import school.hei.haapi.repository.CourseRepository;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepository repository;

    private final CourseValidator courseValidator;

    public List<Course> getAll(String name, String ref, PageFromOne page, BoundedPageSize pageSize) {
        Pageable pageable = PageRequest.of(
                page.getValue() - 1,
                pageSize.getValue(),
                Sort.by(DESC, "dueDatetime")
        );
        return repository.getByCriteria(
                name,
                ref,
                pageable
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
