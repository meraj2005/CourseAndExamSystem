package ir.maktabsharif.test_app.mapper;

import ir.maktabsharif.test_app.dto.AnswerResponse;
import ir.maktabsharif.test_app.model.StudentAnswerDraft;

public class AnswerMapper {
    public static AnswerResponse toDTO(StudentAnswerDraft studentAnswerDraft){
        return AnswerResponse.builder()
                .id(studentAnswerDraft.getId())
                .name(studentAnswerDraft.getSession().getStudent().getFirstName()+studentAnswerDraft.getSession().getStudent().getLastName())
                .StudentId(studentAnswerDraft.getSession().getStudent().getId())
                .questionId(studentAnswerDraft.getQuestion().getId())
                .answer(studentAnswerDraft.getAnswer())
                .build();
    }
}
