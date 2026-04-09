package com.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.model.CalendarEvent;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    List<CalendarEvent> findByAcademicYearId(Long academicYearId);
    List<CalendarEvent> findByEventType(String eventType);
}
