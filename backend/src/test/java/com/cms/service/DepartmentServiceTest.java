package com.cms.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cms.dto.DepartmentRequest;
import com.cms.dto.DepartmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        department.setCode("CS");
    }

    @Test
    void findAll_returnsAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(List.of(department));

        List<DepartmentResponse> result = departmentService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Computer Science");
        assertThat(result.get(0).code()).isEqualTo("CS");
    }

    @Test
    void findAll_returnsEmptyList() {
        when(departmentRepository.findAll()).thenReturn(List.of());

        List<DepartmentResponse> result = departmentService.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void findById_returnsDepartment() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        DepartmentResponse result = departmentService.findById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Computer Science");
    }

    @Test
    void findById_throwsWhenNotFound() {
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> departmentService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("Department");
    }

    @Test
    void create_savesAndReturnsDepartment() {
        DepartmentRequest request = new DepartmentRequest("Computer Science", "CS");
        when(departmentRepository.existsByCode("CS")).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentResponse result = departmentService.create(request);

        assertThat(result.name()).isEqualTo("Computer Science");
        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void create_throwsWhenCodeExists() {
        DepartmentRequest request = new DepartmentRequest("Computer Science", "CS");
        when(departmentRepository.existsByCode("CS")).thenReturn(true);

        assertThatThrownBy(() -> departmentService.create(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("already exists");
        verify(departmentRepository, never()).save(any());
    }

    @Test
    void update_updatesAndReturnsDepartment() {
        DepartmentRequest request = new DepartmentRequest("Updated CS", "CS2");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.findByCode("CS2")).thenReturn(Optional.empty());
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentResponse result = departmentService.update(1L, request);

        assertThat(result).isNotNull();
        verify(departmentRepository).save(department);
    }

    @Test
    void update_throwsWhenNotFound() {
        DepartmentRequest request = new DepartmentRequest("Updated CS", "CS2");
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> departmentService.update(99L, request))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_throwsWhenDuplicateCode() {
        Department other = new Department();
        other.setId(2L);
        other.setCode("CS2");

        DepartmentRequest request = new DepartmentRequest("Updated CS", "CS2");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.findByCode("CS2")).thenReturn(Optional.of(other));

        assertThatThrownBy(() -> departmentService.update(1L, request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("already exists");
    }

    @Test
    void update_allowsSameCodeForSameEntity() {
        DepartmentRequest request = new DepartmentRequest("Updated CS", "CS");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.findByCode("CS")).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentResponse result = departmentService.update(1L, request);

        assertThat(result).isNotNull();
    }

    @Test
    void delete_deletesExistingDepartment() {
        when(departmentRepository.existsById(1L)).thenReturn(true);

        departmentService.delete(1L);

        verify(departmentRepository).deleteById(1L);
    }

    @Test
    void delete_throwsWhenNotFound() {
        when(departmentRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> departmentService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }
}
