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
import com.cms.model.FacultyProfile;
import com.cms.model.FacultyQualification;
import com.cms.model.LeaveApplication;
import com.cms.model.ResearchPublication;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.service.FacultyDetailService;
import com.cms.service.StaffService;
import com.cms.model.FacultyWorkload;
import com.cms.model.FacultyAppraisal;
import com.cms.model.LeaveType;
import com.cms.model.CommitteeMembership;
import com.cms.repository.SemesterRepository;

@Controller
public class FacultyGraphQLController {

    private final FacultyProfileRepository facultyProfileRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyDetailService facultyDetailService;
    private final StaffService staffService;
    private final SemesterRepository semesterRepository;

    public FacultyGraphQLController(FacultyProfileRepository facultyProfileRepository,
                                     DepartmentRepository departmentRepository,
                                     FacultyDetailService facultyDetailService,
                                     StaffService staffService,
                                     SemesterRepository semesterRepository) {
        this.facultyProfileRepository = facultyProfileRepository;
        this.departmentRepository = departmentRepository;
        this.facultyDetailService = facultyDetailService;
        this.staffService = staffService;
        this.semesterRepository = semesterRepository;
    }

    @QueryMapping
    public List<FacultyProfile> facultyProfiles() { return facultyProfileRepository.findAll(); }

    @QueryMapping
    public FacultyProfile facultyProfile(@Argument Long id) { return facultyProfileRepository.findById(id).orElse(null); }

    @QueryMapping
    public FacultyProfile facultyProfileByKeycloakId(@Argument String keycloakId) {
        return facultyProfileRepository.findByKeycloakId(keycloakId).orElse(null);
    }

    @QueryMapping
    public List<FacultyProfile> facultyProfilesByDepartment(@Argument Long departmentId) {
        return facultyProfileRepository.findByDepartmentId(departmentId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FacultyProfile createFacultyProfile(@Argument Map<String, Object> input) {
        FacultyProfile fp = new FacultyProfile();
        fp.setKeycloakId((String) input.get("keycloakId"));
        fp.setEmployeeCode((String) input.get("employeeId"));
        fp.setFirstName((String) input.get("firstName"));
        fp.setLastName((String) input.get("lastName"));
        fp.setEmail((String) input.get("email"));
        fp.setPhone((String) input.get("phone"));
        fp.setDesignation((String) input.get("designation"));
        if (input.get("joiningDate") != null) fp.setJoiningDate(LocalDate.parse((String) input.get("joiningDate")));
        fp.setStatus(input.get("status") != null ? (String) input.get("status") : "ACTIVE");
        if (input.get("highestQualification") != null) fp.setHighestQualification((String) input.get("highestQualification"));
        if (input.get("specialization") != null) fp.setSpecialization((String) input.get("specialization"));
        if (input.get("experienceYears") != null) fp.setExperienceYears((Integer) input.get("experienceYears"));
        if (input.get("gender") != null) fp.setGender((String) input.get("gender"));
        if (input.get("photoUrl") != null) fp.setPhotoUrl((String) input.get("photoUrl"));
        if (input.get("departmentId") != null) {
            Long deptId = Long.valueOf(input.get("departmentId").toString());
            fp.setDepartment(departmentRepository.findById(deptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", deptId)));
        }
        return facultyProfileRepository.save(fp);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FacultyProfile updateFacultyProfile(@Argument Long id, @Argument Map<String, Object> input) {
        FacultyProfile fp = facultyProfileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", id));
        if (input.containsKey("firstName")) fp.setFirstName((String) input.get("firstName"));
        if (input.containsKey("lastName")) fp.setLastName((String) input.get("lastName"));
        if (input.containsKey("phone")) fp.setPhone((String) input.get("phone"));
        if (input.containsKey("designation")) fp.setDesignation((String) input.get("designation"));
        if (input.containsKey("status")) fp.setStatus((String) input.get("status"));
        if (input.containsKey("highestQualification")) fp.setHighestQualification((String) input.get("highestQualification"));
        if (input.containsKey("specialization")) fp.setSpecialization((String) input.get("specialization"));
        if (input.containsKey("experienceYears")) fp.setExperienceYears((Integer) input.get("experienceYears"));
        return facultyProfileRepository.save(fp);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteFacultyProfile(@Argument Long id) {
        if (!facultyProfileRepository.existsById(id)) throw new ResourceNotFoundException("FacultyProfile", id);
        facultyProfileRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public List<FacultyQualification> facultyQualifications(@Argument Long facultyId) {
        return facultyDetailService.findQualificationsByFacultyId(facultyId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public FacultyQualification createFacultyQualification(@Argument Map<String, Object> input) {
        FacultyQualification q = new FacultyQualification();
        Long facultyId = Long.valueOf(input.get("facultyId").toString());
        q.setFaculty(facultyProfileRepository.findById(facultyId)
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", facultyId)));
        q.setDegree((String) input.get("degree"));
        q.setFieldOfStudy((String) input.get("specialization"));
        q.setUniversity((String) input.get("university"));
        if (input.get("yearOfCompletion") != null) q.setYearOfCompletion((Integer) input.get("yearOfCompletion"));
        q.setGrade((String) input.get("gradeOrPercentage"));
        return facultyDetailService.createQualification(q);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteFacultyQualification(@Argument Long id) {
        facultyDetailService.deleteQualification(id);
        return true;
    }

    @QueryMapping
    public List<ResearchPublication> researchPublications(@Argument Long facultyId) {
        return facultyDetailService.findPublicationsByFacultyId(facultyId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResearchPublication createResearchPublication(@Argument Map<String, Object> input) {
        ResearchPublication pub = new ResearchPublication();
        Long facultyId = Long.valueOf(input.get("facultyId").toString());
        pub.setFaculty(facultyProfileRepository.findById(facultyId)
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", facultyId)));
        pub.setTitle((String) input.get("title"));
        pub.setJournalOrConference((String) input.get("journalName"));
        pub.setPublicationType((String) input.get("publicationType"));
        pub.setDoi((String) input.get("doi"));
        if (input.get("year") != null) pub.setYear((Integer) input.get("year"));
        if (input.get("impactFactor") != null) pub.setImpactFactor(new java.math.BigDecimal(input.get("impactFactor").toString()));
        return facultyDetailService.createPublication(pub);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResearchPublication updateResearchPublication(@Argument Long id, @Argument Map<String, Object> input) {
        ResearchPublication data = new ResearchPublication();
        if (input.containsKey("title")) data.setTitle((String) input.get("title"));
        if (input.containsKey("journalName")) data.setJournalOrConference((String) input.get("journalName"));
        if (input.containsKey("publicationType")) data.setPublicationType((String) input.get("publicationType"));
        if (input.containsKey("doi")) data.setDoi((String) input.get("doi"));
        if (input.get("impactFactor") != null) data.setImpactFactor(new java.math.BigDecimal(input.get("impactFactor").toString()));
        return facultyDetailService.updatePublication(id, data);
    }

    @QueryMapping
    public List<LeaveApplication> leaveApplications(@Argument Long facultyId) {
        return facultyDetailService.findLeavesByFacultyId(facultyId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public LeaveApplication createLeaveApplication(@Argument Map<String, Object> input) {
        LeaveApplication leave = new LeaveApplication();
        Long facultyId = Long.valueOf(input.get("facultyId").toString());
        leave.setFaculty(facultyProfileRepository.findById(facultyId)
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", facultyId)));
        leave.setLeaveType((String) input.get("leaveType"));
        leave.setFromDate(LocalDate.parse((String) input.get("startDate")));
        leave.setToDate(LocalDate.parse((String) input.get("endDate")));
        leave.setReason((String) input.get("reason"));
        leave.setStatus("PENDING");
        return facultyDetailService.createLeave(leave);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public LeaveApplication updateLeaveApplicationStatus(@Argument Long id,
                                                          @Argument String status,
                                                          @Argument String remarks) {
        return facultyDetailService.updateLeaveStatus(id, status, remarks);
    }

    // Faculty Workload
    @QueryMapping
    public List<FacultyWorkload> facultyWorkload(@Argument Long facultyId, @Argument Long semesterId) {
        List<FacultyWorkload> workloads = staffService.findWorkloadByFaculty(facultyId);
        if (semesterId != null) return workloads.stream().filter(w -> w.getSemester() != null && w.getSemester().getId().equals(semesterId)).toList();
        return workloads;
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FacultyWorkload saveFacultyWorkload(@Argument Map<String, Object> input) {
        FacultyWorkload w = new FacultyWorkload();
        if (input.get("facultyId") != null) w.setFaculty(facultyProfileRepository.findById(Long.valueOf(input.get("facultyId").toString())).orElse(null));
        if (input.get("semesterId") != null) w.setSemester(semesterRepository.findById(Long.valueOf(input.get("semesterId").toString())).orElse(null));
        if (input.get("teachingHours") != null) w.setTeachingHours((Integer) input.get("teachingHours"));
        if (input.get("labHours") != null) w.setLabHours((Integer) input.get("labHours"));
        if (input.get("adminHours") != null) w.setAdminHours((Integer) input.get("adminHours"));
        if (input.get("researchHours") != null) w.setResearchHours((Integer) input.get("researchHours"));
        return staffService.saveWorkload(w);
    }

    // Faculty Appraisals
    @QueryMapping
    public List<FacultyAppraisal> facultyAppraisals(@Argument Long facultyId) {
        return staffService.findAppraisalsByFaculty(facultyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FacultyAppraisal createFacultyAppraisal(@Argument Map<String, Object> input) {
        FacultyAppraisal a = new FacultyAppraisal();
        if (input.get("facultyId") != null) a.setFaculty(facultyProfileRepository.findById(Long.valueOf(input.get("facultyId").toString())).orElse(null));
        if (input.get("year") != null) a.setYear((Integer) input.get("year"));
        if (input.get("teachingScore") != null) a.setTeachingRating(new java.math.BigDecimal(input.get("teachingScore").toString()));
        if (input.get("researchScore") != null) a.setResearchRating(new java.math.BigDecimal(input.get("researchScore").toString()));
        if (input.get("overallScore") != null) a.setOverallRating(new java.math.BigDecimal(input.get("overallScore").toString()));
        a.setRemarks((String) input.get("remarks"));
        a.setStatus("SUBMITTED");
        return staffService.createAppraisal(a);
    }

    // Leave Types
    @QueryMapping
    public List<LeaveType> leaveTypes() {
        return staffService.findAllLeaveTypes();
    }

    // Committee Memberships
    @QueryMapping
    public List<CommitteeMembership> committeeMemberships(@Argument Long facultyId) {
        return staffService.findCommitteesByFaculty(facultyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CommitteeMembership createCommitteeMembership(@Argument Map<String, Object> input) {
        CommitteeMembership c = new CommitteeMembership();
        if (input.get("facultyId") != null) c.setFaculty(facultyProfileRepository.findById(Long.valueOf(input.get("facultyId").toString())).orElse(null));
        c.setCommitteeName((String) input.get("committeeName"));
        c.setRole((String) input.get("role"));
        if (input.get("startDate") != null) c.setStartDate(LocalDate.parse((String) input.get("startDate")));
        if (input.get("endDate") != null) c.setEndDate(LocalDate.parse((String) input.get("endDate")));
        c.setStatus("ACTIVE");
        return staffService.createCommittee(c);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteCommitteeMembership(@Argument Long id) {
        staffService.deleteCommittee(id);
        return true;
    }
}
