package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.TheoryTimetable;

public interface TheoryTimetableRepository extends JpaRepository<TheoryTimetable, Long> {
}
