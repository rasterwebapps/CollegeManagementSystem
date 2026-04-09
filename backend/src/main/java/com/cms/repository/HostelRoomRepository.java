package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.HostelRoom;

public interface HostelRoomRepository extends JpaRepository<HostelRoom, Long> {
    List<HostelRoom> findByHostelId(Long hostelId);
    List<HostelRoom> findByHostelIdAndStatus(Long hostelId, String status);
}
