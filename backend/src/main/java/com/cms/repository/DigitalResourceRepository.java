package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.DigitalResource;

public interface DigitalResourceRepository extends JpaRepository<DigitalResource, Long> {
}
