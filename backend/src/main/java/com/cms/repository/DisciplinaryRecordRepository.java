package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.DisciplinaryRecord;

public interface DisciplinaryRecordRepository extends JpaRepository<DisciplinaryRecord, Long> {
    List<DisciplinaryRecord> findByStudentId(Long studentId);
}
