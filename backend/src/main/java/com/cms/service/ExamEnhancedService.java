package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class ExamEnhancedService {
    private final ExamSeatingPlanRepository seatingRepo;
    private final GradeScaleRepository gradeRepo;
    private final RevaluationRequestRepository revalRepo;
    private final QuestionBankRepository questionRepo;
    private final QuestionPaperRepository paperRepo;
    private final CourseFeedbackRepository feedbackRepo;

    public ExamEnhancedService(ExamSeatingPlanRepository seatingRepo, GradeScaleRepository gradeRepo, RevaluationRequestRepository revalRepo, QuestionBankRepository questionRepo, QuestionPaperRepository paperRepo, CourseFeedbackRepository feedbackRepo) {
        this.seatingRepo = seatingRepo; this.gradeRepo = gradeRepo; this.revalRepo = revalRepo;
        this.questionRepo = questionRepo; this.paperRepo = paperRepo; this.feedbackRepo = feedbackRepo;
    }

    public List<ExamSeatingPlan> findSeatingByExam(Long examId) { return seatingRepo.findAll().stream().filter(s -> s.getExam().getId().equals(examId)).toList(); }
    public ExamSeatingPlan createSeating(ExamSeatingPlan s) { return seatingRepo.save(s); }

    public List<GradeScale> findAllGradeScales() { return gradeRepo.findAll(); }
    public GradeScale createGradeScale(GradeScale g) { return gradeRepo.save(g); }

    public List<RevaluationRequest> findAllRevaluations() { return revalRepo.findAll(); }
    public RevaluationRequest createRevaluation(RevaluationRequest r) { return revalRepo.save(r); }
    public RevaluationRequest processRevaluation(Long id, String status, java.math.BigDecimal marks) { return revalRepo.findById(id).map(r -> { r.setStatus(status); if (marks != null) r.setRevisedMarks(marks); return revalRepo.save(r); }).orElse(null); }

    public List<QuestionBank> findQuestionsByCoure(Long courseId) { return questionRepo.findAll().stream().filter(q -> q.getCourse().getId().equals(courseId)).toList(); }
    public QuestionBank createQuestion(QuestionBank q) { return questionRepo.save(q); }

    public List<QuestionPaper> findPapersByExam(Long examId) { return paperRepo.findAll().stream().filter(p -> p.getExam().getId().equals(examId)).toList(); }
    public QuestionPaper createPaper(QuestionPaper p) { return paperRepo.save(p); }

    public List<CourseFeedback> findFeedbackByCourse(Long courseId) { return feedbackRepo.findAll().stream().filter(f -> f.getCourse().getId().equals(courseId)).toList(); }
    public CourseFeedback createFeedback(CourseFeedback f) { return feedbackRepo.save(f); }
}
