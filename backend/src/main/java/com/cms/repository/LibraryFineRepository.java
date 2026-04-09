package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.LibraryFine;

public interface LibraryFineRepository extends JpaRepository<LibraryFine, Long> {
}
