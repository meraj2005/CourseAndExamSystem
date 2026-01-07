package ir.maktabsharif.test_app.model.questions;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Option {
    private String text;
    private boolean correct;
}
