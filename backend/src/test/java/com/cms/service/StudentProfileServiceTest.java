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

import com.cms.dto.StudentProfileRequest;
import com.cms.dto.StudentProfileResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.model.StudentProfile;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.StudentProfileRepository;

@ExtendWith(MockitoExtension.class)
class StudentProfileServiceTest {

    @Mock private StudentProfileRepository spRepo;
    @Mock private ProgramRepository programRepo;
    @Mock private DepartmentRepository deptRepo;
    @InjectMocks private StudentProfileService service;

    private StudentProfile sp;
    private Program program;
    private Department dept;

    @BeforeEach
    void setUp() {
        program = new Program(); program.setId(1L); program.setName("B.Tech");
        dept = new Department(); dept.setId(1L); dept.setName("CS");

        sp = new StudentProfile();
        sp.setId(1L); sp.setKeycloakId("kc-s-1"); sp.setEnrollmentNumber("EN001");
        sp.setFirstName("Alice"); sp.setLastName("Smith"); sp.setEmail("alice@college.edu");
        sp.setPhone("1234567890"); sp.setProgram(program); sp.setDepartment(dept);
        sp.setCurrentSemester(1); sp.setAdmissionDate(LocalDate.of(2024,8,1)); sp.setStatus("ACTIVE");
    }

    @Test void findAll() {
        when(spRepo.findAll()).thenReturn(List.of(sp));
        List<StudentProfileResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).enrollmentNumber()).isEqualTo("EN001");
    }

    @Test void findById_found() {
        when(spRepo.findById(1L)).thenReturn(Optional.of(sp));
        assertThat(service.findById(1L).firstName()).isEqualTo("Alice");
    }

    @Test void findById_notFound() {
        when(spRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "alice@college.edu", "1234567890", 1L, 1L, 1, LocalDate.of(2024,8,1), "ACTIVE");
        when(spRepo.existsByEnrollmentNumber("EN001")).thenReturn(false);
        when(spRepo.existsByEmail("alice@college.edu")).thenReturn(false);
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(spRepo.save(any())).thenReturn(sp);
        assertThat(service.create(req).firstName()).isEqualTo("Alice");
    }

    @Test void create_nullStatus_defaultsActive() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "alice@college.edu", null, 1L, 1L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.existsByEnrollmentNumber("EN001")).thenReturn(false);
        when(spRepo.existsByEmail("alice@college.edu")).thenReturn(false);
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(spRepo.save(any())).thenReturn(sp);
        service.create(req);
        verify(spRepo).save(any());
    }

    @Test void create_duplicateEnrollmentNumber() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "alice@college.edu", null, 1L, 1L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.existsByEnrollmentNumber("EN001")).thenReturn(true);
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Enrollment number already exists");
    }

    @Test void create_duplicateEmail() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "alice@college.edu", null, 1L, 1L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.existsByEnrollmentNumber("EN001")).thenReturn(false);
        when(spRepo.existsByEmail("alice@college.edu")).thenReturn(true);
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Email already exists");
    }

    @Test void create_programNotFound() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "alice@college.edu", null, 99L, 1L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.existsByEnrollmentNumber("EN001")).thenReturn(false);
        when(spRepo.existsByEmail("alice@college.edu")).thenReturn(false);
        when(programRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_deptNotFound() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "alice@college.edu", null, 1L, 99L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.existsByEnrollmentNumber("EN001")).thenReturn(false);
        when(spRepo.existsByEmail("alice@college.edu")).thenReturn(false);
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(deptRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN002", "Bob", "Jones", "bob@college.edu", "0987654321", 1L, 1L, 2, LocalDate.of(2024,8,1), "INACTIVE");
        when(spRepo.findById(1L)).thenReturn(Optional.of(sp));
        when(spRepo.findByEnrollmentNumber("EN002")).thenReturn(Optional.empty());
        when(spRepo.findByEmail("bob@college.edu")).thenReturn(Optional.empty());
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(spRepo.save(any())).thenReturn(sp);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus_keepsCurrent() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "alice@college.edu", null, 1L, 1L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.findById(1L)).thenReturn(Optional.of(sp));
        when(spRepo.findByEnrollmentNumber("EN001")).thenReturn(Optional.of(sp));
        when(spRepo.findByEmail("alice@college.edu")).thenReturn(Optional.of(sp));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(spRepo.save(any())).thenReturn(sp);
        service.update(1L, req);
        assertThat(sp.getStatus()).isEqualTo("ACTIVE");
    }

    @Test void update_notFound() {
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "alice@college.edu", null, 1L, 1L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_duplicateEnrollmentNumber() {
        StudentProfile other = new StudentProfile(); other.setId(2L);
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN002", "Alice", "Smith", "alice@college.edu", null, 1L, 1L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.findById(1L)).thenReturn(Optional.of(sp));
        when(spRepo.findByEnrollmentNumber("EN002")).thenReturn(Optional.of(other));
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Enrollment number already exists");
    }

    @Test void update_duplicateEmail() {
        StudentProfile other = new StudentProfile(); other.setId(2L);
        StudentProfileRequest req = new StudentProfileRequest("kc-s-1", "EN001", "Alice", "Smith", "dup@college.edu", null, 1L, 1L, 1, LocalDate.of(2024,8,1), null);
        when(spRepo.findById(1L)).thenReturn(Optional.of(sp));
        when(spRepo.findByEnrollmentNumber("EN001")).thenReturn(Optional.of(sp));
        when(spRepo.findByEmail("dup@college.edu")).thenReturn(Optional.of(other));
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Email already exists");
    }

    @Test void delete_success() {
        when(spRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(spRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(spRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
