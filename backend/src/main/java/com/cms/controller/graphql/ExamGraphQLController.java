package com.cms.controller.graphql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.ExamSchedule;
import com.cms.model.StudentMark;
import com.cms.repository.CourseRepository;
import com.cms.repository.ExamScheduleRepository;
import com.cms.repository.SemesterRepository;
import com.cms.repository.StudentProfileRepository;
import com.cms.service.ExamService;
import com.cms.service.ExamEnhancedService;
import com.cms.model.ExamSeatingPlan;
import com.cms.model.GradeScale;
import com.cms.model.RevaluationRequest;
import com.cms.model.QuestionBank;
import com.cms.model.QuestionPaper;
import com.cms.model.CourseFeedback;

@Controller
public class ExamGraphQLController {

    private final ExamService examService;
    private final ExamEnhancedService examEnhancedService;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final ExamScheduleRepository examScheduleRepository;

    public ExamGraphQLController(ExamService examService,
                                  ExamEnhancedService examEnhancedService,
                                  CourseRepository courseRepository,
                                  SemesterRepository semesterRepository,
                                  StudentProfileRepository studentProfileRepository,
                                  ExamScheduleRepository examScheduleRepository) {
        this.examService = examService;
        this.examEnhancedService = examEnhancedService;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.examScheduleRepository = examScheduleRepository;
    }

    @QueryMapping
    public List<ExamSchedule> examSchedules(@Argument Long semesterId) {
        if (semesterId != null) {
            return examService.findSchedulesBySemesterId(semesterId);
        }
        return examService.findAllSchedules();
    }

    @QueryMapping
    public ExamSchedule examSchedule(@Argument Long id) {
        return examService.findScheduleById(id);
    }

    @QueryMapping
    public List<ExamSchedule> examSchedulesByCourse(@Argument Long courseId) {
        return examScheduleRepository.findByCourseId(courseId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ExamSchedule createExamSchedule(@Argument Map<String, Object> input) {
        ExamSchedule schedule = new ExamSchedule();
        Long courseId = Long.valueOf(input.get("courseId").toString());
        schedule.setCourse(courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course", courseId)));
        Long semesterId = Long.valueOf(input.get("semesterId").toString());
        schedule.setSemester(semesterRepository.findById(semesterId)
            .orElseThrow(() -> new ResourceNotFoundException("Semester", semesterId)));
        schedule.setExamType((String) input.get("examType"));
        schedule.setDate(LocalDate.parse((String) input.get("date")));
        schedule.setStartTime(LocalTime.parse((String) input.get("startTime")));
        schedule.setDuration((Integer) input.get("duration"));
        schedule.setRoomLocation((String) input.get("roomLocation"));
        schedule.setMaxMarks(new java.math.BigDecimal(input.get("maxMarks").toString()));
        schedule.setPassingMarks(new java.math.BigDecimal(input.get("passingMarks").toString()));
        if (input.get("internalMarksWeight") != null)
            schedule.setInternalMarksWeight(new java.math.BigDecimal(input.get("internalMarksWeight").toString()));
        if (input.get("externalMarksWeight") != null)
            schedule.setExternalMarksWeight(new java.math.BigDecimal(input.get("externalMarksWeight").toString()));
        return examService.createSchedule(schedule);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ExamSchedule updateExamSchedule(@Argument Long id, @Argument Map<String, Object> input) {
        ExamSchedule schedule = examService.findScheduleById(id);
        if (input.containsKey("date")) schedule.setDate(LocalDate.parse((String) input.get("date")));
        if (input.containsKey("startTime")) schedule.setStartTime(LocalTime.parse((String) input.get("startTime")));
        if (input.containsKey("duration")) schedule.setDuration((Integer) input.get("duration"));
        if (input.containsKey("roomLocation")) schedule.setRoomLocation((String) input.get("roomLocation"));
        if (input.containsKey("status")) schedule.setStatus((String) input.get("status"));
        return examService.updateSchedule(id, schedule);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteExamSchedule(@Argument Long id) {
        examService.deleteSchedule(id);
        return true;
    }

    @QueryMapping
    public List<StudentMark> studentMarks(@Argument Long studentId, @Argument Long semesterId) {
        if (semesterId != null) {
            return examService.findMarksByStudentAndSemester(studentId, semesterId);
        }
        return examService.findMarksByStudentId(studentId);
    }

    @QueryMapping
    public List<StudentMark> marksByExam(@Argument Long examId) {
        return examService.findMarksByExamId(examId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public StudentMark recordStudentMark(@Argument Map<String, Object> input) {
        StudentMark mark = new StudentMark();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        mark.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        Long examId = Long.valueOf(input.get("examId").toString());
        mark.setExam(examScheduleRepository.findById(examId)
            .orElseThrow(() -> new ResourceNotFoundException("ExamSchedule", examId)));
        Long courseId = Long.valueOf(input.get("courseId").toString());
        mark.setCourse(courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course", courseId)));
        Long semesterId = Long.valueOf(input.get("semesterId").toString());
        mark.setSemester(semesterRepository.findById(semesterId)
            .orElseThrow(() -> new ResourceNotFoundException("Semester", semesterId)));
        if (input.get("internalMarks") != null)
            mark.setInternalMarks(new java.math.BigDecimal(input.get("internalMarks").toString()));
        if (input.get("externalMarks") != null)
            mark.setExternalMarks(new java.math.BigDecimal(input.get("externalMarks").toString()));
        mark.setTotalMarks(new java.math.BigDecimal(input.get("totalMarks").toString()));
        mark.setMaxMarks(new java.math.BigDecimal(input.get("maxMarks").toString()));
        mark.setGrade((String) input.get("grade"));
        mark.setResult((String) input.get("result"));
        mark.setRemarks((String) input.get("remarks"));
        return examService.recordMark(mark);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public StudentMark updateStudentMark(@Argument Long id, @Argument Map<String, Object> input) {
        StudentMark mark = examService.findMarkById(id);
        if (input.containsKey("internalMarks"))
            mark.setInternalMarks(new java.math.BigDecimal(input.get("internalMarks").toString()));
        if (input.containsKey("externalMarks"))
            mark.setExternalMarks(new java.math.BigDecimal(input.get("externalMarks").toString()));
        if (input.containsKey("totalMarks"))
            mark.setTotalMarks(new java.math.BigDecimal(input.get("totalMarks").toString()));
        if (input.containsKey("grade")) mark.setGrade((String) input.get("grade"));
        if (input.containsKey("result")) mark.setResult((String) input.get("result"));
        if (input.containsKey("remarks")) mark.setRemarks((String) input.get("remarks"));
        return examService.updateMark(id, mark);
    }

    // Exam Seating Plan
    @QueryMapping
    public List<ExamSeatingPlan> examSeatingPlan(@Argument Long examId) {
        return examEnhancedService.findSeatingByExam(examId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ExamSeatingPlan generateSeatingPlan(@Argument Long examId) {
        ExamSeatingPlan s = new ExamSeatingPlan();
        s.setExam(examScheduleRepository.findById(examId).orElse(null));
        return examEnhancedService.createSeating(s);
    }

    // Grade Scales
    @QueryMapping
    public List<GradeScale> gradeScales() {
        return examEnhancedService.findAllGradeScales();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public GradeScale createGradeScale(@Argument Map<String, Object> input) {
        GradeScale g = new GradeScale();
        g.setGrade((String) input.get("grade"));
        if (input.get("minPercentage") != null) g.setMinPercentage(new java.math.BigDecimal(input.get("minPercentage").toString()));
        if (input.get("maxPercentage") != null) g.setMaxPercentage(new java.math.BigDecimal(input.get("maxPercentage").toString()));
        if (input.get("gradePoint") != null) g.setGradePoint(new java.math.BigDecimal(input.get("gradePoint").toString()));
        return examEnhancedService.createGradeScale(g);
    }

    // Revaluation Requests
    @QueryMapping
    public List<RevaluationRequest> revaluationRequests(@Argument Long studentId) {
        return examEnhancedService.findAllRevaluations().stream()
            .filter(r -> studentId == null || r.getStudent().getId().equals(studentId)).toList();
    }

    @MutationMapping
    public RevaluationRequest createRevaluationRequest(@Argument Map<String, Object> input) {
        RevaluationRequest r = new RevaluationRequest();
        if (input.get("studentId") != null) r.setStudent(studentProfileRepository.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("examId") != null) r.setExam(examScheduleRepository.findById(Long.valueOf(input.get("examId").toString())).orElse(null));
        r.setReason((String) input.get("reason"));
        r.setStatus("PENDING");
        return examEnhancedService.createRevaluation(r);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RevaluationRequest processRevaluationRequest(@Argument Long id, @Argument java.math.BigDecimal revisedMarks, @Argument String status) {
        return examEnhancedService.processRevaluation(id, status, revisedMarks);
    }

    // Question Bank
    @QueryMapping
    public List<QuestionBank> questionBank(@Argument Long courseId) {
        return examEnhancedService.findQuestionsByCoure(courseId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public QuestionBank createQuestionBankEntry(@Argument Map<String, Object> input) {
        QuestionBank q = new QuestionBank();
        if (input.get("courseId") != null) q.setCourse(courseRepository.findById(Long.valueOf(input.get("courseId").toString())).orElse(null));
        q.setQuestion((String) input.get("question"));
        q.setType((String) input.get("questionType"));
        q.setMarks((Integer) input.get("marks"));
        if (input.get("unit") != null) q.setUnit(Integer.parseInt(input.get("unit").toString()));
        q.setBloomLevel((String) input.get("bloomLevel"));
        q.setCreatedBy((String) input.get("createdBy"));
        return examEnhancedService.createQuestion(q);
    }

    // Question Papers
    @QueryMapping
    public List<QuestionPaper> questionPapers(@Argument Long examId) {
        return examEnhancedService.findPapersByExam(examId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public QuestionPaper createQuestionPaper(@Argument Map<String, Object> input) {
        QuestionPaper p = new QuestionPaper();
        if (input.get("examId") != null) p.setExam(examScheduleRepository.findById(Long.valueOf(input.get("examId").toString())).orElse(null));
        if (input.get("courseId") != null) p.setCourse(courseRepository.findById(Long.valueOf(input.get("courseId").toString())).orElse(null));
        p.setTotalMarks((Integer) input.get("totalMarks"));
        p.setSections((String) input.get("sections"));
        p.setApprovedBy((String) input.get("approvedBy"));
        p.setStatus("DRAFT");
        return examEnhancedService.createPaper(p);
    }

    // Course Feedback
    @QueryMapping
    public List<CourseFeedback> courseFeedback(@Argument Long courseId, @Argument Long semesterId) {
        return examEnhancedService.findFeedbackByCourse(courseId);
    }

    @MutationMapping
    public CourseFeedback createCourseFeedback(@Argument Map<String, Object> input) {
        CourseFeedback f = new CourseFeedback();
        if (input.get("studentId") != null) f.setStudent(studentProfileRepository.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("courseId") != null) f.setCourse(courseRepository.findById(Long.valueOf(input.get("courseId").toString())).orElse(null));
        if (input.get("semesterId") != null) f.setSemester(semesterRepository.findById(Long.valueOf(input.get("semesterId").toString())).orElse(null));
        if (input.get("rating") != null) f.setRating(new java.math.BigDecimal(input.get("rating").toString()));
        f.setComments((String) input.get("comments"));
        return examEnhancedService.createFeedback(f);
    }
}
