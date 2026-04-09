package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.PlacementOffer;

public interface PlacementOfferRepository extends JpaRepository<PlacementOffer, Long> {
}
