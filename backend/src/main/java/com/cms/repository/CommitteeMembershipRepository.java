package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.CommitteeMembership;

public interface CommitteeMembershipRepository extends JpaRepository<CommitteeMembership, Long> {
}
