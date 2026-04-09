package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Announcement;
import com.cms.repository.AnnouncementRepository;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    public Announcement findById(Long id) {
        return announcementRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Announcement", id));
    }

    public List<Announcement> findByCategory(String category) {
        return announcementRepository.findByCategory(category);
    }

    public List<Announcement> findPinned() {
        return announcementRepository.findByPinnedTrue();
    }

    public Announcement create(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public Announcement update(Long id, Announcement announcementData) {
        Announcement announcement = findById(id);
        announcement.setTitle(announcementData.getTitle());
        announcement.setContent(announcementData.getContent());
        announcement.setCategory(announcementData.getCategory());
        announcement.setTargetRoles(announcementData.getTargetRoles());
        announcement.setTargetDepartments(announcementData.getTargetDepartments());
        announcement.setPriority(announcementData.getPriority());
        announcement.setExpiryDate(announcementData.getExpiryDate());
        announcement.setPinned(announcementData.getPinned());
        return announcementRepository.save(announcement);
    }

    public void delete(Long id) {
        if (!announcementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Announcement", id);
        }
        announcementRepository.deleteById(id);
    }
}
