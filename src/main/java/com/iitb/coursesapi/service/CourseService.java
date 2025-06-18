package com.iitb.coursesapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iitb.coursesapi.dto.CourseRequestDto;
import com.iitb.coursesapi.dto.CourseResponseDto;
import com.iitb.coursesapi.dto.PrerequisiteDto;
import com.iitb.coursesapi.exception.CourseNotFoundException;
import com.iitb.coursesapi.exception.DependencyExistsException;
import com.iitb.coursesapi.exception.DuplicateCourseException;
import com.iitb.coursesapi.exception.PrerequisiteNotFoundException;
import com.iitb.coursesapi.model.Course;
import com.iitb.coursesapi.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAllWithPrerequisites();
        return courses.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseResponseDto getCourseById(String courseId) {
        Course course = findCourseById(courseId);
        return mapToResponseDto(course);
    }

    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
        // Check if course already exists
        if (courseRepository.existsByCourseId(courseRequestDto.getCourseId())) {
            throw new DuplicateCourseException("Course with ID " + courseRequestDto.getCourseId() + " already exists");
        }

        Course course = new Course();
        course.setCourseId(courseRequestDto.getCourseId());
        course.setTitle(courseRequestDto.getTitle());
        course.setDescription(courseRequestDto.getDescription());

        // Add prerequisites if provided
        if (courseRequestDto.getPrerequisiteCourseIds() != null
                && !courseRequestDto.getPrerequisiteCourseIds().isEmpty()) {
            List<Course> prerequisites = new ArrayList<>();
            List<String> invalidPrerequisites = new ArrayList<>();

            for (String prerequisiteId : courseRequestDto.getPrerequisiteCourseIds()) {
                courseRepository.findByCourseId(prerequisiteId)
                        .ifPresentOrElse(prerequisites::add, () -> invalidPrerequisites.add(prerequisiteId));
            }

            // Check if all prerequisites exist
            if (!invalidPrerequisites.isEmpty()) {
                throw new PrerequisiteNotFoundException("The following prerequisite courses do not exist: " +
                        String.join(", ", invalidPrerequisites));
            }

            course.setPrerequisites(prerequisites);
        }

        Course savedCourse = courseRepository.save(course);
        return mapToResponseDto(savedCourse);
    }

    @Transactional
    public void deleteCourse(String courseId) {
        Course course = findCourseById(courseId);

        // Check if course is a prerequisite for any other course
        if (courseRepository.isPrerequisiteForAnyCourse(course.getId())) {
            throw new DependencyExistsException(
                    "Cannot delete course " + courseId + " as it is a prerequisite for other courses");
        }

        courseRepository.delete(course);
    }

    private Course findCourseById(String courseId) {
        return courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + courseId + " not found"));
    }

    private CourseResponseDto mapToResponseDto(Course course) {
        CourseResponseDto dto = new CourseResponseDto();
        dto.setId(course.getId());
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());

        // Map prerequisites
        List<PrerequisiteDto> prerequisiteDtos = course.getPrerequisites().stream()
                .map(prerequisite -> {
                    PrerequisiteDto prerequisiteDto = new PrerequisiteDto();
                    prerequisiteDto.setId(prerequisite.getId());
                    prerequisiteDto.setCourseId(prerequisite.getCourseId());
                    prerequisiteDto.setTitle(prerequisite.getTitle());
                    return prerequisiteDto;
                })
                .collect(Collectors.toList());

        dto.setPrerequisites(prerequisiteDtos);
        return dto;
    }
}
