package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
