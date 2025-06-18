package com.iitb.coursesapi.service;

import com.iitb.coursesapi.dto.CourseInstanceRequestDto;
import com.iitb.coursesapi.dto.CourseInstanceResponseDto;
import com.iitb.coursesapi.exception.CourseNotFoundException;
import com.iitb.coursesapi.exception.DuplicateCourseException;
import com.iitb.coursesapi.model.Course;
import com.iitb.coursesapi.model.CourseInstance;
import com.iitb.coursesapi.model.CourseInstanceId;
import com.iitb.coursesapi.repository.CourseInstanceRepository;
import com.iitb.coursesapi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseInstanceService {

    private final CourseInstanceRepository courseInstanceRepository;
    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<CourseInstanceResponseDto> getInstancesByYearAndSemester(Integer year, Integer semester) {
        List<CourseInstance> instances = courseInstanceRepository.findByYearAndSemesterWithCourse(year, semester);
        return instances.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseInstanceResponseDto getInstanceByYearSemesterAndCourseId(Integer year, Integer semester, String courseId) {
        CourseInstance instance = findInstanceByYearSemesterAndCourseId(year, semester, courseId);
        return mapToResponseDto(instance);
    }

    @Transactional
    public CourseInstanceResponseDto createInstance(CourseInstanceRequestDto requestDto) {
        // Find the course
        Course course = courseRepository.findByCourseId(requestDto.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + requestDto.getCourseId() + " not found"));

        // Check if instance already exists
        CourseInstanceId instanceId = new CourseInstanceId(course.getId(), requestDto.getYear(), requestDto.getSemester());
        if (courseInstanceRepository.existsById(instanceId)) {
            throw new DuplicateCourseException("Course instance for " + requestDto.getCourseId() + 
                    " in year " + requestDto.getYear() + ", semester " + requestDto.getSemester() + 
                    " already exists");
        }

        CourseInstance instance = new CourseInstance();
        instance.setId(instanceId);
        instance.setCourse(course);
        instance.setAdditionalInfo(requestDto.getAdditionalInfo());

        CourseInstance savedInstance = courseInstanceRepository.save(instance);
        return mapToResponseDto(savedInstance);
    }

    @Transactional
    public void deleteInstance(Integer year, Integer semester, String courseId) {
        CourseInstance instance = findInstanceByYearSemesterAndCourseId(year, semester, courseId);
        courseInstanceRepository.delete(instance);
    }

    private CourseInstance findInstanceByYearSemesterAndCourseId(Integer year, Integer semester, String courseId) {
        return courseInstanceRepository.findByYearAndSemesterAndCourseId(year, semester, courseId)
                .orElseThrow(() -> new CourseNotFoundException(
                        "Course instance for course ID " + courseId + 
                        " in year " + year + ", semester " + semester + 
                        " not found"));
    }

    private CourseInstanceResponseDto mapToResponseDto(CourseInstance instance) {
        CourseInstanceResponseDto dto = new CourseInstanceResponseDto();
        dto.setCourseId(instance.getCourse().getCourseId());
        dto.setTitle(instance.getCourse().getTitle());
        dto.setDescription(instance.getCourse().getDescription());
        dto.setYear(instance.getId().getYear());
        dto.setSemester(instance.getId().getSemester());
        dto.setAdditionalInfo(instance.getAdditionalInfo());
        return dto;
    }
}
