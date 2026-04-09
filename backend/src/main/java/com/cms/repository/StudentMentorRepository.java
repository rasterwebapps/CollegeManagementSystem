package com.cms.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cms.model.StudentMentor;

public interface StudentMentorRepository extends JpaRepository<StudentMentor, Long> {
    List<StudentMentor> findByStudentId(Long studentId);
    List<StudentMentor> findByFacultyId(Long facultyId);
    Optional<StudentMentor> findByStudentIdAndActiveTrue(Long studentId);
}
