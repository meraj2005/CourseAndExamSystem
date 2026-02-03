package ir.maktabsharif.test_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveAnswerRequest {

    @NotNull
    private Long questionId;

    private List<String> answer;
}
