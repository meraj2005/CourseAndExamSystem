package ir.maktabsharif.test_app.dto.question;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionRequest {
    private String text;
    private boolean correct;

}
