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

import com.cms.dto.AcademicYearRequest;
import com.cms.dto.AcademicYearResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.repository.AcademicYearRepository;

@ExtendWith(MockitoExtension.class)
class AcademicYearServiceTest {

    @Mock private AcademicYearRepository repo;
    @InjectMocks private AcademicYearService service;

    private AcademicYear ay;

    @BeforeEach
    void setUp() {
        ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2024-2025");
        ay.setStartDate(LocalDate.of(2024, 8, 1));
        ay.setEndDate(LocalDate.of(2025, 5, 31));
        ay.setActive(true);
    }

    @Test
    void findAll() {
        when(repo.findAll()).thenReturn(List.of(ay));
        List<AcademicYearResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("2024-2025");
    }

    @Test
    void findById_found() {
        when(repo.findById(1L)).thenReturn(Optional.of(ay));
        assertThat(service.findById(1L).active()).isTrue();
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_success() {
        AcademicYearRequest req = new AcademicYearRequest("2024-2025", LocalDate.of(2024, 8, 1), LocalDate.of(2025, 5, 31), true);
        when(repo.save(any(AcademicYear.class))).thenReturn(ay);
        AcademicYearResponse result = service.create(req);
        assertThat(result.name()).isEqualTo("2024-2025");
    }

    @Test
    void create_nullActive_defaultsFalse() {
        AcademicYearRequest req = new AcademicYearRequest("2024-2025", LocalDate.of(2024, 8, 1), LocalDate.of(2025, 5, 31), null);
        AcademicYear saved = new AcademicYear();
        saved.setId(2L);
        saved.setName("2024-2025");
        saved.setStartDate(LocalDate.of(2024, 8, 1));
        saved.setEndDate(LocalDate.of(2025, 5, 31));
        saved.setActive(false);
        when(repo.save(any(AcademicYear.class))).thenReturn(saved);
        AcademicYearResponse result = service.create(req);
        assertThat(result.active()).isFalse();
    }

    @Test
    void update_success() {
        AcademicYearRequest req = new AcademicYearRequest("Updated", LocalDate.of(2024, 9, 1), LocalDate.of(2025, 6, 30), false);
        when(repo.findById(1L)).thenReturn(Optional.of(ay));
        when(repo.save(any(AcademicYear.class))).thenReturn(ay);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test
    void update_nullActive_keepsCurrent() {
        AcademicYearRequest req = new AcademicYearRequest("Updated", LocalDate.of(2024, 9, 1), LocalDate.of(2025, 6, 30), null);
        when(repo.findById(1L)).thenReturn(Optional.of(ay));
        when(repo.save(any(AcademicYear.class))).thenReturn(ay);
        service.update(1L, req);
        assertThat(ay.getActive()).isTrue();
    }

    @Test
    void update_notFound() {
        AcademicYearRequest req = new AcademicYearRequest("Updated", LocalDate.of(2024, 9, 1), LocalDate.of(2025, 6, 30), true);
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(repo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
