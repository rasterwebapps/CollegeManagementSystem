package com.cms.controller;

import com.cms.dto.FacultyLabExpertiseRequest;
import com.cms.dto.FacultyLabExpertiseResponse;
import com.cms.service.FacultyLabExpertiseService;
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
@RequestMapping("/api/v1/faculty-lab-expertise")
public class FacultyLabExpertiseController {

    private final FacultyLabExpertiseService facultyLabExpertiseService;

    public FacultyLabExpertiseController(
            FacultyLabExpertiseService facultyLabExpertiseService) {
        this.facultyLabExpertiseService = facultyLabExpertiseService;
    }

    @GetMapping("/by-faculty/{facultyId}")
    public ResponseEntity<List<FacultyLabExpertiseResponse>> findByFacultyId(
            @PathVariable Long facultyId) {
        return ResponseEntity.ok(facultyLabExpertiseService.findByFacultyId(facultyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyLabExpertiseResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyLabExpertiseService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyLabExpertiseResponse> create(
            @Valid @RequestBody FacultyLabExpertiseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facultyLabExpertiseService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyLabExpertiseResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody FacultyLabExpertiseRequest request) {
        return ResponseEntity.ok(facultyLabExpertiseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyLabExpertiseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
