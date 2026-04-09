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

import com.cms.dto.FacultyProfileRequest;
import com.cms.dto.FacultyProfileResponse;
import com.cms.service.FacultyProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/faculty-profiles")
public class FacultyProfileController {

    private final FacultyProfileService facultyProfileService;

    public FacultyProfileController(FacultyProfileService facultyProfileService) {
        this.facultyProfileService = facultyProfileService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<FacultyProfileResponse>> findAll() {
        return ResponseEntity.ok(facultyProfileService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<FacultyProfileResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyProfileService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FacultyProfileResponse> create(@Valid @RequestBody FacultyProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyProfileService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FacultyProfileResponse> update(@PathVariable Long id,
                                                         @Valid @RequestBody FacultyProfileRequest request) {
        return ResponseEntity.ok(facultyProfileService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
