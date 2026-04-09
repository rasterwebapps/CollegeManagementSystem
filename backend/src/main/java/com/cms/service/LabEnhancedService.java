package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class LabEnhancedService {
    private final LabSafetyRecordRepository safetyRepo;
    private final LabReservationRepository reservationRepo;
    private final SoftwareLicenseRepository licenseRepo;

    public LabEnhancedService(LabSafetyRecordRepository safetyRepo, LabReservationRepository reservationRepo, SoftwareLicenseRepository licenseRepo) {
        this.safetyRepo = safetyRepo; this.reservationRepo = reservationRepo; this.licenseRepo = licenseRepo;
    }

    public List<LabSafetyRecord> findSafetyRecordsByLab(Long labId) { return safetyRepo.findAll().stream().filter(r -> r.getLab().getId().equals(labId)).toList(); }
    public LabSafetyRecord createSafetyRecord(LabSafetyRecord r) { return safetyRepo.save(r); }

    public List<LabReservation> findReservationsByLab(Long labId) { return reservationRepo.findAll().stream().filter(r -> r.getLab().getId().equals(labId)).toList(); }
    public LabReservation createReservation(LabReservation r) { return reservationRepo.save(r); }
    public LabReservation updateReservationStatus(Long id, String status) { return reservationRepo.findById(id).map(r -> { r.setStatus(status); return reservationRepo.save(r); }).orElse(null); }

    public List<SoftwareLicense> findAllLicenses() { return licenseRepo.findAll(); }
    public List<SoftwareLicense> findLicensesByLab(Long labId) { return licenseRepo.findAll().stream().filter(l -> l.getLab() != null && l.getLab().getId().equals(labId)).toList(); }
    public SoftwareLicense createLicense(SoftwareLicense l) { return licenseRepo.save(l); }
    public SoftwareLicense updateLicense(SoftwareLicense l) { return licenseRepo.save(l); }
}
