package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.RoomResourceRequest;
import com.cms.dto.RoomResourceResponse;
import com.cms.service.RoomResourceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/room-resources")
public class RoomResourceController {

    private final RoomResourceService roomResourceService;

    public RoomResourceController(RoomResourceService roomResourceService) {
        this.roomResourceService = roomResourceService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<List<RoomResourceResponse>> findAll() {
        return ResponseEntity.ok(roomResourceService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<RoomResourceResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomResourceService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResourceResponse> create(@Valid @RequestBody RoomResourceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomResourceService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomResourceResponse> update(@PathVariable Long id, @Valid @RequestBody RoomResourceRequest request) {
        return ResponseEntity.ok(roomResourceService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomResourceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
