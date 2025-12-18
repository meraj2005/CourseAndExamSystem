package ir.maktabsharif.test_app.dto;

import ir.maktabsharif.test_app.dto.user.UserResponse;
import lombok.Data;

import java.util.Set;

@Data
public class CourseParticipantsResponse {
    private String courseCode;
    private String courseTitle;
    private Set<UserResponse> students;
    private UserResponse teacher;
}
