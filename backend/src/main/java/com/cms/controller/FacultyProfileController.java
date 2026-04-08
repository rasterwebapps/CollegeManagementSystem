package com.cms.controller;

import com.cms.dto.FacultyProfileRequest;
import com.cms.dto.FacultyProfileResponse;
import com.cms.service.FacultyProfileService;
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
@RequestMapping("/api/v1/faculty")
public class FacultyProfileController {

    private final FacultyProfileService facultyProfileService;

    public FacultyProfileController(FacultyProfileService facultyProfileService) {
        this.facultyProfileService = facultyProfileService;
    }

    @GetMapping
    public ResponseEntity<List<FacultyProfileResponse>> findAll() {
        return ResponseEntity.ok(facultyProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyProfileResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyProfileService.findById(id));
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<FacultyProfileResponse>> findByDepartmentId(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(facultyProfileService.findByDepartmentId(departmentId));
    }

    @GetMapping("/by-keycloak/{keycloakUserId}")
    public ResponseEntity<FacultyProfileResponse> findByKeycloakUserId(
            @PathVariable String keycloakUserId) {
        return ResponseEntity.ok(facultyProfileService.findByKeycloakUserId(keycloakUserId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyProfileResponse> create(
            @Valid @RequestBody FacultyProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facultyProfileService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyProfileResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody FacultyProfileRequest request) {
        return ResponseEntity.ok(facultyProfileService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
