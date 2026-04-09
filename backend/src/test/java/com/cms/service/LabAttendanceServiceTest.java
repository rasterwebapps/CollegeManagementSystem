package com.cms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cms.dto.LabAttendanceRequest;
import com.cms.dto.LabAttendanceResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Department;
import com.cms.model.Experiment;
import com.cms.model.FacultyProfile;
import com.cms.model.Lab;
import com.cms.model.LabAttendance;
import com.cms.model.LabTimetable;
import com.cms.model.Program;
import com.cms.model.Semester;
import com.cms.model.StudentProfile;
import com.cms.repository.ExperimentRepository;
import com.cms.repository.LabAttendanceRepository;
import com.cms.repository.LabTimetableRepository;
import com.cms.repository.StudentProfileRepository;

@ExtendWith(MockitoExtension.class)
class LabAttendanceServiceTest {

    @Mock private LabAttendanceRepository laRepo;
    @Mock private StudentProfileRepository studentRepo;
    @Mock private ExperimentRepository experimentRepo;
    @Mock private LabTimetableRepository ltRepo;
    @InjectMocks private LabAttendanceService service;

    private LabAttendance la;
    private StudentProfile student;
    private Experiment experiment;
    private LabTimetable labTimetable;

    @BeforeEach
    void setUp() {
        Department dept = new Department(); dept.setId(1L); dept.setName("CS");
        Program program = new Program(); program.setId(1L); program.setName("B.Tech");
        student = new StudentProfile();
        student.setId(1L); student.setFirstName("Alice"); student.setLastName("Smith");
        student.setProgram(program); student.setDepartment(dept);

        experiment = new Experiment(); experiment.setId(1L); experiment.setTitle("Exp 1");

        Lab lab = new Lab(); lab.setId(1L); lab.setName("Lab A");
        Course c = new Course(); c.setId(1L); c.setName("CS101");
        Semester sem = new Semester(); sem.setId(1L); sem.setName("Fall 2024");
        FacultyProfile fac = new FacultyProfile(); fac.setId(1L); fac.setFirstName("John"); fac.setLastName("Doe"); fac.setDepartment(dept);
        labTimetable = new LabTimetable();
        labTimetable.setId(1L); labTimetable.setLab(lab); labTimetable.setCourse(c);
        labTimetable.setSemester(sem); labTimetable.setFaculty(fac);

        la = new LabAttendance();
        la.setId(1L); la.setStudent(student); la.setExperiment(experiment);
        la.setLabTimetable(labTimetable); la.setAttendanceDate(LocalDate.of(2024,9,15));
        la.setStatus("PRESENT"); la.setRemarks("On time");
    }

    @Test void findAll() {
        when(laRepo.findAll()).thenReturn(List.of(la));
        List<LabAttendanceResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).status()).isEqualTo("PRESENT");
    }

    @Test void findById_found() {
        when(laRepo.findById(1L)).thenReturn(Optional.of(la));
        assertThat(service.findById(1L).experimentTitle()).isEqualTo("Exp 1");
    }

    @Test void findById_notFound() {
        when(laRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        LabAttendanceRequest req = new LabAttendanceRequest(1L, 1L, 1L, LocalDate.of(2024,9,15), "PRESENT", "On time");
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(experimentRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(ltRepo.findById(1L)).thenReturn(Optional.of(labTimetable));
        when(laRepo.save(any())).thenReturn(la);
        assertThat(service.create(req).status()).isEqualTo("PRESENT");
    }

    @Test void create_studentNotFound() {
        LabAttendanceRequest req = new LabAttendanceRequest(99L, 1L, 1L, LocalDate.of(2024,9,15), "PRESENT", null);
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_experimentNotFound() {
        LabAttendanceRequest req = new LabAttendanceRequest(1L, 99L, 1L, LocalDate.of(2024,9,15), "PRESENT", null);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(experimentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_labTimetableNotFound() {
        LabAttendanceRequest req = new LabAttendanceRequest(1L, 1L, 99L, LocalDate.of(2024,9,15), "PRESENT", null);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(experimentRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(ltRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        LabAttendanceRequest req = new LabAttendanceRequest(1L, 1L, 1L, LocalDate.of(2024,9,16), "ABSENT", "Late");
        when(laRepo.findById(1L)).thenReturn(Optional.of(la));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(experimentRepo.findById(1L)).thenReturn(Optional.of(experiment));
        when(ltRepo.findById(1L)).thenReturn(Optional.of(labTimetable));
        when(laRepo.save(any())).thenReturn(la);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_notFound() {
        LabAttendanceRequest req = new LabAttendanceRequest(1L, 1L, 1L, LocalDate.of(2024,9,15), "PRESENT", null);
        when(laRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_studentNotFound() {
        LabAttendanceRequest req = new LabAttendanceRequest(99L, 1L, 1L, LocalDate.of(2024,9,15), "PRESENT", null);
        when(laRepo.findById(1L)).thenReturn(Optional.of(la));
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(laRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(laRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(laRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
