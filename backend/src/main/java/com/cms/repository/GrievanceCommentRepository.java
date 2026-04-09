package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.GrievanceComment;

public interface GrievanceCommentRepository extends JpaRepository<GrievanceComment, Long> {
}
