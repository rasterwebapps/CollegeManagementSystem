package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.ConsumableStockRequest;
import com.cms.dto.ConsumableStockResponse;
import com.cms.service.ConsumableStockService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/consumable-stocks")
public class ConsumableStockController {

    private final ConsumableStockService consumableStockService;

    public ConsumableStockController(ConsumableStockService consumableStockService) {
        this.consumableStockService = consumableStockService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE', 'ROLE_TECHNICIAN')")
    public ResponseEntity<List<ConsumableStockResponse>> findAll() {
        return ResponseEntity.ok(consumableStockService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE', 'ROLE_TECHNICIAN')")
    public ResponseEntity<ConsumableStockResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(consumableStockService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<ConsumableStockResponse> create(@Valid @RequestBody ConsumableStockRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consumableStockService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<ConsumableStockResponse> update(@PathVariable Long id, @Valid @RequestBody ConsumableStockRequest request) {
        return ResponseEntity.ok(consumableStockService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        consumableStockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
