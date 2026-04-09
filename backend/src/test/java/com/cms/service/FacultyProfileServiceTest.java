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

import com.cms.dto.FacultyProfileRequest;
import com.cms.dto.FacultyProfileResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.FacultyProfile;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.FacultyProfileRepository;

@ExtendWith(MockitoExtension.class)
class FacultyProfileServiceTest {

    @Mock private FacultyProfileRepository fpRepo;
    @Mock private DepartmentRepository deptRepo;
    @InjectMocks private FacultyProfileService service;

    private FacultyProfile fp;
    private Department dept;

    @BeforeEach
    void setUp() {
        dept = new Department();
        dept.setId(1L);
        dept.setName("CS");

        fp = new FacultyProfile();
        fp.setId(1L);
        fp.setKeycloakId("kc-fp-1");
        fp.setEmployeeCode("EMP001");
        fp.setFirstName("John");
        fp.setLastName("Doe");
        fp.setEmail("john@college.edu");
        fp.setPhone("1234567890");
        fp.setDepartment(dept);
        fp.setDesignation("Professor");
        fp.setJoiningDate(LocalDate.of(2020, 1, 1));
        fp.setStatus("ACTIVE");
    }

    @Test void findAll() {
        when(fpRepo.findAll()).thenReturn(List.of(fp));
        List<FacultyProfileResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).departmentName()).isEqualTo("CS");
    }

    @Test void findById_found() {
        when(fpRepo.findById(1L)).thenReturn(Optional.of(fp));
        assertThat(service.findById(1L).employeeCode()).isEqualTo("EMP001");
    }

    @Test void findById_notFound() {
        when(fpRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        FacultyProfileRequest req = new FacultyProfileRequest("kc-fp-1", "EMP001", "John", "Doe", "john@college.edu", "1234567890", 1L, "Professor", LocalDate.of(2020, 1, 1), "ACTIVE");
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(fpRepo.save(any())).thenReturn(fp);
        assertThat(service.create(req).firstName()).isEqualTo("John");
    }

    @Test void create_nullStatus_defaultsActive() {
        FacultyProfileRequest req = new FacultyProfileRequest("kc-fp-1", "EMP001", "John", "Doe", "john@college.edu", null, 1L, "Professor", LocalDate.of(2020, 1, 1), null);
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(fpRepo.save(any())).thenReturn(fp);
        service.create(req);
        verify(fpRepo).save(any());
    }

    @Test void create_deptNotFound() {
        FacultyProfileRequest req = new FacultyProfileRequest("kc-fp-1", "EMP001", "John", "Doe", "john@college.edu", null, 99L, "Professor", LocalDate.of(2020, 1, 1), null);
        when(deptRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        FacultyProfileRequest req = new FacultyProfileRequest("kc-fp-1", "EMP002", "Jane", "Doe", "jane@college.edu", "0987654321", 1L, "Asst Prof", LocalDate.of(2021, 1, 1), "INACTIVE");
        when(fpRepo.findById(1L)).thenReturn(Optional.of(fp));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(fpRepo.save(any())).thenReturn(fp);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus_keepsCurrent() {
        FacultyProfileRequest req = new FacultyProfileRequest("kc-fp-1", "EMP002", "Jane", "Doe", "jane@college.edu", null, 1L, "Asst Prof", LocalDate.of(2021, 1, 1), null);
        when(fpRepo.findById(1L)).thenReturn(Optional.of(fp));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(fpRepo.save(any())).thenReturn(fp);
        service.update(1L, req);
        assertThat(fp.getStatus()).isEqualTo("ACTIVE");
    }

    @Test void update_notFound() {
        FacultyProfileRequest req = new FacultyProfileRequest("kc-fp-1", "EMP002", "Jane", "Doe", "jane@college.edu", null, 1L, "Asst Prof", LocalDate.of(2021, 1, 1), null);
        when(fpRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_deptNotFound() {
        FacultyProfileRequest req = new FacultyProfileRequest("kc-fp-1", "EMP002", "Jane", "Doe", "jane@college.edu", null, 99L, "Asst Prof", LocalDate.of(2021, 1, 1), null);
        when(fpRepo.findById(1L)).thenReturn(Optional.of(fp));
        when(deptRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(fpRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(fpRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(fpRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
