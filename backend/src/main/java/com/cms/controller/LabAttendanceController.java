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

import com.cms.dto.LabAttendanceRequest;
import com.cms.dto.LabAttendanceResponse;
import com.cms.service.LabAttendanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/lab-attendances")
public class LabAttendanceController {

    private final LabAttendanceService labAttendanceService;

    public LabAttendanceController(LabAttendanceService labAttendanceService) {
        this.labAttendanceService = labAttendanceService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<List<LabAttendanceResponse>> findAll() {
        return ResponseEntity.ok(labAttendanceService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabAttendanceResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(labAttendanceService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<LabAttendanceResponse> create(@Valid @RequestBody LabAttendanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(labAttendanceService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<LabAttendanceResponse> update(@PathVariable Long id,
                                                        @Valid @RequestBody LabAttendanceRequest request) {
        return ResponseEntity.ok(labAttendanceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labAttendanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
