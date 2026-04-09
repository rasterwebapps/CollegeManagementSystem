package com.cms.service;

import java.math.BigDecimal;
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

import com.cms.dto.FeeStructureRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FeeStructure;
import com.cms.model.Program;
import com.cms.model.Semester;
import com.cms.repository.FeeStructureRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
class FeeStructureServiceTest {

    @Mock private FeeStructureRepository feeStructureRepo;
    @Mock private ProgramRepository programRepo;
    @Mock private SemesterRepository semesterRepo;
    @InjectMocks private FeeStructureService service;

    private FeeStructure feeStructure;
    private Program program;
    private Semester semester;

    @BeforeEach
    void setUp() {
        program = new Program();
        program.setId(1L);
        program.setName("B.Tech CS");
        semester = new Semester();
        semester.setId(1L);
        semester.setName("Sem 1");
        feeStructure = new FeeStructure();
        feeStructure.setId(1L);
        feeStructure.setProgram(program);
        feeStructure.setSemester(semester);
        feeStructure.setFeeType("TUITION");
        feeStructure.setAmount(new BigDecimal("50000.00"));
        feeStructure.setCurrency("INR");
        feeStructure.setEffectiveFrom(LocalDate.of(2024, 8, 1));
        feeStructure.setStatus("ACTIVE");
    }

    @Test void findAll() {
        when(feeStructureRepo.findAll()).thenReturn(List.of(feeStructure));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(feeStructureRepo.findById(1L)).thenReturn(Optional.of(feeStructure));
        assertThat(service.findById(1L).feeType()).isEqualTo("TUITION");
    }

    @Test void findById_notFound() {
        when(feeStructureRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        FeeStructureRequest req = new FeeStructureRequest(1L, 1L, "TUITION", new BigDecimal("50000"), "INR", LocalDate.of(2024, 8, 1), null, "ACTIVE");
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(feeStructureRepo.save(any())).thenReturn(feeStructure);
        assertThat(service.create(req).amount()).isEqualTo(new BigDecimal("50000.00"));
    }

    @Test void create_nullStatus() {
        FeeStructureRequest req = new FeeStructureRequest(1L, 1L, "TUITION", new BigDecimal("50000"), null, LocalDate.of(2024, 8, 1), null, null);
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(feeStructureRepo.save(any())).thenReturn(feeStructure);
        service.create(req);
        verify(feeStructureRepo).save(any());
    }

    @Test void create_programNotFound() {
        FeeStructureRequest req = new FeeStructureRequest(99L, 1L, "TUITION", new BigDecimal("50000"), null, LocalDate.of(2024, 8, 1), null, null);
        when(programRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        FeeStructureRequest req = new FeeStructureRequest(1L, 99L, "TUITION", new BigDecimal("50000"), null, LocalDate.of(2024, 8, 1), null, null);
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        FeeStructureRequest req = new FeeStructureRequest(1L, 1L, "LAB", new BigDecimal("60000"), "USD", LocalDate.of(2024, 8, 1), LocalDate.of(2025, 7, 31), "ACTIVE");
        when(feeStructureRepo.findById(1L)).thenReturn(Optional.of(feeStructure));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(feeStructureRepo.save(any())).thenReturn(feeStructure);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus() {
        FeeStructureRequest req = new FeeStructureRequest(1L, 1L, "LAB", new BigDecimal("60000"), null, LocalDate.of(2024, 8, 1), null, null);
        when(feeStructureRepo.findById(1L)).thenReturn(Optional.of(feeStructure));
        when(programRepo.findById(1L)).thenReturn(Optional.of(program));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(feeStructureRepo.save(any())).thenReturn(feeStructure);
        service.update(1L, req);
        assertThat(feeStructure.getStatus()).isEqualTo("ACTIVE");
    }

    @Test void update_notFound() {
        FeeStructureRequest req = new FeeStructureRequest(1L, 1L, "LAB", new BigDecimal("60000"), null, LocalDate.of(2024, 8, 1), null, null);
        when(feeStructureRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_programNotFound() {
        FeeStructureRequest req = new FeeStructureRequest(99L, 1L, "LAB", new BigDecimal("60000"), null, LocalDate.of(2024, 8, 1), null, null);
        when(feeStructureRepo.findById(1L)).thenReturn(Optional.of(feeStructure));
        when(programRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(feeStructureRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(feeStructureRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(feeStructureRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
