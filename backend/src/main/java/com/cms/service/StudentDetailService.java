package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.StudentGuardian;
import com.cms.model.StudentDocument;
import com.cms.model.StudentAchievement;
import com.cms.model.StudentPreviousEducation;
import com.cms.model.DisciplinaryRecord;
import com.cms.model.StudentMentor;
import com.cms.model.Backlog;
import com.cms.repository.StudentGuardianRepository;
import com.cms.repository.StudentDocumentRepository;
import com.cms.repository.StudentAchievementRepository;
import com.cms.repository.StudentPreviousEducationRepository;
import com.cms.repository.DisciplinaryRecordRepository;
import com.cms.repository.StudentMentorRepository;
import com.cms.repository.BacklogRepository;

@Service
public class StudentDetailService {

    private final StudentGuardianRepository guardianRepository;
    private final StudentDocumentRepository documentRepository;
    private final StudentAchievementRepository achievementRepository;
    private final StudentPreviousEducationRepository previousEducationRepository;
    private final DisciplinaryRecordRepository disciplinaryRepository;
    private final StudentMentorRepository mentorRepository;
    private final BacklogRepository backlogRepository;

    public StudentDetailService(StudentGuardianRepository guardianRepository,
                                StudentDocumentRepository documentRepository,
                                StudentAchievementRepository achievementRepository,
                                StudentPreviousEducationRepository previousEducationRepository,
                                DisciplinaryRecordRepository disciplinaryRepository,
                                StudentMentorRepository mentorRepository,
                                BacklogRepository backlogRepository) {
        this.guardianRepository = guardianRepository;
        this.documentRepository = documentRepository;
        this.achievementRepository = achievementRepository;
        this.previousEducationRepository = previousEducationRepository;
        this.disciplinaryRepository = disciplinaryRepository;
        this.mentorRepository = mentorRepository;
        this.backlogRepository = backlogRepository;
    }

    public List<StudentGuardian> findGuardiansByStudentId(Long studentId) {
        return guardianRepository.findByStudentId(studentId);
    }

    public StudentGuardian createGuardian(StudentGuardian guardian) {
        return guardianRepository.save(guardian);
    }

    public void deleteGuardian(Long id) {
        if (!guardianRepository.existsById(id)) {
            throw new ResourceNotFoundException("StudentGuardian", id);
        }
        guardianRepository.deleteById(id);
    }

    public List<StudentDocument> findDocumentsByStudentId(Long studentId) {
        return documentRepository.findByStudentId(studentId);
    }

    public StudentDocument createDocument(StudentDocument document) {
        return documentRepository.save(document);
    }

    public void deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new ResourceNotFoundException("StudentDocument", id);
        }
        documentRepository.deleteById(id);
    }

    public List<StudentAchievement> findAchievementsByStudentId(Long studentId) {
        return achievementRepository.findByStudentId(studentId);
    }

    public StudentAchievement createAchievement(StudentAchievement achievement) {
        return achievementRepository.save(achievement);
    }

    public List<StudentPreviousEducation> findPreviousEducationByStudentId(Long studentId) {
        return previousEducationRepository.findByStudentId(studentId);
    }

    public StudentPreviousEducation createPreviousEducation(StudentPreviousEducation education) {
        return previousEducationRepository.save(education);
    }

    public List<DisciplinaryRecord> findDisciplinaryRecordsByStudentId(Long studentId) {
        return disciplinaryRepository.findByStudentId(studentId);
    }

    public DisciplinaryRecord createDisciplinaryRecord(DisciplinaryRecord record) {
        return disciplinaryRepository.save(record);
    }

    public StudentMentor findMentorByStudentId(Long studentId) {
        return mentorRepository.findByStudentIdAndActiveTrue(studentId).orElse(null);
    }

    public StudentMentor assignMentor(StudentMentor mentor) {
        return mentorRepository.save(mentor);
    }

    public List<Backlog> findBacklogsByStudentId(Long studentId) {
        return backlogRepository.findByStudentId(studentId);
    }

    public List<Backlog> findActiveBacklogsByStudentId(Long studentId) {
        return backlogRepository.findByStudentIdAndStatus(studentId, "ACTIVE");
    }

    public Backlog createBacklog(Backlog backlog) {
        return backlogRepository.save(backlog);
    }

    public Backlog updateBacklogStatus(Long id, String status) {
        Backlog backlog = backlogRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Backlog", id));
        backlog.setStatus(status);
        return backlogRepository.save(backlog);
    }
}
