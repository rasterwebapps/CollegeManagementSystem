package com.cms.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Grievance;
import com.cms.repository.GrievanceRepository;

@Service
public class GrievanceService {

    private final GrievanceRepository grievanceRepository;

    public GrievanceService(GrievanceRepository grievanceRepository) {
        this.grievanceRepository = grievanceRepository;
    }

    public List<Grievance> findAll() {
        return grievanceRepository.findAll();
    }

    public Grievance findById(Long id) {
        return grievanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Grievance", id));
    }

    public List<Grievance> findByComplainant(String keycloakId) {
        return grievanceRepository.findByComplainantKeycloakId(keycloakId);
    }

    public List<Grievance> findByStatus(String status) {
        return grievanceRepository.findByStatus(status);
    }

    public Grievance findByTicketNumber(String ticketNumber) {
        return grievanceRepository.findByTicketNumber(ticketNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Grievance with ticket " + ticketNumber + " not found"));
    }

    public Grievance create(Grievance grievance) {
        grievance.setTicketNumber("GRV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return grievanceRepository.save(grievance);
    }

    public Grievance updateStatus(Long id, String status, String resolution) {
        Grievance grievance = findById(id);
        grievance.setStatus(status);
        if (resolution != null) {
            grievance.setResolution(resolution);
        }
        if ("RESOLVED".equals(status) || "CLOSED".equals(status)) {
            grievance.setResolvedDate(Instant.now());
        }
        return grievanceRepository.save(grievance);
    }
}
