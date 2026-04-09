package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Hostel;
import com.cms.model.HostelRoom;
import com.cms.model.HostelAllocation;
import com.cms.repository.HostelRepository;
import com.cms.repository.HostelRoomRepository;
import com.cms.repository.HostelAllocationRepository;

@Service
public class HostelService {

    private final HostelRepository hostelRepository;
    private final HostelRoomRepository hostelRoomRepository;
    private final HostelAllocationRepository hostelAllocationRepository;

    public HostelService(HostelRepository hostelRepository,
                         HostelRoomRepository hostelRoomRepository,
                         HostelAllocationRepository hostelAllocationRepository) {
        this.hostelRepository = hostelRepository;
        this.hostelRoomRepository = hostelRoomRepository;
        this.hostelAllocationRepository = hostelAllocationRepository;
    }

    public List<Hostel> findAllHostels() {
        return hostelRepository.findAll();
    }

    public Hostel findHostelById(Long id) {
        return hostelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hostel", id));
    }

    public Hostel createHostel(Hostel hostel) {
        if (hostelRepository.existsByCode(hostel.getCode())) {
            throw new IllegalArgumentException("Hostel with code '" + hostel.getCode() + "' already exists");
        }
        return hostelRepository.save(hostel);
    }

    public List<HostelRoom> findRoomsByHostelId(Long hostelId) {
        return hostelRoomRepository.findByHostelId(hostelId);
    }

    public HostelRoom createRoom(HostelRoom room) {
        return hostelRoomRepository.save(room);
    }

    public List<HostelAllocation> findAllocationsByStudentId(Long studentId) {
        return hostelAllocationRepository.findByStudentId(studentId);
    }

    public HostelAllocation createAllocation(HostelAllocation allocation) {
        return hostelAllocationRepository.save(allocation);
    }

    public HostelAllocation vacateAllocation(Long id) {
        HostelAllocation allocation = hostelAllocationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("HostelAllocation", id));
        allocation.setStatus("VACATED");
        allocation.setVacateDate(java.time.LocalDate.now());
        return hostelAllocationRepository.save(allocation);
    }
}
