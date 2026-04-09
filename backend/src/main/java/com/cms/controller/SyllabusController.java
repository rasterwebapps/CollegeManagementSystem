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

import com.cms.dto.SyllabusRequest;
import com.cms.dto.SyllabusResponse;
import com.cms.service.SyllabusService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/syllabi")
public class SyllabusController {

    private final SyllabusService syllabusService;

    public SyllabusController(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<SyllabusResponse>> findAll() {
        return ResponseEntity.ok(syllabusService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<SyllabusResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(syllabusService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<SyllabusResponse> create(@Valid @RequestBody SyllabusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(syllabusService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<SyllabusResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody SyllabusRequest request) {
        return ResponseEntity.ok(syllabusService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        syllabusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
