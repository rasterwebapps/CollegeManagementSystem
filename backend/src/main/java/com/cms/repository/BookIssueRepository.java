package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.BookIssue;

public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    List<BookIssue> findByMemberId(Long memberId);
    List<BookIssue> findByBookId(Long bookId);
    List<BookIssue> findByMemberIdAndStatus(Long memberId, String status);
    List<BookIssue> findByStatus(String status);
}
