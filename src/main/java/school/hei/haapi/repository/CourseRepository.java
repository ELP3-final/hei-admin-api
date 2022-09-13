package school.hei.haapi.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.hei.haapi.model.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {
        @Query("SELECT S from Course S where S.name like concat('%', :name, '%') and S.ref like concat('%', :ref, '%') ")
    List<Course> getByCriteria(String ref, String name);
}
