package com.cms.controller;

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

import com.cms.dto.LabTimetableRequest;
import com.cms.dto.LabTimetableResponse;
import com.cms.service.LabTimetableService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/lab-timetables")
public class LabTimetableController {

    private final LabTimetableService labTimetableService;

    public LabTimetableController(LabTimetableService labTimetableService) {
        this.labTimetableService = labTimetableService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<List<LabTimetableResponse>> findAll() {
        return ResponseEntity.ok(labTimetableService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabTimetableResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(labTimetableService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LabTimetableResponse> create(@Valid @RequestBody LabTimetableRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(labTimetableService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LabTimetableResponse> update(@PathVariable Long id,
                                                       @Valid @RequestBody LabTimetableRequest request) {
        return ResponseEntity.ok(labTimetableService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labTimetableService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
