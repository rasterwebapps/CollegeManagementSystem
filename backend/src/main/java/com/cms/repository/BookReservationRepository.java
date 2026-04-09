package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.BookReservation;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {
}
