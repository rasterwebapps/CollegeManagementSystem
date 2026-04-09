package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.RoomResourceRequest;
import com.cms.dto.RoomResourceResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Lab;
import com.cms.model.RoomResource;
import com.cms.repository.LabRepository;
import com.cms.repository.RoomResourceRepository;

@Service
public class RoomResourceService {

    private final RoomResourceRepository roomResourceRepository;
    private final LabRepository labRepository;

    public RoomResourceService(RoomResourceRepository roomResourceRepository,
                               LabRepository labRepository) {
        this.roomResourceRepository = roomResourceRepository;
        this.labRepository = labRepository;
    }

    public List<RoomResourceResponse> findAll() {
        return roomResourceRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RoomResourceResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public RoomResourceResponse create(RoomResourceRequest request) {
        RoomResource rr = new RoomResource();
        rr.setRoomNumber(request.roomNumber());
        rr.setBuilding(request.building());
        rr.setFloor(request.floor());
        rr.setRoomType(request.roomType());
        rr.setCapacity(request.capacity());
        if (request.labId() != null) {
            Lab lab = labRepository.findById(request.labId())
                .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
            rr.setLab(lab);
        }
        rr.setStatus(request.status() != null ? request.status() : "AVAILABLE");
        return toResponse(roomResourceRepository.save(rr));
    }

    public RoomResourceResponse update(Long id, RoomResourceRequest request) {
        RoomResource rr = getOrThrow(id);
        rr.setRoomNumber(request.roomNumber());
        rr.setBuilding(request.building());
        rr.setFloor(request.floor());
        rr.setRoomType(request.roomType());
        rr.setCapacity(request.capacity());
        if (request.labId() != null) {
            Lab lab = labRepository.findById(request.labId())
                .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
            rr.setLab(lab);
        } else {
            rr.setLab(null);
        }
        if (request.status() != null) { rr.setStatus(request.status()); }
        return toResponse(roomResourceRepository.save(rr));
    }

    public void delete(Long id) {
        if (!roomResourceRepository.existsById(id)) {
            throw new ResourceNotFoundException("RoomResource", id);
        }
        roomResourceRepository.deleteById(id);
    }

    private RoomResource getOrThrow(Long id) {
        return roomResourceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("RoomResource", id));
    }

    private RoomResourceResponse toResponse(RoomResource rr) {
        return new RoomResourceResponse(
            rr.getId(), rr.getRoomNumber(), rr.getBuilding(), rr.getFloor(),
            rr.getRoomType(), rr.getCapacity(),
            rr.getLab() != null ? rr.getLab().getId() : null,
            rr.getLab() != null ? rr.getLab().getName() : null,
            rr.getStatus(), rr.getCreatedAt(), rr.getUpdatedAt()
        );
    }
}
