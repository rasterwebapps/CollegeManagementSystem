package com.cms.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.cms.model.*;
import com.cms.repository.*;

@Service
public class PlacementEnhancedService {
    private final PlacementOfferRepository offerRepo;
    private final InternshipRepository internshipRepo;
    private final PlacementStatisticsRepository statsRepo;

    public PlacementEnhancedService(PlacementOfferRepository offerRepo, InternshipRepository internshipRepo, PlacementStatisticsRepository statsRepo) {
        this.offerRepo = offerRepo; this.internshipRepo = internshipRepo; this.statsRepo = statsRepo;
    }

    public List<PlacementOffer> findAllOffers() { return offerRepo.findAll(); }
    public PlacementOffer createOffer(PlacementOffer o) { return offerRepo.save(o); }
    public PlacementOffer updateOfferStatus(Long id, String status) { return offerRepo.findById(id).map(o -> { o.setStatus(status); return offerRepo.save(o); }).orElse(null); }

    public List<Internship> findAllInternships() { return internshipRepo.findAll(); }
    public Internship createInternship(Internship i) { return internshipRepo.save(i); }
    public Internship updateInternship(Internship i) { return internshipRepo.save(i); }

    public List<PlacementStatistics> findAllStatistics() { return statsRepo.findAll(); }
    public PlacementStatistics generateStatistics(PlacementStatistics s) { return statsRepo.save(s); }
}
