package com.cms.controller;

import com.cms.dto.CourseOutcomeRequest;
import com.cms.dto.CourseOutcomeResponse;
import com.cms.service.CourseOutcomeService;
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
@RequestMapping("/api/v1/course-outcomes")
public class CourseOutcomeController {

    private final CourseOutcomeService courseOutcomeService;

    public CourseOutcomeController(CourseOutcomeService courseOutcomeService) {
        this.courseOutcomeService = courseOutcomeService;
    }

    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<CourseOutcomeResponse>> findByCourseId(
            @PathVariable Long courseId) {
        return ResponseEntity.ok(courseOutcomeService.findByCourseId(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseOutcomeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(courseOutcomeService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<CourseOutcomeResponse> create(
            @Valid @RequestBody CourseOutcomeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseOutcomeService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<CourseOutcomeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CourseOutcomeRequest request) {
        return ResponseEntity.ok(courseOutcomeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseOutcomeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
