package ir.maktabsharif.test_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StartExamResponse {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long remainingSeconds;
}
