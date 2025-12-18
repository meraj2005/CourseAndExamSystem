package ir.maktabsharif.test_app.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseResponse {
    private Long id;
    private String title;
    private String courseCode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Long teacherId;
    private String teacherName;
}
