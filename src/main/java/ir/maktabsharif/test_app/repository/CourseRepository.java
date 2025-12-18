package ir.maktabsharif.test_app.repository;

import ir.maktabsharif.test_app.model.Course;

import java.util.Optional;

public interface CourseRepository extends BaseRepository<Long, Course> {
    Optional<Course> findByCourseCode(String courseCode);
}
