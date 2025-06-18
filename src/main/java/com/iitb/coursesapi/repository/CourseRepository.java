package com.iitb.coursesapi.repository;

import com.iitb.coursesapi.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Optional<Course> findByCourseId(String courseId);
    
    boolean existsByCourseId(String courseId);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Course c JOIN c.prerequisites p WHERE p.id = :courseId")
    boolean isPrerequisiteForAnyCourse(@Param("courseId") Long courseId);
    
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.prerequisites")
    List<Course> findAllWithPrerequisites();
}
