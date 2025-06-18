package com.iitb.coursesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String courseId;
    private String title;
    private String description;
    private List<PrerequisiteDto> prerequisites = new ArrayList<>();
}
