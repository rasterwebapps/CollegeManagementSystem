package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.SoftwareLicense;

public interface SoftwareLicenseRepository extends JpaRepository<SoftwareLicense, Long> {
}
