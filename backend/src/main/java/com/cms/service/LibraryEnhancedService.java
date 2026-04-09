package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class LibraryEnhancedService {
    private final BookReservationRepository reservationRepo;
    private final DigitalResourceRepository digitalRepo;
    private final LibraryFineRepository fineRepo;

    public LibraryEnhancedService(BookReservationRepository reservationRepo, DigitalResourceRepository digitalRepo, LibraryFineRepository fineRepo) {
        this.reservationRepo = reservationRepo; this.digitalRepo = digitalRepo; this.fineRepo = fineRepo;
    }

    public List<BookReservation> findAllReservations() { return reservationRepo.findAll(); }
    public BookReservation createReservation(BookReservation r) { return reservationRepo.save(r); }
    public BookReservation cancelReservation(Long id) { return reservationRepo.findById(id).map(r -> { r.setStatus("CANCELLED"); return reservationRepo.save(r); }).orElse(null); }

    public List<DigitalResource> findAllDigitalResources() { return digitalRepo.findAll(); }
    public DigitalResource createDigitalResource(DigitalResource r) { return digitalRepo.save(r); }
    public DigitalResource updateDigitalResource(DigitalResource r) { return digitalRepo.save(r); }
    public void deleteDigitalResource(Long id) { digitalRepo.deleteById(id); }

    public List<LibraryFine> findAllFines() { return fineRepo.findAll(); }
    public LibraryFine payFine(Long id) { return fineRepo.findById(id).map(f -> { f.setStatus("PAID"); f.setPaidDate(java.time.LocalDate.now()); return fineRepo.save(f); }).orElse(null); }
}
