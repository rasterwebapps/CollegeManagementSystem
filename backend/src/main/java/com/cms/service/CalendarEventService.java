package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.CalendarEventRequest;
import com.cms.dto.CalendarEventResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.CalendarEvent;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CalendarEventRepository;

@Service
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final AcademicYearRepository academicYearRepository;

    public CalendarEventService(CalendarEventRepository calendarEventRepository,
                                AcademicYearRepository academicYearRepository) {
        this.calendarEventRepository = calendarEventRepository;
        this.academicYearRepository = academicYearRepository;
    }

    public List<CalendarEventResponse> findAll() {
        return calendarEventRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public CalendarEventResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public CalendarEventResponse create(CalendarEventRequest request) {
        CalendarEvent event = new CalendarEvent();
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setEventDate(request.eventDate());
        event.setEventType(request.eventType());
        if (request.academicYearId() != null) {
            AcademicYear ay = academicYearRepository.findById(request.academicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
            event.setAcademicYear(ay);
        }
        return toResponse(calendarEventRepository.save(event));
    }

    public CalendarEventResponse update(Long id, CalendarEventRequest request) {
        CalendarEvent event = getOrThrow(id);
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setEventDate(request.eventDate());
        event.setEventType(request.eventType());
        if (request.academicYearId() != null) {
            AcademicYear ay = academicYearRepository.findById(request.academicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
            event.setAcademicYear(ay);
        } else {
            event.setAcademicYear(null);
        }
        return toResponse(calendarEventRepository.save(event));
    }

    public void delete(Long id) {
        if (!calendarEventRepository.existsById(id)) {
            throw new ResourceNotFoundException("CalendarEvent", id);
        }
        calendarEventRepository.deleteById(id);
    }

    private CalendarEvent getOrThrow(Long id) {
        return calendarEventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("CalendarEvent", id));
    }

    private CalendarEventResponse toResponse(CalendarEvent e) {
        return new CalendarEventResponse(
            e.getId(), e.getTitle(), e.getDescription(),
            e.getEventDate(), e.getEventType(),
            e.getAcademicYear() != null ? e.getAcademicYear().getId() : null,
            e.getAcademicYear() != null ? e.getAcademicYear().getName() : null,
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
