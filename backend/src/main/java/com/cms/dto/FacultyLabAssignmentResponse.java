package com.cms.dto;

public record FacultyLabAssignmentResponse(
        Long id,
        Long facultyId,
        String employeeId,
        Long labId,
        String labName,
        Long courseId,
        String courseName,
        Long academicYearId,
        String academicYearName,
        Long semesterId,
        String semesterName
) {
}
