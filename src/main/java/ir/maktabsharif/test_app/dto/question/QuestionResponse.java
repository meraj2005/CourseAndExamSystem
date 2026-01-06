package ir.maktabsharif.test_app.dto.question;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionResponse {
    private String type;
    private String text;
    private List<OptionRequest> options;
}
