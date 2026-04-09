package com.cms.service;

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

import com.cms.dto.ProgramRequest;
import com.cms.dto.ProgramResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.ProgramRepository;

@ExtendWith(MockitoExtension.class)
class ProgramServiceTest {

    @Mock
    private ProgramRepository programRepository;
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ProgramService programService;

    private Program program;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("Computer Science");
        department.setCode("CS");

        program = new Program();
        program.setId(1L);
        program.setName("B.Tech CS");
        program.setCode("BTCS");
        program.setDepartment(department);
        program.setDurationYears(4);
        program.setDegreeType("B.Tech");
    }

    @Test
    void findAll_returnsAll() {
        when(programRepository.findAll()).thenReturn(List.of(program));
        List<ProgramResponse> result = programService.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("B.Tech CS");
        assertThat(result.get(0).departmentName()).isEqualTo("Computer Science");
    }

    @Test
    void findAll_empty() {
        when(programRepository.findAll()).thenReturn(List.of());
        assertThat(programService.findAll()).isEmpty();
    }

    @Test
    void findById_found() {
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        ProgramResponse result = programService.findById(1L);
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.departmentId()).isEqualTo(1L);
    }

    @Test
    void findById_notFound() {
        when(programRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> programService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_success() {
        ProgramRequest request = new ProgramRequest("B.Tech CS", "BTCS", 1L, 4, "B.Tech");
        when(programRepository.existsByCode("BTCS")).thenReturn(false);
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(programRepository.save(any(Program.class))).thenReturn(program);

        ProgramResponse result = programService.create(request);
        assertThat(result.name()).isEqualTo("B.Tech CS");
        verify(programRepository).save(any(Program.class));
    }

    @Test
    void create_duplicateCode() {
        ProgramRequest request = new ProgramRequest("B.Tech CS", "BTCS", 1L, 4, "B.Tech");
        when(programRepository.existsByCode("BTCS")).thenReturn(true);
        assertThatThrownBy(() -> programService.create(request))
            .isInstanceOf(IllegalArgumentException.class);
        verify(programRepository, never()).save(any());
    }

    @Test
    void create_departmentNotFound() {
        ProgramRequest request = new ProgramRequest("B.Tech CS", "BTCS", 99L, 4, "B.Tech");
        when(programRepository.existsByCode("BTCS")).thenReturn(false);
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> programService.create(request))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_success() {
        ProgramRequest request = new ProgramRequest("Updated", "BTCS2", 1L, 4, "B.Tech");
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(programRepository.findByCode("BTCS2")).thenReturn(Optional.empty());
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(programRepository.save(any(Program.class))).thenReturn(program);

        ProgramResponse result = programService.update(1L, request);
        assertThat(result).isNotNull();
    }

    @Test
    void update_notFound() {
        ProgramRequest request = new ProgramRequest("Updated", "BTCS2", 1L, 4, "B.Tech");
        when(programRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> programService.update(99L, request))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_duplicateCode() {
        Program other = new Program();
        other.setId(2L);
        other.setCode("BTCS2");

        ProgramRequest request = new ProgramRequest("Updated", "BTCS2", 1L, 4, "B.Tech");
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(programRepository.findByCode("BTCS2")).thenReturn(Optional.of(other));
        assertThatThrownBy(() -> programService.update(1L, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void update_sameCode() {
        ProgramRequest request = new ProgramRequest("Updated", "BTCS", 1L, 4, "B.Tech");
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(programRepository.findByCode("BTCS")).thenReturn(Optional.of(program));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(programRepository.save(any(Program.class))).thenReturn(program);
        assertThat(programService.update(1L, request)).isNotNull();
    }

    @Test
    void update_departmentNotFound() {
        ProgramRequest request = new ProgramRequest("Updated", "BTCS2", 1L, 99, "B.Tech");
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));
        when(programRepository.findByCode("BTCS2")).thenReturn(Optional.empty());
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> programService.update(1L, request))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(programRepository.existsById(1L)).thenReturn(true);
        programService.delete(1L);
        verify(programRepository).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(programRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> programService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);
    }
}
