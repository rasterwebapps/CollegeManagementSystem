package com.cms.controller;

import com.cms.dto.FacultyLabAssignmentRequest;
import com.cms.dto.FacultyLabAssignmentResponse;
import com.cms.service.FacultyLabAssignmentService;
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
@RequestMapping("/api/v1/faculty-lab-assignments")
public class FacultyLabAssignmentController {

    private final FacultyLabAssignmentService facultyLabAssignmentService;

    public FacultyLabAssignmentController(
            FacultyLabAssignmentService facultyLabAssignmentService) {
        this.facultyLabAssignmentService = facultyLabAssignmentService;
    }

    @GetMapping("/by-faculty/{facultyId}")
    public ResponseEntity<List<FacultyLabAssignmentResponse>> findByFacultyId(
            @PathVariable Long facultyId) {
        return ResponseEntity.ok(facultyLabAssignmentService.findByFacultyId(facultyId));
    }

    @GetMapping("/by-lab/{labId}")
    public ResponseEntity<List<FacultyLabAssignmentResponse>> findByLabId(
            @PathVariable Long labId) {
        return ResponseEntity.ok(facultyLabAssignmentService.findByLabId(labId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyLabAssignmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyLabAssignmentService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyLabAssignmentResponse> create(
            @Valid @RequestBody FacultyLabAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facultyLabAssignmentService.create(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyLabAssignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
