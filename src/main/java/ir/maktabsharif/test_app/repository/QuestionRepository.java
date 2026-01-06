package ir.maktabsharif.test_app.repository;

import ir.maktabsharif.test_app.dto.question.QuestionCreateRequest;
import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.model.questions.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTeacherAndCourse(User teacher, Course course);
}
