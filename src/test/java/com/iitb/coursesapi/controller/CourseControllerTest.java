package com.iitb.coursesapi.controller;

import com.iitb.coursesapi.dto.CourseRequestDto;
import com.iitb.coursesapi.dto.CourseResponseDto;
import com.iitb.coursesapi.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    public void getAllCourses_ShouldReturnCourses() throws Exception {
        // Arrange
        CourseResponseDto course1 = new CourseResponseDto(1L, "CS101", "Introduction to Programming", "Description",
                Collections.emptyList());
        CourseResponseDto course2 = new CourseResponseDto(2L, "CS102", "Advanced Programming", "Description",
                Collections.emptyList());
        List<CourseResponseDto> courses = Arrays.asList(course1, course2);

        when(courseService.getAllCourses()).thenReturn(courses);

        // Act & Assert
        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].courseId", is("CS101")))
                .andExpect(jsonPath("$[1].courseId", is("CS102")));
    }

    @Test
    public void createCourse_ShouldCreateCourse() throws Exception {
        // Arrange
        CourseResponseDto createdCourse = new CourseResponseDto(1L, "CS101", "Introduction to Programming",
                "Description", Collections.emptyList());

        when(courseService.createCourse(any(CourseRequestDto.class))).thenReturn(createdCourse);

        // Act & Assert
        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"courseId\":\"CS101\",\"title\":\"Introduction to Programming\",\"description\":\"Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.courseId", is("CS101")))
                .andExpect(jsonPath("$.title", is("Introduction to Programming")));
    }

    @Test
    public void getCourseById_ShouldReturnCourse() throws Exception {
        // Arrange
        CourseResponseDto course = new CourseResponseDto(1L, "CS101", "Introduction to Programming", "Description",
                Collections.emptyList());

        when(courseService.getCourseById("CS101")).thenReturn(course);

        // Act & Assert
        mockMvc.perform(get("/api/courses/CS101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId", is("CS101")))
                .andExpect(jsonPath("$.title", is("Introduction to Programming")));
    }
}
