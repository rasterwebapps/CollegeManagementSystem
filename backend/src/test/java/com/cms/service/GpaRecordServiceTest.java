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

import com.cms.dto.GpaRecordRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.GpaRecord;
import com.cms.model.Semester;
import com.cms.model.StudentProfile;
import com.cms.repository.GpaRecordRepository;
import com.cms.repository.SemesterRepository;
import com.cms.repository.StudentProfileRepository;

@ExtendWith(MockitoExtension.class)
class GpaRecordServiceTest {

    @Mock private GpaRecordRepository gpaRecordRepo;
    @Mock private StudentProfileRepository studentRepo;
    @Mock private SemesterRepository semesterRepo;
    @InjectMocks private GpaRecordService service;

    private GpaRecord record;
    private StudentProfile student;
    private Semester semester;

    @BeforeEach
    void setUp() {
        student = new StudentProfile(); student.setId(1L); student.setFirstName("John"); student.setLastName("Doe");
        semester = new Semester(); semester.setId(1L); semester.setName("Fall 2024");
        record = new GpaRecord();
        record.setId(1L); record.setStudent(student); record.setSemester(semester);
        record.setSemesterGpa(new BigDecimal("3.75")); record.setCgpa(new BigDecimal("3.60"));
        record.setTotalCredits(18); record.setEarnedCredits(18);
        record.setLabComponentGpa(new BigDecimal("3.80"));
        record.setCalculationDate(LocalDate.of(2024, 6, 30)); record.setStatus("CALCULATED");
    }

    @Test void findAll() {
        when(gpaRecordRepo.findAll()).thenReturn(List.of(record));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(gpaRecordRepo.findById(1L)).thenReturn(Optional.of(record));
        assertThat(service.findById(1L).status()).isEqualTo("CALCULATED");
    }

    @Test void findById_notFound() {
        when(gpaRecordRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        GpaRecordRequest req = new GpaRecordRequest(1L, 1L, new BigDecimal("3.75"), new BigDecimal("3.60"), 18, 18, new BigDecimal("3.80"), LocalDate.of(2024, 6, 30), "CALCULATED");
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(gpaRecordRepo.save(any())).thenReturn(record);
        assertThat(service.create(req).status()).isEqualTo("CALCULATED");
    }

    @Test void create_nullStatus() {
        GpaRecordRequest req = new GpaRecordRequest(1L, 1L, new BigDecimal("3.75"), new BigDecimal("3.60"), 18, 18, null, LocalDate.of(2024, 6, 30), null);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(gpaRecordRepo.save(any())).thenReturn(record);
        service.create(req);
        verify(gpaRecordRepo).save(any());
    }

    @Test void create_studentNotFound() {
        GpaRecordRequest req = new GpaRecordRequest(99L, 1L, new BigDecimal("3.75"), new BigDecimal("3.60"), 18, 18, null, LocalDate.of(2024, 6, 30), null);
        when(studentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_semesterNotFound() {
        GpaRecordRequest req = new GpaRecordRequest(1L, 99L, new BigDecimal("3.75"), new BigDecimal("3.60"), 18, 18, null, LocalDate.of(2024, 6, 30), null);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(semesterRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        GpaRecordRequest req = new GpaRecordRequest(1L, 1L, new BigDecimal("3.80"), new BigDecimal("3.70"), 20, 20, new BigDecimal("3.90"), LocalDate.of(2024, 7, 1), "VERIFIED");
        when(gpaRecordRepo.findById(1L)).thenReturn(Optional.of(record));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(gpaRecordRepo.save(any())).thenReturn(record);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus() {
        GpaRecordRequest req = new GpaRecordRequest(1L, 1L, new BigDecimal("3.80"), new BigDecimal("3.70"), 20, 20, null, LocalDate.of(2024, 7, 1), null);
        when(gpaRecordRepo.findById(1L)).thenReturn(Optional.of(record));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));
        when(semesterRepo.findById(1L)).thenReturn(Optional.of(semester));
        when(gpaRecordRepo.save(any())).thenReturn(record);
        service.update(1L, req);
        assertThat(record.getStatus()).isEqualTo("CALCULATED");
    }

    @Test void update_notFound() {
        GpaRecordRequest req = new GpaRecordRequest(1L, 1L, new BigDecimal("3.80"), new BigDecimal("3.70"), 20, 20, null, LocalDate.of(2024, 7, 1), null);
        when(gpaRecordRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(gpaRecordRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(gpaRecordRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(gpaRecordRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
