package com.iitb.coursesapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
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
