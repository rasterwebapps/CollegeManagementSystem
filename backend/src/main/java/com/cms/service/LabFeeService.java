package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.LabFeeRequest;
import com.cms.dto.LabFeeResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Lab;
import com.cms.model.LabFee;
import com.cms.model.Semester;
import com.cms.repository.CourseRepository;
import com.cms.repository.LabFeeRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.SemesterRepository;

@Service
public class LabFeeService {

    private final LabFeeRepository labFeeRepository;
    private final LabRepository labRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;

    public LabFeeService(LabFeeRepository labFeeRepository, LabRepository labRepository,
                         CourseRepository courseRepository, SemesterRepository semesterRepository) {
        this.labFeeRepository = labFeeRepository;
        this.labRepository = labRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<LabFeeResponse> findAll() {
        return labFeeRepository.findAll().stream().map(this::toResponse).toList();
    }

    public LabFeeResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public LabFeeResponse create(LabFeeRequest request) {
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        LabFee lf = new LabFee();
        lf.setLab(lab);
        lf.setCourse(course);
        lf.setSemester(semester);
        lf.setAmount(request.amount());
        lf.setDescription(request.description());
        lf.setStatus(request.status() != null ? request.status() : "ACTIVE");
        return toResponse(labFeeRepository.save(lf));
    }

    public LabFeeResponse update(Long id, LabFeeRequest request) {
        LabFee lf = getOrThrow(id);
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        lf.setLab(lab);
        lf.setCourse(course);
        lf.setSemester(semester);
        lf.setAmount(request.amount());
        lf.setDescription(request.description());
        if (request.status() != null) { lf.setStatus(request.status()); }
        return toResponse(labFeeRepository.save(lf));
    }

    public void delete(Long id) {
        if (!labFeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("LabFee", id);
        }
        labFeeRepository.deleteById(id);
    }

    private LabFee getOrThrow(Long id) {
        return labFeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LabFee", id));
    }

    private LabFeeResponse toResponse(LabFee lf) {
        return new LabFeeResponse(
            lf.getId(), lf.getLab().getId(), lf.getLab().getName(),
            lf.getCourse().getId(), lf.getCourse().getName(),
            lf.getSemester().getId(), lf.getSemester().getName(),
            lf.getAmount(), lf.getDescription(), lf.getStatus(),
            lf.getCreatedAt(), lf.getUpdatedAt()
        );
    }
}
