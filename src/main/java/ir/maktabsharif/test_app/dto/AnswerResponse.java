package ir.maktabsharif.test_app.dto;

import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnswerResponse {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Long questionId;
    @NotNull
    private Long StudentId;
    @NotBlank
    private List<String> answer;

}
