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

import com.cms.dto.StudentProfileRequest;
import com.cms.dto.StudentProfileResponse;
import com.cms.service.StudentProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/student-profiles")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<StudentProfileResponse>> findAll() {
        return ResponseEntity.ok(studentProfileService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY', 'ROLE_STUDENT')")
    public ResponseEntity<StudentProfileResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentProfileService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StudentProfileResponse> create(@Valid @RequestBody StudentProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentProfileService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StudentProfileResponse> update(@PathVariable Long id,
                                                         @Valid @RequestBody StudentProfileRequest request) {
        return ResponseEntity.ok(studentProfileService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
