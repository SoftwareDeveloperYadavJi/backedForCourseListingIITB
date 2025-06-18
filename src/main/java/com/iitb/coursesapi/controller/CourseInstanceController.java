package com.iitb.coursesapi.controller;

import com.iitb.coursesapi.dto.CourseInstanceRequestDto;
import com.iitb.coursesapi.dto.CourseInstanceResponseDto;
import com.iitb.coursesapi.service.CourseInstanceService;
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
@RequestMapping("/api/instances")
@RequiredArgsConstructor
@Tag(name = "Course Instances API", description = "API for managing course instances")
public class CourseInstanceController {

    private final CourseInstanceService courseInstanceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a course instance", description = "Creates a new instance of course delivery")
    @ApiResponse(responseCode = "201", description = "Course instance created")
    @ApiResponse(responseCode = "404", description = "Course not found")
    @ApiResponse(responseCode = "409", description = "Course instance already exists")
    public ResponseEntity<CourseInstanceResponseDto> createInstance(
            @Valid @RequestBody CourseInstanceRequestDto requestDto) {
        CourseInstanceResponseDto createdInstance = courseInstanceService.createInstance(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInstance);
    }

    @GetMapping("/{year}/{semester}")
    @Operation(summary = "Get course instances by year and semester", 
               description = "Returns all course instances for a specific year and semester")
    @ApiResponse(responseCode = "200", description = "List of course instances")
    public ResponseEntity<List<CourseInstanceResponseDto>> getInstancesByYearAndSemester(
            @PathVariable Integer year,
            @PathVariable Integer semester) {
        List<CourseInstanceResponseDto> instances = 
                courseInstanceService.getInstancesByYearAndSemester(year, semester);
        return ResponseEntity.ok(instances);
    }

    @GetMapping("/{year}/{semester}/{courseId}")
    @Operation(summary = "Get specific course instance", 
               description = "Returns details of a specific course instance")
    @ApiResponse(responseCode = "200", description = "Course instance found")
    @ApiResponse(responseCode = "404", description = "Course instance not found")
    public ResponseEntity<CourseInstanceResponseDto> getInstanceByYearSemesterAndCourseId(
            @PathVariable Integer year,
            @PathVariable Integer semester,
            @PathVariable String courseId) {
        CourseInstanceResponseDto instance = 
                courseInstanceService.getInstanceByYearSemesterAndCourseId(year, semester, courseId);
        return ResponseEntity.ok(instance);
    }

    @DeleteMapping("/{year}/{semester}/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a course instance", 
               description = "Deletes a specific course instance")
    @ApiResponse(responseCode = "204", description = "Course instance deleted")
    @ApiResponse(responseCode = "404", description = "Course instance not found")
    public ResponseEntity<Void> deleteInstance(
            @PathVariable Integer year,
            @PathVariable Integer semester,
            @PathVariable String courseId) {
        courseInstanceService.deleteInstance(year, semester, courseId);
        return ResponseEntity.noContent().build();
    }
}
