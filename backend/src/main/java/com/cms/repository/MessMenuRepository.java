package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.MessMenu;

public interface MessMenuRepository extends JpaRepository<MessMenu, Long> {
}
