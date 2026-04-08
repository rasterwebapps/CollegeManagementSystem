package com.cms.controller;

import com.cms.dto.CalendarEventRequest;
import com.cms.dto.CalendarEventResponse;
import com.cms.service.CalendarEventService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calendar-events")
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    public CalendarEventController(CalendarEventService calendarEventService) {
        this.calendarEventService = calendarEventService;
    }

    @GetMapping
    public ResponseEntity<List<CalendarEventResponse>> findAll() {
        return ResponseEntity.ok(calendarEventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarEventResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(calendarEventService.findById(id));
    }

    @GetMapping("/by-academic-year/{academicYearId}")
    public ResponseEntity<List<CalendarEventResponse>> findByAcademicYearId(
            @PathVariable Long academicYearId) {
        return ResponseEntity.ok(calendarEventService.findByAcademicYearId(academicYearId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CalendarEventResponse> create(
            @Valid @RequestBody CalendarEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(calendarEventService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CalendarEventResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CalendarEventRequest request) {
        return ResponseEntity.ok(calendarEventService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        calendarEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
