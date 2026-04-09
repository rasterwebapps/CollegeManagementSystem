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
import com.cms.model.Company;
import com.cms.model.PlacementApplication;
import com.cms.model.PlacementDrive;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CompanyRepository;
import com.cms.repository.PlacementDriveRepository;
import com.cms.repository.StudentProfileRepository;
import com.cms.service.PlacementService;
import com.cms.service.PlacementEnhancedService;
import com.cms.model.PlacementOffer;
import com.cms.model.Internship;
import com.cms.model.PlacementStatistics;
import com.cms.repository.DepartmentRepository;

@Controller
public class PlacementGraphQLController {

    private final PlacementService placementService;
    private final PlacementEnhancedService placementEnhancedService;
    private final CompanyRepository companyRepository;
    private final AcademicYearRepository academicYearRepository;
    private final PlacementDriveRepository placementDriveRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final DepartmentRepository departmentRepository;

    public PlacementGraphQLController(PlacementService placementService,
                                       PlacementEnhancedService placementEnhancedService,
                                       CompanyRepository companyRepository,
                                       AcademicYearRepository academicYearRepository,
                                       PlacementDriveRepository placementDriveRepository,
                                       StudentProfileRepository studentProfileRepository,
                                       DepartmentRepository departmentRepository) {
        this.placementService = placementService;
        this.placementEnhancedService = placementEnhancedService;
        this.companyRepository = companyRepository;
        this.academicYearRepository = academicYearRepository;
        this.placementDriveRepository = placementDriveRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.departmentRepository = departmentRepository;
    }

    // Company queries
    @QueryMapping
    public List<Company> companies() {
        return placementService.findAllCompanies();
    }

    @QueryMapping
    public Company company(@Argument Long id) {
        return placementService.findCompanyById(id);
    }

    @QueryMapping
    public List<Company> companiesByIndustry(@Argument String industry) {
        return companyRepository.findByIndustry(industry);
    }

    @QueryMapping
    public List<Company> companiesByTier(@Argument String tier) {
        return companyRepository.findByTier(tier);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Company createCompany(@Argument Map<String, Object> input) {
        Company company = new Company();
        company.setName((String) input.get("name"));
        company.setIndustry((String) input.get("industry"));
        company.setWebsite((String) input.get("website"));
        company.setContactPerson((String) input.get("contactPerson"));
        company.setContactEmail((String) input.get("contactEmail"));
        company.setContactPhone((String) input.get("contactPhone"));
        company.setAddress((String) input.get("address"));
        company.setDescription((String) input.get("description"));
        company.setTier((String) input.get("tier"));
        company.setAgreementStatus((String) input.get("agreementStatus"));
        return placementService.createCompany(company);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Company updateCompany(@Argument Long id, @Argument Map<String, Object> input) {
        Company company = placementService.findCompanyById(id);
        if (input.containsKey("name")) company.setName((String) input.get("name"));
        if (input.containsKey("industry")) company.setIndustry((String) input.get("industry"));
        if (input.containsKey("website")) company.setWebsite((String) input.get("website"));
        if (input.containsKey("contactPerson")) company.setContactPerson((String) input.get("contactPerson"));
        if (input.containsKey("contactEmail")) company.setContactEmail((String) input.get("contactEmail"));
        if (input.containsKey("contactPhone")) company.setContactPhone((String) input.get("contactPhone"));
        if (input.containsKey("tier")) company.setTier((String) input.get("tier"));
        if (input.containsKey("status")) company.setStatus((String) input.get("status"));
        return placementService.createCompany(company);
    }

    // Placement Drive queries
    @QueryMapping
    public List<PlacementDrive> placementDrives(@Argument Long academicYearId) {
        if (academicYearId != null) {
            return placementDriveRepository.findByAcademicYearId(academicYearId);
        }
        return placementService.findAllDrives();
    }

    @QueryMapping
    public PlacementDrive placementDrive(@Argument Long id) {
        return placementService.findDriveById(id);
    }

    @QueryMapping
    public List<PlacementDrive> placementDrivesByStatus(@Argument String status) {
        return placementDriveRepository.findByStatus(status);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PlacementDrive createPlacementDrive(@Argument Map<String, Object> input) {
        PlacementDrive drive = new PlacementDrive();
        Long companyId = Long.valueOf(input.get("companyId").toString());
        drive.setCompany(companyRepository.findById(companyId)
            .orElseThrow(() -> new ResourceNotFoundException("Company", companyId)));
        if (input.get("academicYearId") != null) {
            Long ayId = Long.valueOf(input.get("academicYearId").toString());
            drive.setAcademicYear(academicYearRepository.findById(ayId)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", ayId)));
        }
        drive.setTitle((String) input.get("title"));
        drive.setDriveDate(LocalDate.parse((String) input.get("driveDate")));
        drive.setLastDateToApply(LocalDate.parse((String) input.get("lastDateToApply")));
        drive.setEligibleDepartments((String) input.get("eligibleDepartments"));
        drive.setEligibilityCriteria((String) input.get("eligibilityCriteria"));
        if (input.get("minCgpa") != null) drive.setMinCgpa(new java.math.BigDecimal(input.get("minCgpa").toString()));
        if (input.get("maxBacklogs") != null) drive.setMaxBacklogs((Integer) input.get("maxBacklogs"));
        drive.setRolesOffered((String) input.get("rolesOffered"));
        drive.setPackageMin(new java.math.BigDecimal(input.get("packageMin").toString()));
        if (input.get("packageMax") != null) drive.setPackageMax(new java.math.BigDecimal(input.get("packageMax").toString()));
        drive.setJobLocation((String) input.get("jobLocation"));
        drive.setSelectionProcess((String) input.get("selectionProcess"));
        drive.setBondDetails((String) input.get("bondDetails"));
        return placementService.createDrive(drive);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PlacementDrive updatePlacementDrive(@Argument Long id, @Argument Map<String, Object> input) {
        PlacementDrive drive = placementService.findDriveById(id);
        if (input.containsKey("title")) drive.setTitle((String) input.get("title"));
        if (input.containsKey("driveDate")) drive.setDriveDate(LocalDate.parse((String) input.get("driveDate")));
        if (input.containsKey("lastDateToApply")) drive.setLastDateToApply(LocalDate.parse((String) input.get("lastDateToApply")));
        if (input.containsKey("eligibleDepartments")) drive.setEligibleDepartments((String) input.get("eligibleDepartments"));
        if (input.containsKey("eligibilityCriteria")) drive.setEligibilityCriteria((String) input.get("eligibilityCriteria"));
        if (input.containsKey("status")) drive.setStatus((String) input.get("status"));
        if (input.containsKey("selectedCount")) drive.setSelectedCount((Integer) input.get("selectedCount"));
        return placementService.createDrive(drive);
    }

    // Placement Application
    @QueryMapping
    public List<PlacementApplication> placementApplications(@Argument Long driveId) {
        return placementService.findApplicationsByDriveId(driveId);
    }

    @QueryMapping
    public List<PlacementApplication> studentPlacementApplications(@Argument Long studentId) {
        return placementService.findApplicationsByStudentId(studentId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public PlacementApplication applyForPlacement(@Argument Map<String, Object> input) {
        PlacementApplication app = new PlacementApplication();
        Long studentId = Long.valueOf(input.get("studentId").toString());
        app.setStudent(studentProfileRepository.findById(studentId)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", studentId)));
        Long driveId = Long.valueOf(input.get("driveId").toString());
        app.setDrive(placementDriveRepository.findById(driveId)
            .orElseThrow(() -> new ResourceNotFoundException("PlacementDrive", driveId)));
        app.setApplicationDate(LocalDate.now());
        app.setResumeUrl((String) input.get("resumeUrl"));
        app.setStatus("APPLIED");
        return placementService.createApplication(app);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PlacementApplication updatePlacementApplicationStatus(@Argument Long id, @Argument String status, @Argument String remarks) {
        PlacementApplication app = placementService.findApplicationById(id);
        app.setStatus(status);
        app.setRemarks(remarks);
        return placementService.createApplication(app);
    }

    // Placement Offers
    @QueryMapping
    public List<PlacementOffer> placementOffers(@Argument Long studentId) {
        return placementEnhancedService.findAllOffers().stream()
            .filter(o -> studentId == null || o.getStudent().getId().equals(studentId)).toList();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PlacementOffer createPlacementOffer(@Argument Map<String, Object> input) {
        PlacementOffer o = new PlacementOffer();
        if (input.get("studentId") != null) o.setStudent(studentProfileRepository.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("companyId") != null) o.setCompany(companyRepository.findById(Long.valueOf(input.get("companyId").toString())).orElse(null));
        if (input.get("driveId") != null) o.setDrive(placementDriveRepository.findById(Long.valueOf(input.get("driveId").toString())).orElse(null));
        o.setRole((String) input.get("role"));
        if (input.get("packageAmount") != null) o.setPackageAmount(new java.math.BigDecimal(input.get("packageAmount").toString()));
        if (input.get("joiningDate") != null) o.setJoiningDate(LocalDate.parse((String) input.get("joiningDate")));
        o.setStatus("OFFERED");
        return placementEnhancedService.createOffer(o);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public PlacementOffer updatePlacementOfferStatus(@Argument Long id, @Argument String status) {
        return placementEnhancedService.updateOfferStatus(id, status);
    }

    // Internships
    @QueryMapping
    public List<Internship> internships(@Argument Long studentId) {
        return placementEnhancedService.findAllInternships().stream()
            .filter(i -> studentId == null || i.getStudent().getId().equals(studentId)).toList();
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public Internship createInternship(@Argument Map<String, Object> input) {
        Internship i = new Internship();
        if (input.get("studentId") != null) i.setStudent(studentProfileRepository.findById(Long.valueOf(input.get("studentId").toString())).orElse(null));
        if (input.get("companyId") != null) i.setCompany(companyRepository.findById(Long.valueOf(input.get("companyId").toString())).orElse(null));
        i.setRole((String) input.get("role"));
        if (input.get("startDate") != null) i.setStartDate(LocalDate.parse((String) input.get("startDate")));
        if (input.get("endDate") != null) i.setEndDate(LocalDate.parse((String) input.get("endDate")));
        if (input.get("stipend") != null) i.setStipend(new java.math.BigDecimal(input.get("stipend").toString()));
        i.setMentorName((String) input.get("mentorName"));
        i.setStatus("ONGOING");
        return placementEnhancedService.createInternship(i);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    public Internship updateInternship(@Argument Long id, @Argument Map<String, Object> input) {
        Internship i = placementEnhancedService.findAllInternships().stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
        if (i == null) return null;
        if (input.containsKey("status")) i.setStatus((String) input.get("status"));
        return placementEnhancedService.updateInternship(i);
    }

    // Placement Statistics
    @QueryMapping
    public List<PlacementStatistics> placementStatistics(@Argument Long academicYearId, @Argument Long departmentId) {
        return placementEnhancedService.findAllStatistics().stream()
            .filter(s -> (academicYearId == null || s.getAcademicYear().getId().equals(academicYearId))
                && (departmentId == null || s.getDepartment().getId().equals(departmentId)))
            .toList();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PlacementStatistics generatePlacementStatistics(@Argument Long academicYearId, @Argument Long departmentId) {
        PlacementStatistics s = new PlacementStatistics();
        if (academicYearId != null) s.setAcademicYear(academicYearRepository.findById(academicYearId).orElse(null));
        if (departmentId != null) s.setDepartment(departmentRepository.findById(departmentId).orElse(null));
        return placementEnhancedService.generateStatistics(s);
    }
}
