package com.iitb.coursesapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstanceId implements Serializable {

    @Column(name = "course_id")
    private Long courseId;    @NotNull(message = "Year is required")
    @Min(value = 2000, message = "Year must be 2000 or later")
    @Column(name = "academic_year") // Changed column name to avoid reserved keyword
    private Integer year;

    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be 1 or greater")
    @Max(value = 3, message = "Semester must be 3 or less")
    private Integer semester;
}
