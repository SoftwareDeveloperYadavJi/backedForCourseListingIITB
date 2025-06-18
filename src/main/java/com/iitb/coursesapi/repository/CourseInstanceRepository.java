package com.iitb.coursesapi.repository;

import com.iitb.coursesapi.model.CourseInstance;
import com.iitb.coursesapi.model.CourseInstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseInstanceRepository extends JpaRepository<CourseInstance, CourseInstanceId> {
    
    List<CourseInstance> findByIdYearAndIdSemester(Integer year, Integer semester);
    
    @Query("SELECT ci FROM CourseInstance ci JOIN FETCH ci.course WHERE ci.id.year = :year AND ci.id.semester = :semester")
    List<CourseInstance> findByYearAndSemesterWithCourse(@Param("year") Integer year, @Param("semester") Integer semester);
    
    @Query("SELECT ci FROM CourseInstance ci JOIN FETCH ci.course WHERE ci.id.year = :year AND ci.id.semester = :semester AND ci.course.courseId = :courseId")
    Optional<CourseInstance> findByYearAndSemesterAndCourseId(
            @Param("year") Integer year,
            @Param("semester") Integer semester,
            @Param("courseId") String courseId);
}
