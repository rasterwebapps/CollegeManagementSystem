package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FacultyQualification;
import com.cms.model.ResearchPublication;
import com.cms.model.LeaveApplication;
import com.cms.repository.FacultyQualificationRepository;
import com.cms.repository.ResearchPublicationRepository;
import com.cms.repository.LeaveApplicationRepository;

@Service
public class FacultyDetailService {

    private final FacultyQualificationRepository qualificationRepository;
    private final ResearchPublicationRepository publicationRepository;
    private final LeaveApplicationRepository leaveRepository;

    public FacultyDetailService(FacultyQualificationRepository qualificationRepository,
                                ResearchPublicationRepository publicationRepository,
                                LeaveApplicationRepository leaveRepository) {
        this.qualificationRepository = qualificationRepository;
        this.publicationRepository = publicationRepository;
        this.leaveRepository = leaveRepository;
    }

    public List<FacultyQualification> findQualificationsByFacultyId(Long facultyId) {
        return qualificationRepository.findByFacultyId(facultyId);
    }

    public FacultyQualification createQualification(FacultyQualification qualification) {
        return qualificationRepository.save(qualification);
    }

    public void deleteQualification(Long id) {
        if (!qualificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("FacultyQualification", id);
        }
        qualificationRepository.deleteById(id);
    }

    public List<ResearchPublication> findPublicationsByFacultyId(Long facultyId) {
        return publicationRepository.findByFacultyId(facultyId);
    }

    public ResearchPublication createPublication(ResearchPublication publication) {
        return publicationRepository.save(publication);
    }

    public ResearchPublication updatePublication(Long id, ResearchPublication data) {
        ResearchPublication publication = publicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ResearchPublication", id));
        if (data.getTitle() != null) publication.setTitle(data.getTitle());
        if (data.getJournalOrConference() != null) publication.setJournalOrConference(data.getJournalOrConference());
        if (data.getPublicationType() != null) publication.setPublicationType(data.getPublicationType());
        if (data.getDoi() != null) publication.setDoi(data.getDoi());
        if (data.getImpactFactor() != null) publication.setImpactFactor(data.getImpactFactor());
        return publicationRepository.save(publication);
    }

    public List<LeaveApplication> findLeavesByFacultyId(Long facultyId) {
        return leaveRepository.findByFacultyId(facultyId);
    }

    public List<LeaveApplication> findLeavesByStatus(String status) {
        return leaveRepository.findByStatus(status);
    }

    public LeaveApplication createLeave(LeaveApplication leave) {
        return leaveRepository.save(leave);
    }

    public LeaveApplication updateLeaveStatus(Long id, String status, String remarks) {
        LeaveApplication leave = leaveRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LeaveApplication", id));
        leave.setStatus(status);
        if (remarks != null) {
            leave.setRemarks(remarks);
        }
        return leaveRepository.save(leave);
    }
}
