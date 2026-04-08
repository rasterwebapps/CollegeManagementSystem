package com.cms.controller;

import com.cms.dto.SyllabusRequest;
import com.cms.dto.SyllabusResponse;
import com.cms.service.SyllabusService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/syllabi")
public class SyllabusController {

    private final SyllabusService syllabusService;

    public SyllabusController(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }

    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<SyllabusResponse>> findByCourseId(
            @PathVariable Long courseId) {
        return ResponseEntity.ok(syllabusService.findByCourseId(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SyllabusResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(syllabusService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<SyllabusResponse> createOrUpdate(
            @Valid @RequestBody SyllabusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(syllabusService.createOrUpdate(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        syllabusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
