package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.ScholarshipRequest;
import com.cms.dto.ScholarshipResponse;
import com.cms.service.ScholarshipService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/scholarships")
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    public ScholarshipController(ScholarshipService scholarshipService) {
        this.scholarshipService = scholarshipService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<ScholarshipResponse>> findAll() {
        return ResponseEntity.ok(scholarshipService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY', 'ROLE_STUDENT')")
    public ResponseEntity<ScholarshipResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(scholarshipService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ScholarshipResponse> create(@Valid @RequestBody ScholarshipRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scholarshipService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ScholarshipResponse> update(@PathVariable Long id, @Valid @RequestBody ScholarshipRequest request) {
        return ResponseEntity.ok(scholarshipService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scholarshipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
