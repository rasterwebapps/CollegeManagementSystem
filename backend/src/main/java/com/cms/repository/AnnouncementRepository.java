package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByCategory(String category);
    List<Announcement> findByStatus(String status);
    List<Announcement> findByPinnedTrue();
}
