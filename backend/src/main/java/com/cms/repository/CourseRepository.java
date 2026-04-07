package com.cms.repository;

import com.cms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCode(String code);

    List<Course> findByProgramId(Long programId);

    List<Course> findByDepartmentId(Long departmentId);

    List<Course> findByActiveTrue();
}
