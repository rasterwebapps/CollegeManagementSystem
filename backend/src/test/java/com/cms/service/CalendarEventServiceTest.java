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

import com.cms.dto.CalendarEventRequest;
import com.cms.dto.CalendarEventResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.CalendarEvent;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CalendarEventRepository;

@ExtendWith(MockitoExtension.class)
class CalendarEventServiceTest {

    @Mock private CalendarEventRepository eventRepo;
    @Mock private AcademicYearRepository ayRepo;
    @InjectMocks private CalendarEventService service;

    private CalendarEvent event;
    private AcademicYear ay;

    @BeforeEach
    void setUp() {
        ay = new AcademicYear();
        ay.setId(1L);
        ay.setName("2024-2025");

        event = new CalendarEvent();
        event.setId(1L);
        event.setTitle("Semester Start");
        event.setDescription("First day of semester");
        event.setEventDate(LocalDate.of(2024, 8, 1));
        event.setEventType("ACADEMIC");
        event.setAcademicYear(ay);
    }

    @Test
    void findAll() {
        when(eventRepo.findAll()).thenReturn(List.of(event));
        List<CalendarEventResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).academicYearName()).isEqualTo("2024-2025");
    }

    @Test
    void findById_found() {
        when(eventRepo.findById(1L)).thenReturn(Optional.of(event));
        assertThat(service.findById(1L).title()).isEqualTo("Semester Start");
    }

    @Test
    void findById_notFound() {
        when(eventRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_withAcademicYear() {
        CalendarEventRequest req = new CalendarEventRequest("Semester Start", "Desc", LocalDate.of(2024, 8, 1), "ACADEMIC", 1L);
        when(ayRepo.findById(1L)).thenReturn(Optional.of(ay));
        when(eventRepo.save(any())).thenReturn(event);
        assertThat(service.create(req).title()).isEqualTo("Semester Start");
    }

    @Test
    void create_withoutAcademicYear() {
        CalendarEvent noAy = new CalendarEvent();
        noAy.setId(2L);
        noAy.setTitle("Holiday");
        noAy.setEventDate(LocalDate.of(2024, 12, 25));
        noAy.setEventType("HOLIDAY");

        CalendarEventRequest req = new CalendarEventRequest("Holiday", null, LocalDate.of(2024, 12, 25), "HOLIDAY", null);
        when(eventRepo.save(any())).thenReturn(noAy);
        CalendarEventResponse result = service.create(req);
        assertThat(result.academicYearId()).isNull();
    }

    @Test
    void create_ayNotFound() {
        CalendarEventRequest req = new CalendarEventRequest("Event", "Desc", LocalDate.of(2024, 8, 1), "ACADEMIC", 99L);
        when(ayRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_withAcademicYear() {
        CalendarEventRequest req = new CalendarEventRequest("Updated", "Desc", LocalDate.of(2024, 8, 2), "EXAM", 1L);
        when(eventRepo.findById(1L)).thenReturn(Optional.of(event));
        when(ayRepo.findById(1L)).thenReturn(Optional.of(ay));
        when(eventRepo.save(any())).thenReturn(event);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test
    void update_removeAcademicYear() {
        CalendarEventRequest req = new CalendarEventRequest("Updated", "Desc", LocalDate.of(2024, 8, 2), "HOLIDAY", null);
        when(eventRepo.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepo.save(any())).thenReturn(event);
        service.update(1L, req);
        assertThat(event.getAcademicYear()).isNull();
    }

    @Test
    void update_notFound() {
        CalendarEventRequest req = new CalendarEventRequest("Updated", "Desc", LocalDate.of(2024, 8, 2), "EXAM", null);
        when(eventRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_ayNotFound() {
        CalendarEventRequest req = new CalendarEventRequest("Updated", "Desc", LocalDate.of(2024, 8, 2), "EXAM", 99L);
        when(eventRepo.findById(1L)).thenReturn(Optional.of(event));
        when(ayRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(eventRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(eventRepo).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(eventRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
