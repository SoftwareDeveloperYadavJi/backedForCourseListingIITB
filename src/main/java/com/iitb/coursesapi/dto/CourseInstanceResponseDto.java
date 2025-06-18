package com.iitb.coursesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstanceResponseDto {
    private String courseId;
    private String title;
    private String description;
    private Integer year;
    private Integer semester;
    private String additionalInfo;
}
