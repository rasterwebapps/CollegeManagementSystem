package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.RoomAllocation;

public interface RoomAllocationRepository extends JpaRepository<RoomAllocation, Long> {
}
