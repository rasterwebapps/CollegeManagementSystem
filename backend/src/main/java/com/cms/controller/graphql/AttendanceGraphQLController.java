package com.cms.controller.graphql;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.TheoryAttendance;
import com.cms.repository.CourseRepository;
import com.cms.repository.SemesterRepository;
import com.cms.repository.StudentProfileRepository;
import com.cms.service.TheoryAttendanceService;

@Controller
public class AttendanceGraphQLController {

    private final TheoryAttendanceService theoryAttendanceService;
    private final StudentProfileRepository studentProfileRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;

    public AttendanceGraphQLController(TheoryAttendanceService theoryAttendanceService,
                                        StudentProfileRepository studentProfileRepository,
                                        CourseRepository courseRepository,
                                        SemesterRepository semesterRepository) {
        this.theoryAttendanceService = theoryAttendanceService;
        this.studentProfileRepository = studentProfileRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
    }

    @QueryMapping
    public List<TheoryAttendance> theoryAttendance(@Argument Long studentId, @Argument Long courseId) {
        return theoryAttendanceService.findByStudentAndCourse(studentId, courseId);
    }

    @QueryMapping
    public List<TheoryAttendance> theoryAttendanceBySemester(@Argument Long studentId, @Argument Long semesterId) {
        return theoryAttendanceService.findByStudentAndSemester(studentId, semesterId);
    }

    @QueryMapping
    public List<TheoryAttendance> theoryAttendanceByDate(@Argument Long courseId, @Argument String date) {
        return theoryAttendanceService.findByCourseAndDate(courseId, LocalDate.parse(date));
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public TheoryAttendance recordTheoryAttendance(@Argument Map<String, Object> input) {
        TheoryAttendance attendance = new TheoryAttendance();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        attendance.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        Long courseId = Long.valueOf(input.get("courseId").toString());
        attendance.setCourse(courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course", courseId)));
        Long semesterId = Long.valueOf(input.get("semesterId").toString());
        attendance.setSemester(semesterRepository.findById(semesterId)
            .orElseThrow(() -> new ResourceNotFoundException("Semester", semesterId)));
        attendance.setAttendanceDate(LocalDate.parse((String) input.get("attendanceDate")));
        attendance.setStatus((String) input.get("status"));
        attendance.setRemarks((String) input.get("remarks"));
        return theoryAttendanceService.record(attendance);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public TheoryAttendance updateTheoryAttendance(@Argument Long id, @Argument String status) {
        return theoryAttendanceService.updateStatus(id, status);
    }
}
