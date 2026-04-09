package com.cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.QuestionPaper;

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long> {
}
