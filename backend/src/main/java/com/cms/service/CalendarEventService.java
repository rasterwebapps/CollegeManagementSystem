package com.cms.service;

import com.cms.dto.CalendarEventRequest;
import com.cms.dto.CalendarEventResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.CalendarEvent;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CalendarEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final AcademicYearRepository academicYearRepository;

    public CalendarEventService(CalendarEventRepository calendarEventRepository,
                                AcademicYearRepository academicYearRepository) {
        this.calendarEventRepository = calendarEventRepository;
        this.academicYearRepository = academicYearRepository;
    }

    @Transactional(readOnly = true)
    public List<CalendarEventResponse> findAll() {
        return calendarEventRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CalendarEventResponse findById(Long id) {
        CalendarEvent event = calendarEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarEvent", id));
        return toResponse(event);
    }

    @Transactional(readOnly = true)
    public List<CalendarEventResponse> findByAcademicYearId(Long academicYearId) {
        return calendarEventRepository.findByAcademicYearId(academicYearId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CalendarEventResponse create(CalendarEventRequest request) {
        AcademicYear academicYear = academicYearRepository.findById(request.academicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));

        CalendarEvent event = new CalendarEvent();
        event.setAcademicYear(academicYear);
        event.setTitle(request.title());
        event.setEventType(request.eventType());
        event.setStartDate(request.startDate());
        event.setEndDate(request.endDate());
        event.setDescription(request.description());
        CalendarEvent saved = calendarEventRepository.save(event);
        return toResponse(saved);
    }

    @Transactional
    public CalendarEventResponse update(Long id, CalendarEventRequest request) {
        CalendarEvent event = calendarEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarEvent", id));
        AcademicYear academicYear = academicYearRepository.findById(request.academicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));

        event.setAcademicYear(academicYear);
        event.setTitle(request.title());
        event.setEventType(request.eventType());
        event.setStartDate(request.startDate());
        event.setEndDate(request.endDate());
        event.setDescription(request.description());
        CalendarEvent saved = calendarEventRepository.save(event);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        CalendarEvent event = calendarEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarEvent", id));
        calendarEventRepository.delete(event);
    }

    private CalendarEventResponse toResponse(CalendarEvent entity) {
        return new CalendarEventResponse(
                entity.getId(),
                entity.getAcademicYear().getId(),
                entity.getAcademicYear().getName(),
                entity.getTitle(),
                entity.getEventType(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getDescription()
        );
    }
}
