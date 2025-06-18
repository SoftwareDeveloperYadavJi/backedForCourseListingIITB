package com.iitb.coursesapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_instances")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInstance {

    @EmbeddedId
    private CourseInstanceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "additional_info")
    private String additionalInfo;
    
    // Constructor for easier creation
    public CourseInstance(Course course, int year, int semester) {
        this.id = new CourseInstanceId(course.getId(), year, semester);
        this.course = course;
    }
}
