package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByIndustry(String industry);
    List<Company> findByTier(String tier);
    List<Company> findByNameContainingIgnoreCase(String name);
}
