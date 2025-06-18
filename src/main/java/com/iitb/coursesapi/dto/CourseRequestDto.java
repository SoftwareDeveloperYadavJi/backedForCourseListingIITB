package com.iitb.coursesapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {
    
    @NotBlank(message = "Course ID is required")
    private String courseId;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    private List<String> prerequisiteCourseIds = new ArrayList<>();
}
