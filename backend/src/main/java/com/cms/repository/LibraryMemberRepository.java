package com.cms.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.LibraryMember;

public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {
    Optional<LibraryMember> findByMembershipNumber(String membershipNumber);
    Optional<LibraryMember> findByStudentProfileId(Long studentProfileId);
    Optional<LibraryMember> findByFacultyProfileId(Long facultyProfileId);
    boolean existsByMembershipNumber(String membershipNumber);
}
