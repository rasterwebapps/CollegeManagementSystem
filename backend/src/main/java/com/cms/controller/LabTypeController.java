package com.cms.controller;

import com.cms.dto.LabTypeRequest;
import com.cms.dto.LabTypeResponse;
import com.cms.service.LabTypeService;
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
@RequestMapping("/api/v1/lab-types")
public class LabTypeController {

    private final LabTypeService labTypeService;

    public LabTypeController(LabTypeService labTypeService) {
        this.labTypeService = labTypeService;
    }

    @GetMapping
    public ResponseEntity<List<LabTypeResponse>> findAll() {
        return ResponseEntity.ok(labTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTypeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(labTypeService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LAB_INCHARGE')")
    public ResponseEntity<LabTypeResponse> create(
            @Valid @RequestBody LabTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(labTypeService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LAB_INCHARGE')")
    public ResponseEntity<LabTypeResponse> update(
            @PathVariable Long id, @Valid @RequestBody LabTypeRequest request) {
        return ResponseEntity.ok(labTypeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
