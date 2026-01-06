package ir.maktabsharif.test_app.dto.question;

import lombok.Data;

@Data
public class OptionRequest {
    private String text;
    private boolean correct;

}
