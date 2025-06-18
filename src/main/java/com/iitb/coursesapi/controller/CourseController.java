package com.iitb.coursesapi.controller;

import com.iitb.coursesapi.dto.CourseRequestDto;
import com.iitb.coursesapi.dto.CourseResponseDto;
import com.iitb.coursesapi.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Courses API", description = "API for managing courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @Operation(summary = "Get all courses", description = "Returns a list of all courses with their prerequisites")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    @Operation(summary = "Get course by ID", description = "Returns details of a specific course including prerequisites")
    @ApiResponse(responseCode = "200", description = "Course found")
    @ApiResponse(responseCode = "404", description = "Course not found")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable String courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new course", description = "Creates a new course with optional prerequisites")
    @ApiResponse(responseCode = "201", description = "Course created")
    @ApiResponse(responseCode = "400", description = "Invalid prerequisite course ID")
    @ApiResponse(responseCode = "409", description = "Course ID already exists")
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto courseRequestDto) {
        CourseResponseDto createdCourse = courseService.createCourse(courseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a course", description = "Deletes a course if it's not a prerequisite for other courses")
    @ApiResponse(responseCode = "204", description = "Course deleted successfully")
    @ApiResponse(responseCode = "404", description = "Course not found")
    @ApiResponse(responseCode = "409", description = "Course cannot be deleted as it's a prerequisite for other courses")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
