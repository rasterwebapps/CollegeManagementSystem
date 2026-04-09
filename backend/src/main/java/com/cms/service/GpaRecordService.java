package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.GpaRecordRequest;
import com.cms.dto.GpaRecordResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.GpaRecord;
import com.cms.model.Semester;
import com.cms.model.StudentProfile;
import com.cms.repository.GpaRecordRepository;
import com.cms.repository.SemesterRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class GpaRecordService {

    private final GpaRecordRepository gpaRecordRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final SemesterRepository semesterRepository;

    public GpaRecordService(GpaRecordRepository gpaRecordRepository,
                            StudentProfileRepository studentProfileRepository,
                            SemesterRepository semesterRepository) {
        this.gpaRecordRepository = gpaRecordRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<GpaRecordResponse> findAll() {
        return gpaRecordRepository.findAll().stream().map(this::toResponse).toList();
    }

    public GpaRecordResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public GpaRecordResponse create(GpaRecordRequest request) {
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        GpaRecord e = new GpaRecord();
        e.setStudent(student);
        e.setSemester(semester);
        e.setSemesterGpa(request.semesterGpa());
        e.setCgpa(request.cgpa());
        e.setTotalCredits(request.totalCredits());
        e.setEarnedCredits(request.earnedCredits());
        e.setLabComponentGpa(request.labComponentGpa());
        e.setCalculationDate(request.calculationDate());
        e.setStatus(request.status() != null ? request.status() : "CALCULATED");
        return toResponse(gpaRecordRepository.save(e));
    }

    public GpaRecordResponse update(Long id, GpaRecordRequest request) {
        GpaRecord e = getOrThrow(id);
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        e.setStudent(student);
        e.setSemester(semester);
        e.setSemesterGpa(request.semesterGpa());
        e.setCgpa(request.cgpa());
        e.setTotalCredits(request.totalCredits());
        e.setEarnedCredits(request.earnedCredits());
        e.setLabComponentGpa(request.labComponentGpa());
        e.setCalculationDate(request.calculationDate());
        if (request.status() != null) { e.setStatus(request.status()); }
        return toResponse(gpaRecordRepository.save(e));
    }

    public void delete(Long id) {
        if (!gpaRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("GpaRecord", id);
        }
        gpaRecordRepository.deleteById(id);
    }

    private GpaRecord getOrThrow(Long id) {
        return gpaRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("GpaRecord", id));
    }

    private GpaRecordResponse toResponse(GpaRecord e) {
        return new GpaRecordResponse(
            e.getId(),
            e.getStudent().getId(),
            e.getStudent().getFirstName() + " " + e.getStudent().getLastName(),
            e.getSemester().getId(), e.getSemester().getName(),
            e.getSemesterGpa(), e.getCgpa(),
            e.getTotalCredits(), e.getEarnedCredits(),
            e.getLabComponentGpa(), e.getCalculationDate(),
            e.getStatus(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
