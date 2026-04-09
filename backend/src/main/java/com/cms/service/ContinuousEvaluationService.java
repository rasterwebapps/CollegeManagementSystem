package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.ContinuousEvaluationRequest;
import com.cms.dto.ContinuousEvaluationResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.ContinuousEvaluation;
import com.cms.model.Course;
import com.cms.model.Experiment;
import com.cms.model.Semester;
import com.cms.model.StudentProfile;
import com.cms.repository.ContinuousEvaluationRepository;
import com.cms.repository.CourseRepository;
import com.cms.repository.ExperimentRepository;
import com.cms.repository.SemesterRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class ContinuousEvaluationService {

    private final ContinuousEvaluationRepository continuousEvaluationRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CourseRepository courseRepository;
    private final ExperimentRepository experimentRepository;
    private final SemesterRepository semesterRepository;

    public ContinuousEvaluationService(ContinuousEvaluationRepository continuousEvaluationRepository,
                                       StudentProfileRepository studentProfileRepository,
                                       CourseRepository courseRepository,
                                       ExperimentRepository experimentRepository,
                                       SemesterRepository semesterRepository) {
        this.continuousEvaluationRepository = continuousEvaluationRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.courseRepository = courseRepository;
        this.experimentRepository = experimentRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<ContinuousEvaluationResponse> findAll() {
        return continuousEvaluationRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ContinuousEvaluationResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public ContinuousEvaluationResponse create(ContinuousEvaluationRequest request) {
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        Experiment experiment = null;
        if (request.experimentId() != null) {
            experiment = experimentRepository.findById(request.experimentId())
                .orElseThrow(() -> new ResourceNotFoundException("Experiment", request.experimentId()));
        }
        ContinuousEvaluation e = new ContinuousEvaluation();
        e.setStudent(student);
        e.setCourse(course);
        e.setExperiment(experiment);
        e.setSemester(semester);
        e.setEvaluationType(request.evaluationType());
        e.setMarksObtained(request.marksObtained());
        e.setMaxMarks(request.maxMarks());
        e.setEvaluationDate(request.evaluationDate());
        e.setRemarks(request.remarks());
        return toResponse(continuousEvaluationRepository.save(e));
    }

    public ContinuousEvaluationResponse update(Long id, ContinuousEvaluationRequest request) {
        ContinuousEvaluation e = getOrThrow(id);
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        Experiment experiment = null;
        if (request.experimentId() != null) {
            experiment = experimentRepository.findById(request.experimentId())
                .orElseThrow(() -> new ResourceNotFoundException("Experiment", request.experimentId()));
        }
        e.setStudent(student);
        e.setCourse(course);
        e.setExperiment(experiment);
        e.setSemester(semester);
        e.setEvaluationType(request.evaluationType());
        e.setMarksObtained(request.marksObtained());
        e.setMaxMarks(request.maxMarks());
        e.setEvaluationDate(request.evaluationDate());
        e.setRemarks(request.remarks());
        return toResponse(continuousEvaluationRepository.save(e));
    }

    public void delete(Long id) {
        if (!continuousEvaluationRepository.existsById(id)) {
            throw new ResourceNotFoundException("ContinuousEvaluation", id);
        }
        continuousEvaluationRepository.deleteById(id);
    }

    private ContinuousEvaluation getOrThrow(Long id) {
        return continuousEvaluationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ContinuousEvaluation", id));
    }

    private ContinuousEvaluationResponse toResponse(ContinuousEvaluation e) {
        return new ContinuousEvaluationResponse(
            e.getId(),
            e.getStudent().getId(),
            e.getStudent().getFirstName() + " " + e.getStudent().getLastName(),
            e.getCourse().getId(), e.getCourse().getName(),
            e.getExperiment() != null ? e.getExperiment().getId() : null,
            e.getExperiment() != null ? e.getExperiment().getTitle() : null,
            e.getSemester().getId(), e.getSemester().getName(),
            e.getEvaluationType(), e.getMarksObtained(), e.getMaxMarks(),
            e.getEvaluationDate(), e.getRemarks(),
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
