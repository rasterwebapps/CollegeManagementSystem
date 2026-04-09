package com.cms.controller.graphql;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Backlog;
import com.cms.model.DisciplinaryRecord;
import com.cms.model.StudentAchievement;
import com.cms.model.StudentDocument;
import com.cms.model.StudentGuardian;
import com.cms.model.StudentMentor;
import com.cms.model.StudentPreviousEducation;
import com.cms.model.StudentProfile;
import com.cms.repository.CourseRepository;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.SemesterRepository;
import com.cms.repository.StudentProfileRepository;
import com.cms.service.StudentDetailService;

@Controller
public class StudentGraphQLController {

    private final StudentProfileRepository studentProfileRepository;
    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final StudentDetailService studentDetailService;

    public StudentGraphQLController(StudentProfileRepository studentProfileRepository,
                                     ProgramRepository programRepository,
                                     DepartmentRepository departmentRepository,
                                     FacultyProfileRepository facultyProfileRepository,
                                     CourseRepository courseRepository,
                                     SemesterRepository semesterRepository,
                                     StudentDetailService studentDetailService) {
        this.studentProfileRepository = studentProfileRepository;
        this.programRepository = programRepository;
        this.departmentRepository = departmentRepository;
        this.facultyProfileRepository = facultyProfileRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.studentDetailService = studentDetailService;
    }

    @QueryMapping
    public Map<String, Object> studentProfiles(@Argument Map<String, Object> filter,
                                                @Argument Integer page,
                                                @Argument Integer size) {
        int pageNum = page != null ? page : 0;
        int pageSize = size != null ? size : 20;
        Page<StudentProfile> result = studentProfileRepository.findAll(PageRequest.of(pageNum, pageSize));
        return Map.of(
            "content", result.getContent(),
            "totalElements", result.getTotalElements(),
            "totalPages", result.getTotalPages(),
            "pageNumber", result.getNumber(),
            "pageSize", result.getSize()
        );
    }

    @QueryMapping
    public StudentProfile studentProfile(@Argument Long id) {
        return studentProfileRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public StudentProfile studentProfileByKeycloakId(@Argument String keycloakId) {
        return studentProfileRepository.findByKeycloakId(keycloakId).orElse(null);
    }

    @QueryMapping
    public StudentProfile studentProfileByEnrollmentNumber(@Argument String enrollmentNumber) {
        return studentProfileRepository.findByEnrollmentNumber(enrollmentNumber).orElse(null);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public StudentProfile createStudentProfile(@Argument Map<String, Object> input) {
        String enrollmentNumber = (String) input.get("enrollmentNumber");
        if (studentProfileRepository.existsByEnrollmentNumber(enrollmentNumber)) {
            throw new IllegalStateException("Enrollment number already exists: " + enrollmentNumber);
        }
        String email = (String) input.get("email");
        if (studentProfileRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already exists: " + email);
        }
        StudentProfile sp = new StudentProfile();
        sp.setKeycloakId((String) input.get("keycloakId"));
        sp.setEnrollmentNumber(enrollmentNumber);
        sp.setFirstName((String) input.get("firstName"));
        sp.setLastName((String) input.get("lastName"));
        sp.setEmail(email);
        sp.setPhone((String) input.get("phone"));
        if (input.get("currentSemester") != null) sp.setCurrentSemester((Integer) input.get("currentSemester"));
        sp.setStatus(input.get("status") != null ? (String) input.get("status") : "ACTIVE");
        if (input.get("admissionDate") != null) sp.setAdmissionDate(LocalDate.parse((String) input.get("admissionDate")));
        if (input.get("dateOfBirth") != null) sp.setDateOfBirth(LocalDate.parse((String) input.get("dateOfBirth")));
        if (input.get("gender") != null) sp.setGender((String) input.get("gender"));
        if (input.get("bloodGroup") != null) sp.setBloodGroup((String) input.get("bloodGroup"));
        if (input.get("nationality") != null) sp.setNationality((String) input.get("nationality"));
        if (input.get("category") != null) sp.setCategory((String) input.get("category"));
        if (input.get("religion") != null) sp.setReligion((String) input.get("religion"));
        if (input.get("photoUrl") != null) sp.setPhotoUrl((String) input.get("photoUrl"));
        if (input.get("programId") != null) {
            Long progId = Long.valueOf(input.get("programId").toString());
            sp.setProgram(programRepository.findById(progId)
                .orElseThrow(() -> new ResourceNotFoundException("Program", progId)));
        }
        if (input.get("departmentId") != null) {
            Long deptId = Long.valueOf(input.get("departmentId").toString());
            sp.setDepartment(departmentRepository.findById(deptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", deptId)));
        }
        return studentProfileRepository.save(sp);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public StudentProfile updateStudentProfile(@Argument Long id, @Argument Map<String, Object> input) {
        StudentProfile sp = studentProfileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", id));
        if (input.containsKey("firstName")) sp.setFirstName((String) input.get("firstName"));
        if (input.containsKey("lastName")) sp.setLastName((String) input.get("lastName"));
        if (input.containsKey("phone")) sp.setPhone((String) input.get("phone"));
        if (input.containsKey("currentSemester")) sp.setCurrentSemester((Integer) input.get("currentSemester"));
        if (input.containsKey("status")) sp.setStatus((String) input.get("status"));
        if (input.containsKey("gender")) sp.setGender((String) input.get("gender"));
        if (input.containsKey("bloodGroup")) sp.setBloodGroup((String) input.get("bloodGroup"));
        if (input.containsKey("nationality")) sp.setNationality((String) input.get("nationality"));
        if (input.containsKey("category")) sp.setCategory((String) input.get("category"));
        if (input.containsKey("photoUrl")) sp.setPhotoUrl((String) input.get("photoUrl"));
        return studentProfileRepository.save(sp);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteStudentProfile(@Argument Long id) {
        if (!studentProfileRepository.existsById(id)) throw new ResourceNotFoundException("StudentProfile", id);
        studentProfileRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public List<StudentGuardian> studentGuardians(@Argument Long studentId) {
        return studentDetailService.findGuardiansByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public StudentGuardian createStudentGuardian(@Argument Map<String, Object> input) {
        StudentGuardian g = new StudentGuardian();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        g.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        g.setName((String) input.get("name"));
        g.setRelation((String) input.get("relation"));
        g.setPhone((String) input.get("phone"));
        g.setEmail((String) input.get("email"));
        g.setOccupation((String) input.get("occupation"));
        if (input.get("annualIncome") != null) g.setAnnualIncome(new java.math.BigDecimal(input.get("annualIncome").toString()));
        g.setAddress((String) input.get("address"));
        g.setIsPrimary(input.get("isPrimary") != null ? (Boolean) input.get("isPrimary") : false);
        return studentDetailService.createGuardian(g);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteStudentGuardian(@Argument Long id) {
        studentDetailService.deleteGuardian(id);
        return true;
    }

    @QueryMapping
    public List<StudentDocument> studentDocuments(@Argument Long studentId) {
        return studentDetailService.findDocumentsByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public StudentDocument createStudentDocument(@Argument Map<String, Object> input) {
        StudentDocument doc = new StudentDocument();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        doc.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        doc.setDocumentType((String) input.get("documentType"));
        doc.setDocumentName((String) input.get("documentName"));
        doc.setFileUrl((String) input.get("fileUrl"));
        return studentDetailService.createDocument(doc);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteStudentDocument(@Argument Long id) {
        studentDetailService.deleteDocument(id);
        return true;
    }

    @QueryMapping
    public List<StudentAchievement> studentAchievements(@Argument Long studentId) {
        return studentDetailService.findAchievementsByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public StudentAchievement createStudentAchievement(@Argument Map<String, Object> input) {
        StudentAchievement a = new StudentAchievement();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        a.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        a.setTitle((String) input.get("title"));
        a.setCategory((String) input.get("category"));
        a.setLevel((String) input.get("level"));
        a.setDescription((String) input.get("description"));
        if (input.get("date") != null) a.setDate(LocalDate.parse((String) input.get("date")));
        a.setCertificateUrl((String) input.get("certificateUrl"));
        return studentDetailService.createAchievement(a);
    }

    @QueryMapping
    public List<StudentPreviousEducation> studentPreviousEducation(@Argument Long studentId) {
        return studentDetailService.findPreviousEducationByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public StudentPreviousEducation createStudentPreviousEducation(@Argument Map<String, Object> input) {
        StudentPreviousEducation edu = new StudentPreviousEducation();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        edu.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        edu.setLevel((String) input.get("level"));
        edu.setBoardOrUniversity((String) input.get("boardOrUniversity"));
        edu.setInstitutionName((String) input.get("institutionName"));
        if (input.get("yearOfPassing") != null) edu.setYearOfPassing((Integer) input.get("yearOfPassing"));
        if (input.get("percentage") != null) edu.setPercentage(new java.math.BigDecimal(input.get("percentage").toString()));
        edu.setSubjects((String) input.get("subjects"));
        return studentDetailService.createPreviousEducation(edu);
    }

    @QueryMapping
    public List<DisciplinaryRecord> disciplinaryRecords(@Argument Long studentId) {
        return studentDetailService.findDisciplinaryRecordsByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DisciplinaryRecord createDisciplinaryRecord(@Argument Map<String, Object> input) {
        DisciplinaryRecord record = new DisciplinaryRecord();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        record.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        record.setIncidentDate(LocalDate.parse((String) input.get("incidentDate")));
        record.setCategory((String) input.get("category"));
        record.setDescription((String) input.get("description"));
        record.setSeverity((String) input.get("severity"));
        record.setActionTaken((String) input.get("actionTaken"));
        record.setStatus("OPEN");
        return studentDetailService.createDisciplinaryRecord(record);
    }

    @QueryMapping
    public StudentMentor studentMentor(@Argument Long studentId) {
        return studentDetailService.findMentorByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public StudentMentor assignStudentMentor(@Argument Map<String, Object> input) {
        StudentMentor mentor = new StudentMentor();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        mentor.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        Long facultyId = Long.valueOf(input.get("facultyId").toString());
        mentor.setFaculty(facultyProfileRepository.findById(facultyId)
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", facultyId)));
        if (input.get("semesterId") != null) {
            Long semId = Long.valueOf(input.get("semesterId").toString());
            mentor.setSemester(semesterRepository.findById(semId)
                .orElseThrow(() -> new ResourceNotFoundException("Semester", semId)));
        }
        mentor.setAssignedDate(LocalDate.now());
        mentor.setActive(true);
        return studentDetailService.assignMentor(mentor);
    }

    @QueryMapping
    public List<Backlog> backlogs(@Argument Long studentId) {
        return studentDetailService.findBacklogsByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public Backlog createBacklog(@Argument Map<String, Object> input) {
        Backlog backlog = new Backlog();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        backlog.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        Long courseId = Long.valueOf(input.get("courseId").toString());
        backlog.setCourse(courseRepository.findById(courseId)
            .orElseThrow(() -> new ResourceNotFoundException("Course", courseId)));
        if (input.get("semesterId") != null) {
            Long semId = Long.valueOf(input.get("semesterId").toString());
            backlog.setSemester(semesterRepository.findById(semId)
                .orElseThrow(() -> new ResourceNotFoundException("Semester", semId)));
        }
        if (input.get("originalMarks") != null) backlog.setOriginalMarks(new java.math.BigDecimal(input.get("originalMarks").toString()));
        backlog.setStatus("ACTIVE");
        return studentDetailService.createBacklog(backlog);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public Backlog updateBacklog(@Argument Long id, @Argument Map<String, Object> input) {
        String status = (String) input.get("status");
        return studentDetailService.updateBacklogStatus(id, status);
    }
}
