package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.TransportRoute;

public interface TransportRouteRepository extends JpaRepository<TransportRoute, Long> {
}
