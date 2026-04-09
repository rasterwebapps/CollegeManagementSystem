package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Refund;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}
