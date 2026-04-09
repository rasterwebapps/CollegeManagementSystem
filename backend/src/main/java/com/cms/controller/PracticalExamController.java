package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.PracticalExamRequest;
import com.cms.dto.PracticalExamResponse;
import com.cms.service.PracticalExamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/practical-exams")
public class PracticalExamController {

    private final PracticalExamService practicalExamService;

    public PracticalExamController(PracticalExamService practicalExamService) {
        this.practicalExamService = practicalExamService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<PracticalExamResponse>> findAll() {
        return ResponseEntity.ok(practicalExamService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<PracticalExamResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(practicalExamService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<PracticalExamResponse> create(@Valid @RequestBody PracticalExamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(practicalExamService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<PracticalExamResponse> update(@PathVariable Long id, @Valid @RequestBody PracticalExamRequest request) {
        return ResponseEntity.ok(practicalExamService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        practicalExamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
