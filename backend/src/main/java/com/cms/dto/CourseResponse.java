package com.cms.dto;

import com.cms.model.enums.CourseType;

public record CourseResponse(
        Long id,
        String name,
        String code,
        Long programId,
        String programName,
        Long departmentId,
        String departmentName,
        int credits,
        int theoryHours,
        int practicalHours,
        CourseType courseType,
        int semesterNumber,
        boolean active
) {
}
