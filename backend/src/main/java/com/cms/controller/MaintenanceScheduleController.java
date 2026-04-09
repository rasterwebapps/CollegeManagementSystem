package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.MaintenanceScheduleRequest;
import com.cms.dto.MaintenanceScheduleResponse;
import com.cms.service.MaintenanceScheduleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/maintenance-schedules")
public class MaintenanceScheduleController {

    private final MaintenanceScheduleService maintenanceScheduleService;

    public MaintenanceScheduleController(MaintenanceScheduleService maintenanceScheduleService) {
        this.maintenanceScheduleService = maintenanceScheduleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE', 'ROLE_TECHNICIAN')")
    public ResponseEntity<List<MaintenanceScheduleResponse>> findAll() {
        return ResponseEntity.ok(maintenanceScheduleService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE', 'ROLE_TECHNICIAN')")
    public ResponseEntity<MaintenanceScheduleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceScheduleService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<MaintenanceScheduleResponse> create(@Valid @RequestBody MaintenanceScheduleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceScheduleService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<MaintenanceScheduleResponse> update(@PathVariable Long id, @Valid @RequestBody MaintenanceScheduleRequest request) {
        return ResponseEntity.ok(maintenanceScheduleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        maintenanceScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
