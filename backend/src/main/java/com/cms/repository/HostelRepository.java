package com.cms.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Hostel;

public interface HostelRepository extends JpaRepository<Hostel, Long> {
    Optional<Hostel> findByCode(String code);
    boolean existsByCode(String code);
}
