package com.cms.repository;

import com.cms.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    boolean existsByCode(String code);

    List<Program> findByDepartmentId(Long departmentId);

    List<Program> findByActiveTrue();
}
