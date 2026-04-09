package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.TransportPass;

public interface TransportPassRepository extends JpaRepository<TransportPass, Long> {
}
