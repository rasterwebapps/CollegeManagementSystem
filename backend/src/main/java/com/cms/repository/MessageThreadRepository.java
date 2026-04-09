package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.MessageThread;

public interface MessageThreadRepository extends JpaRepository<MessageThread, Long> {
}
