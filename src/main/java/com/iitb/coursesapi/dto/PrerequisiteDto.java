package com.iitb.coursesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrerequisiteDto {
    private Long id;
    private String courseId;
    private String title;
}
