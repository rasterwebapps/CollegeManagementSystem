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
import com.cms.model.AcademicYear;
import com.cms.model.CalendarEvent;
import com.cms.model.Course;
import com.cms.model.Department;
import com.cms.model.FacultyProfile;
import com.cms.model.Program;
import com.cms.model.Semester;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CalendarEventRepository;
import com.cms.repository.CourseRepository;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.SemesterRepository;

@Controller
public class AdminGraphQLController {

    private final DepartmentRepository departmentRepository;
    private final ProgramRepository programRepository;
    private final CourseRepository courseRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SemesterRepository semesterRepository;
    private final CalendarEventRepository calendarEventRepository;
    private final FacultyProfileRepository facultyProfileRepository;

    public AdminGraphQLController(DepartmentRepository departmentRepository,
                                   ProgramRepository programRepository,
                                   CourseRepository courseRepository,
                                   AcademicYearRepository academicYearRepository,
                                   SemesterRepository semesterRepository,
                                   CalendarEventRepository calendarEventRepository,
                                   FacultyProfileRepository facultyProfileRepository) {
        this.departmentRepository = departmentRepository;
        this.programRepository = programRepository;
        this.courseRepository = courseRepository;
        this.academicYearRepository = academicYearRepository;
        this.semesterRepository = semesterRepository;
        this.calendarEventRepository = calendarEventRepository;
        this.facultyProfileRepository = facultyProfileRepository;
    }

    @QueryMapping
    public List<Department> departments() { return departmentRepository.findAll(); }

    @QueryMapping
    public Department department(@Argument Long id) { return departmentRepository.findById(id).orElse(null); }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Department createDepartment(@Argument Map<String, Object> input) {
        String code = (String) input.get("code");
        if (departmentRepository.existsByCode(code)) {
            throw new IllegalArgumentException("Department with code '" + code + "' already exists");
        }
        Department dept = new Department();
        dept.setName((String) input.get("name"));
        dept.setCode(code);
        dept.setVision((String) input.get("vision"));
        dept.setMission((String) input.get("mission"));
        dept.setEmail((String) input.get("email"));
        dept.setPhone((String) input.get("phone"));
        dept.setOfficeLocation((String) input.get("officeLocation"));
        dept.setWebsite((String) input.get("website"));
        if (input.get("establishedYear") != null) dept.setEstablishedYear((Integer) input.get("establishedYear"));
        if (input.get("hodId") != null) {
            Long hodId = Long.valueOf(input.get("hodId").toString());
            dept.setHod(facultyProfileRepository.findById(hodId)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", hodId)));
        }
        return departmentRepository.save(dept);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Department updateDepartment(@Argument Long id, @Argument Map<String, Object> input) {
        Department dept = departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department", id));
        if (input.containsKey("name")) dept.setName((String) input.get("name"));
        if (input.containsKey("code")) {
            String code = (String) input.get("code");
            departmentRepository.findByCode(code)
                .filter(d -> !d.getId().equals(id))
                .ifPresent(d -> { throw new IllegalArgumentException("Department with code '" + code + "' already exists"); });
            dept.setCode(code);
        }
        if (input.containsKey("vision")) dept.setVision((String) input.get("vision"));
        if (input.containsKey("mission")) dept.setMission((String) input.get("mission"));
        if (input.containsKey("email")) dept.setEmail((String) input.get("email"));
        if (input.containsKey("phone")) dept.setPhone((String) input.get("phone"));
        return departmentRepository.save(dept);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteDepartment(@Argument Long id) {
        if (!departmentRepository.existsById(id)) throw new ResourceNotFoundException("Department", id);
        departmentRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public List<Program> programs() { return programRepository.findAll(); }

    @QueryMapping
    public Program program(@Argument Long id) { return programRepository.findById(id).orElse(null); }

    @QueryMapping
    public List<Program> programsByDepartment(@Argument Long departmentId) {
        return programRepository.findByDepartmentId(departmentId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Program createProgram(@Argument Map<String, Object> input) {
        Program program = new Program();
        program.setName((String) input.get("name"));
        program.setCode((String) input.get("code"));
        if (input.get("durationYears") != null) program.setDurationYears((Integer) input.get("durationYears"));
        program.setDegreeType((String) input.get("degreeType"));
        if (input.get("departmentId") != null) {
            Long deptId = Long.valueOf(input.get("departmentId").toString());
            program.setDepartment(departmentRepository.findById(deptId)
                .orElseThrow(() -> new ResourceNotFoundException("Department", deptId)));
        }
        if (input.get("totalIntake") != null) program.setTotalIntake((Integer) input.get("totalIntake"));
        if (input.get("eligibilityCriteria") != null) program.setEligibilityCriteria((String) input.get("eligibilityCriteria"));
        return programRepository.save(program);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Program updateProgram(@Argument Long id, @Argument Map<String, Object> input) {
        Program program = programRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Program", id));
        if (input.containsKey("name")) program.setName((String) input.get("name"));
        if (input.containsKey("code")) program.setCode((String) input.get("code"));
        if (input.containsKey("durationYears")) program.setDurationYears((Integer) input.get("durationYears"));
        if (input.containsKey("degreeType")) program.setDegreeType((String) input.get("degreeType"));
        return programRepository.save(program);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteProgram(@Argument Long id) {
        if (!programRepository.existsById(id)) throw new ResourceNotFoundException("Program", id);
        programRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public List<Course> courses() { return courseRepository.findAll(); }

    @QueryMapping
    public Course course(@Argument Long id) { return courseRepository.findById(id).orElse(null); }

    @QueryMapping
    public List<Course> coursesByProgram(@Argument Long programId) {
        return courseRepository.findByProgramId(programId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Course createCourse(@Argument Map<String, Object> input) {
        Course course = new Course();
        course.setName((String) input.get("name"));
        course.setCode((String) input.get("code"));
        if (input.get("credits") != null) course.setCredits((Integer) input.get("credits"));
        course.setCourseType((String) input.get("courseType"));
        if (input.get("programId") != null) {
            Long progId = Long.valueOf(input.get("programId").toString());
            course.setProgram(programRepository.findById(progId)
                .orElseThrow(() -> new ResourceNotFoundException("Program", progId)));
        }
        if (input.get("category") != null) course.setCategory((String) input.get("category"));
        return courseRepository.save(course);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Course updateCourse(@Argument Long id, @Argument Map<String, Object> input) {
        Course course = courseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Course", id));
        if (input.containsKey("name")) course.setName((String) input.get("name"));
        if (input.containsKey("code")) course.setCode((String) input.get("code"));
        if (input.containsKey("credits")) course.setCredits((Integer) input.get("credits"));
        if (input.containsKey("courseType")) course.setCourseType((String) input.get("courseType"));
        return courseRepository.save(course);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteCourse(@Argument Long id) {
        if (!courseRepository.existsById(id)) throw new ResourceNotFoundException("Course", id);
        courseRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public List<AcademicYear> academicYears() { return academicYearRepository.findAll(); }

    @QueryMapping
    public AcademicYear academicYear(@Argument Long id) { return academicYearRepository.findById(id).orElse(null); }

    @QueryMapping
    public AcademicYear activeAcademicYear() { return academicYearRepository.findByActiveTrue().orElse(null); }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AcademicYear createAcademicYear(@Argument Map<String, Object> input) {
        AcademicYear ay = new AcademicYear();
        ay.setName((String) input.get("name"));
        ay.setStartDate(LocalDate.parse((String) input.get("startDate")));
        ay.setEndDate(LocalDate.parse((String) input.get("endDate")));
        ay.setActive(input.get("isActive") != null ? (Boolean) input.get("isActive") : false);
        return academicYearRepository.save(ay);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AcademicYear updateAcademicYear(@Argument Long id, @Argument Map<String, Object> input) {
        AcademicYear ay = academicYearRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", id));
        if (input.containsKey("name")) ay.setName((String) input.get("name"));
        if (input.containsKey("isActive")) ay.setActive((Boolean) input.get("isActive"));
        return academicYearRepository.save(ay);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteAcademicYear(@Argument Long id) {
        if (!academicYearRepository.existsById(id)) throw new ResourceNotFoundException("AcademicYear", id);
        academicYearRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public List<Semester> semesters() { return semesterRepository.findAll(); }

    @QueryMapping
    public Semester semester(@Argument Long id) { return semesterRepository.findById(id).orElse(null); }

    @QueryMapping
    public List<Semester> semestersByAcademicYear(@Argument Long academicYearId) {
        return semesterRepository.findByAcademicYearId(academicYearId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Semester createSemester(@Argument Map<String, Object> input) {
        Semester s = new Semester();
        s.setName((String) input.get("name"));
        if (input.get("semesterNumber") != null) s.setSemesterNumber((Integer) input.get("semesterNumber"));
        if (input.get("startDate") != null) s.setStartDate(LocalDate.parse((String) input.get("startDate")));
        if (input.get("endDate") != null) s.setEndDate(LocalDate.parse((String) input.get("endDate")));
        if (input.get("academicYearId") != null) {
            Long ayId = Long.valueOf(input.get("academicYearId").toString());
            s.setAcademicYear(academicYearRepository.findById(ayId)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", ayId)));
        }
        return semesterRepository.save(s);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Semester updateSemester(@Argument Long id, @Argument Map<String, Object> input) {
        Semester s = semesterRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Semester", id));
        if (input.containsKey("name")) s.setName((String) input.get("name"));
        if (input.containsKey("semesterNumber")) s.setSemesterNumber((Integer) input.get("semesterNumber"));
        return semesterRepository.save(s);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteSemester(@Argument Long id) {
        if (!semesterRepository.existsById(id)) throw new ResourceNotFoundException("Semester", id);
        semesterRepository.deleteById(id);
        return true;
    }

    @QueryMapping
    public List<CalendarEvent> calendarEvents() { return calendarEventRepository.findAll(); }

    @QueryMapping
    public CalendarEvent calendarEvent(@Argument Long id) { return calendarEventRepository.findById(id).orElse(null); }

    @QueryMapping
    public List<CalendarEvent> calendarEventsByAcademicYear(@Argument Long academicYearId) {
        return calendarEventRepository.findByAcademicYearId(academicYearId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CalendarEvent createCalendarEvent(@Argument Map<String, Object> input) {
        CalendarEvent event = new CalendarEvent();
        event.setTitle((String) input.get("title"));
        event.setDescription((String) input.get("description"));
        event.setEventType((String) input.get("eventType"));
        if (input.get("eventDate") != null) event.setEventDate(LocalDate.parse((String) input.get("eventDate")));
        if (input.get("academicYearId") != null) {
            Long ayId = Long.valueOf(input.get("academicYearId").toString());
            event.setAcademicYear(academicYearRepository.findById(ayId)
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", ayId)));
        }
        return calendarEventRepository.save(event);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CalendarEvent updateCalendarEvent(@Argument Long id, @Argument Map<String, Object> input) {
        CalendarEvent event = calendarEventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("CalendarEvent", id));
        if (input.containsKey("title")) event.setTitle((String) input.get("title"));
        if (input.containsKey("description")) event.setDescription((String) input.get("description"));
        if (input.containsKey("eventType")) event.setEventType((String) input.get("eventType"));
        return calendarEventRepository.save(event);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean deleteCalendarEvent(@Argument Long id) {
        if (!calendarEventRepository.existsById(id)) throw new ResourceNotFoundException("CalendarEvent", id);
        calendarEventRepository.deleteById(id);
        return true;
    }
}
