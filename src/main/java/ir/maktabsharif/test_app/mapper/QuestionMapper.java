package ir.maktabsharif.test_app.mapper;

import ir.maktabsharif.test_app.dto.question.OptionRequest;
import ir.maktabsharif.test_app.dto.question.QuestionResponse;
import ir.maktabsharif.test_app.model.questions.AnnotationQuestions;
import ir.maktabsharif.test_app.model.questions.MultipleChoiceQuestions;
import ir.maktabsharif.test_app.model.questions.Option;
import ir.maktabsharif.test_app.model.questions.Question;
import org.hibernate.Hibernate;

import java.util.List;

public class QuestionMapper {
    public static QuestionResponse toQuestionResponse(Question question) {

        Question realQuestion = (Question) Hibernate.unproxy(question);

        if (realQuestion instanceof MultipleChoiceQuestions mcq) {
            return QuestionResponse.builder()
                    .text(mcq.getText())
                    .options(
                            mcq.getOptions()
                                    .stream()
                                    .map(OptionMapper::toOptionRequest)
                                    .toList()
                    )
                    .build();
        }

        if (realQuestion instanceof AnnotationQuestions aq) {
            return QuestionResponse.builder()
                    .text(aq.getText())
                    .build();
        }

        throw new IllegalStateException(
                "Unsupported Question type: " + realQuestion.getClass().getName()
                        + ", id=" + realQuestion.getId()
        );
    }
}