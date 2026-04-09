package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.dto.AttendanceAlertRequest;
import com.cms.dto.AttendanceAlertResponse;
import com.cms.service.AttendanceAlertService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/attendance-alerts")
public class AttendanceAlertController {

    private final AttendanceAlertService attendanceAlertService;

    public AttendanceAlertController(AttendanceAlertService attendanceAlertService) {
        this.attendanceAlertService = attendanceAlertService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<AttendanceAlertResponse>> findAll() {
        return ResponseEntity.ok(attendanceAlertService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY', 'ROLE_STUDENT')")
    public ResponseEntity<AttendanceAlertResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceAlertService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttendanceAlertResponse> create(@Valid @RequestBody AttendanceAlertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceAlertService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AttendanceAlertResponse> update(@PathVariable Long id,
                                                          @Valid @RequestBody AttendanceAlertRequest request) {
        return ResponseEntity.ok(attendanceAlertService.update(id, request));
    }

    @PatchMapping("/{id}/resolve")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<AttendanceAlertResponse> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(attendanceAlertService.resolve(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attendanceAlertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
