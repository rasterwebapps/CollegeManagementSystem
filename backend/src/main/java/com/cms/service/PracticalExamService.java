package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.PracticalExamRequest;
import com.cms.dto.PracticalExamResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Experiment;
import com.cms.model.Lab;
import com.cms.model.PracticalExam;
import com.cms.model.Semester;
import com.cms.repository.CourseRepository;
import com.cms.repository.ExperimentRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.PracticalExamRepository;
import com.cms.repository.SemesterRepository;

@Service
public class PracticalExamService {

    private final PracticalExamRepository practicalExamRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final ExperimentRepository experimentRepository;
    private final LabRepository labRepository;

    public PracticalExamService(PracticalExamRepository practicalExamRepository,
                                CourseRepository courseRepository,
                                SemesterRepository semesterRepository,
                                ExperimentRepository experimentRepository,
                                LabRepository labRepository) {
        this.practicalExamRepository = practicalExamRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.experimentRepository = experimentRepository;
        this.labRepository = labRepository;
    }

    public List<PracticalExamResponse> findAll() {
        return practicalExamRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PracticalExamResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public PracticalExamResponse create(PracticalExamRequest request) {
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        Experiment experiment = null;
        if (request.experimentId() != null) {
            experiment = experimentRepository.findById(request.experimentId())
                .orElseThrow(() -> new ResourceNotFoundException("Experiment", request.experimentId()));
        }
        Lab lab = null;
        if (request.labId() != null) {
            lab = labRepository.findById(request.labId())
                .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        }
        PracticalExam e = new PracticalExam();
        e.setCourse(course);
        e.setSemester(semester);
        e.setExperiment(experiment);
        e.setLab(lab);
        e.setExamDate(request.examDate());
        e.setStartTime(request.startTime());
        e.setEndTime(request.endTime());
        e.setMaxMarks(request.maxMarks());
        e.setPassingMarks(request.passingMarks());
        e.setExaminer(request.examiner());
        e.setStatus(request.status() != null ? request.status() : "SCHEDULED");
        return toResponse(practicalExamRepository.save(e));
    }

    public PracticalExamResponse update(Long id, PracticalExamRequest request) {
        PracticalExam e = getOrThrow(id);
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        Experiment experiment = null;
        if (request.experimentId() != null) {
            experiment = experimentRepository.findById(request.experimentId())
                .orElseThrow(() -> new ResourceNotFoundException("Experiment", request.experimentId()));
        }
        Lab lab = null;
        if (request.labId() != null) {
            lab = labRepository.findById(request.labId())
                .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        }
        e.setCourse(course);
        e.setSemester(semester);
        e.setExperiment(experiment);
        e.setLab(lab);
        e.setExamDate(request.examDate());
        e.setStartTime(request.startTime());
        e.setEndTime(request.endTime());
        e.setMaxMarks(request.maxMarks());
        e.setPassingMarks(request.passingMarks());
        e.setExaminer(request.examiner());
        if (request.status() != null) { e.setStatus(request.status()); }
        return toResponse(practicalExamRepository.save(e));
    }

    public void delete(Long id) {
        if (!practicalExamRepository.existsById(id)) {
            throw new ResourceNotFoundException("PracticalExam", id);
        }
        practicalExamRepository.deleteById(id);
    }

    private PracticalExam getOrThrow(Long id) {
        return practicalExamRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("PracticalExam", id));
    }

    private PracticalExamResponse toResponse(PracticalExam e) {
        return new PracticalExamResponse(
            e.getId(),
            e.getCourse().getId(), e.getCourse().getName(),
            e.getSemester().getId(), e.getSemester().getName(),
            e.getExperiment() != null ? e.getExperiment().getId() : null,
            e.getExperiment() != null ? e.getExperiment().getTitle() : null,
            e.getExamDate(), e.getStartTime(), e.getEndTime(),
            e.getLab() != null ? e.getLab().getId() : null,
            e.getLab() != null ? e.getLab().getName() : null,
            e.getMaxMarks(), e.getPassingMarks(), e.getExaminer(),
            e.getStatus(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
