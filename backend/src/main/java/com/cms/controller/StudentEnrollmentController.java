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

import com.cms.dto.StudentEnrollmentRequest;
import com.cms.dto.StudentEnrollmentResponse;
import com.cms.service.StudentEnrollmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/student-enrollments")
public class StudentEnrollmentController {

    private final StudentEnrollmentService studentEnrollmentService;

    public StudentEnrollmentController(StudentEnrollmentService studentEnrollmentService) {
        this.studentEnrollmentService = studentEnrollmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<StudentEnrollmentResponse>> findAll() {
        return ResponseEntity.ok(studentEnrollmentService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY', 'ROLE_STUDENT')")
    public ResponseEntity<StudentEnrollmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(studentEnrollmentService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StudentEnrollmentResponse> create(@Valid @RequestBody StudentEnrollmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentEnrollmentService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StudentEnrollmentResponse> update(@PathVariable Long id,
                                                            @Valid @RequestBody StudentEnrollmentRequest request) {
        return ResponseEntity.ok(studentEnrollmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentEnrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
