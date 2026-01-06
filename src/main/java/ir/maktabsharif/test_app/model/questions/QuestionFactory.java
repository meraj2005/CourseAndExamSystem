package ir.maktabsharif.test_app.model.questions;

import ir.maktabsharif.test_app.dto.question.QuestionCreateRequest;
import ir.maktabsharif.test_app.exceptions.BusinessException;
import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionFactory {

    public Question create(
            QuestionCreateRequest request,
            User teacher,
            Course course
    ) {

        return switch (request.getType()) {

            case "MCQ" -> {
                MultipleChoiceQuestions q = new MultipleChoiceQuestions();
                q.setTitle(request.getTitle());
                q.setText(request.getText());
                q.setTeacher(teacher);
                q.setCourse(course);

                List<Option> options = request.getOptions()
                        .stream()
                        .map(o -> {
                            Option opt = new Option();
                            opt.setText(o.getText());
                            opt.setCorrect(o.isCorrect());
                            return opt;
                        })
                        .toList();

                q.setOptions(options);
                yield q;
            }

            case "DESCRIPTIVE" -> {
                AnnotationQuestions q = new AnnotationQuestions();
                q.setTitle(request.getTitle());
                q.setText(request.getText());
                q.setTeacher(teacher);
                q.setCourse(course);
                yield q;
            }

            default -> throw new BusinessException("Unsupported question type");
        };
    }
}
