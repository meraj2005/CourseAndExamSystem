package ir.maktabsharif.test_app.mapper;

import ir.maktabsharif.test_app.dto.CourseCreateRequest;
import ir.maktabsharif.test_app.dto.CourseResponse;
import ir.maktabsharif.test_app.model.Course;

public class CourseMapper {
    public static CourseResponse toDTO (Course course){
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .courseCode(course.getCourseCode())
                .startTime(course.getStartTime())
                .endTime(course.getEndTime())
                .teacherId(course.getTeacher().getId())
                .teacherName(course.getTeacher().getFirstName()+" "+course.getTeacher().getLastName())
                .build();

    }
    public static Course toEntity (CourseCreateRequest courseCreateRequest){
        return Course.builder()
                .courseCode(courseCreateRequest.getCourseCode())
                .title(courseCreateRequest.getTitle())
                .startTime(courseCreateRequest.getStartTime())
                .endTime(courseCreateRequest.getEndTime())
                .build();
    }
}
