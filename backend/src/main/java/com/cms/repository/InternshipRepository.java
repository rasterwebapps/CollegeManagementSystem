package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.Internship;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
}
