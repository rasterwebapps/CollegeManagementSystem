package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.GpaRecordRequest;
import com.cms.dto.GpaRecordResponse;
import com.cms.service.GpaRecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/gpa-records")
public class GpaRecordController {

    private final GpaRecordService gpaRecordService;

    public GpaRecordController(GpaRecordService gpaRecordService) {
        this.gpaRecordService = gpaRecordService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<GpaRecordResponse>> findAll() {
        return ResponseEntity.ok(gpaRecordService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<GpaRecordResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gpaRecordService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<GpaRecordResponse> create(@Valid @RequestBody GpaRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gpaRecordService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<GpaRecordResponse> update(@PathVariable Long id, @Valid @RequestBody GpaRecordRequest request) {
        return ResponseEntity.ok(gpaRecordService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gpaRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
