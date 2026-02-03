package ir.maktabsharif.test_app.dto.exam;

import ir.maktabsharif.test_app.model.Course;
import ir.maktabsharif.test_app.model.enums.EventStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamUserResponse {
    private String examCode;
    private String title;
    private String description;
    private Integer durationMinutes;
    private Course course;
    private EventStatus eventStatus;
    private boolean examBeenDone;
}
