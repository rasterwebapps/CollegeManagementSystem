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

import com.cms.dto.SemesterRequest;
import com.cms.dto.SemesterResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.Semester;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
class SemesterServiceTest {

    @Mock private SemesterRepository semesterRepo;
    @Mock private AcademicYearRepository ayRepo;
    @InjectMocks private SemesterService service;

    private Semester semester;
    private AcademicYear ay;

    @BeforeEach
    void setUp() {
        ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2024-2025");

        semester = new Semester();
        semester.setId(1L);
        semester.setName("Fall 2024");
        semester.setSemesterNumber(1);
        semester.setAcademicYear(ay);
        semester.setStartDate(LocalDate.of(2024, 8, 1));
        semester.setEndDate(LocalDate.of(2024, 12, 31));
    }

    @Test
    void findAll() {
        when(semesterRepo.findAll()).thenReturn(List.of(semester));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    void findById_found() {
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        SemesterResponse r = service.findById(1L);
        assertThat(r.academicYearName()).isEqualTo("2024-2025");
    }

    @Test
    void findById_notFound() {
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_success() {
        SemesterRequest req = new SemesterRequest("Fall 2024", 1, 1L, LocalDate.of(2024, 8, 1), LocalDate.of(2024, 12, 31));
        when(ayRepo.findById(1L)).thenReturn(Optional.of(ay));
        when(semesterRepo.save(any())).thenReturn(semester);
        assertThat(service.create(req).name()).isEqualTo("Fall 2024");
    }

    @Test
    void create_ayNotFound() {
        SemesterRequest req = new SemesterRequest("Fall 2024", 1, 99L, LocalDate.of(2024, 8, 1), LocalDate.of(2024, 12, 31));
        when(ayRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_success() {
        SemesterRequest req = new SemesterRequest("Spring 2025", 2, 1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 5, 31));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(ayRepo.findById(1L)).thenReturn(Optional.of(ay));
        when(semesterRepo.save(any())).thenReturn(semester);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test
    void update_notFound() {
        SemesterRequest req = new SemesterRequest("Spring 2025", 2, 1L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 5, 31));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_ayNotFound() {
        SemesterRequest req = new SemesterRequest("Spring 2025", 2, 99L, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 5, 31));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(ayRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(semesterRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(semesterRepo).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(semesterRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
