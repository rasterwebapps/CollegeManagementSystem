package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.FeeConcession;

public interface FeeConcessionRepository extends JpaRepository<FeeConcession, Long> {
}
