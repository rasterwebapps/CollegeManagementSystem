package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
