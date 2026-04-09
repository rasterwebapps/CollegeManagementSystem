package com.cms.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cms.dto.RoomResourceRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Lab;
import com.cms.model.RoomResource;
import com.cms.repository.LabRepository;
import com.cms.repository.RoomResourceRepository;

@ExtendWith(MockitoExtension.class)
class RoomResourceServiceTest {

    @Mock private RoomResourceRepository roomResourceRepo;
    @Mock private LabRepository labRepo;
    @InjectMocks private RoomResourceService service;

    private RoomResource room;
    private Lab lab;

    @BeforeEach
    void setUp() {
        lab = new Lab(); lab.setId(1L); lab.setName("Physics Lab");
        room = new RoomResource();
        room.setId(1L); room.setRoomNumber("R101"); room.setBuilding("Main");
        room.setFloor(1); room.setRoomType("LAB"); room.setCapacity(40);
        room.setLab(lab); room.setStatus("AVAILABLE");
    }

    @Test void findAll() {
        when(roomResourceRepo.findAll()).thenReturn(List.of(room));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(roomResourceRepo.findById(1L)).thenReturn(Optional.of(room));
        var resp = service.findById(1L);
        assertThat(resp.roomNumber()).isEqualTo("R101");
        assertThat(resp.labName()).isEqualTo("Physics Lab");
    }

    @Test void findById_nullLab() {
        room.setLab(null);
        when(roomResourceRepo.findById(1L)).thenReturn(Optional.of(room));
        var resp = service.findById(1L);
        assertThat(resp.labId()).isNull();
        assertThat(resp.labName()).isNull();
    }

    @Test void findById_notFound() {
        when(roomResourceRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        RoomResourceRequest req = new RoomResourceRequest("R101", "Main", 1, "LAB", 40, 1L, "AVAILABLE");
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(roomResourceRepo.save(any())).thenReturn(room);
        assertThat(service.create(req).roomNumber()).isEqualTo("R101");
    }

    @Test void create_nullLabId() {
        RoomResourceRequest req = new RoomResourceRequest("R102", "Main", 2, "LECTURE", 60, null, "AVAILABLE");
        when(roomResourceRepo.save(any())).thenReturn(room);
        service.create(req);
        verify(roomResourceRepo).save(any());
    }

    @Test void create_nullStatus() {
        RoomResourceRequest req = new RoomResourceRequest("R103", "Main", 3, "WORKSHOP", 30, null, null);
        when(roomResourceRepo.save(any())).thenReturn(room);
        service.create(req);
        verify(roomResourceRepo).save(any());
    }

    @Test void create_labNotFound() {
        RoomResourceRequest req = new RoomResourceRequest("R104", "Main", 1, "LAB", 40, 99L, null);
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        RoomResourceRequest req = new RoomResourceRequest("R101-A", "Annex", 2, "LAB", 50, 1L, "OCCUPIED");
        when(roomResourceRepo.findById(1L)).thenReturn(Optional.of(room));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(roomResourceRepo.save(any())).thenReturn(room);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullLabId() {
        RoomResourceRequest req = new RoomResourceRequest("R101-A", "Annex", 2, "LECTURE", 50, null, "AVAILABLE");
        when(roomResourceRepo.findById(1L)).thenReturn(Optional.of(room));
        when(roomResourceRepo.save(any())).thenReturn(room);
        service.update(1L, req);
        assertThat(room.getLab()).isNull();
    }

    @Test void update_nullStatus() {
        RoomResourceRequest req = new RoomResourceRequest("R101-A", "Annex", 2, "LAB", 50, null, null);
        when(roomResourceRepo.findById(1L)).thenReturn(Optional.of(room));
        when(roomResourceRepo.save(any())).thenReturn(room);
        service.update(1L, req);
        assertThat(room.getStatus()).isEqualTo("AVAILABLE");
    }

    @Test void update_notFound() {
        RoomResourceRequest req = new RoomResourceRequest("R101", "Main", 1, "LAB", 40, null, null);
        when(roomResourceRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_labNotFound() {
        RoomResourceRequest req = new RoomResourceRequest("R101", "Main", 1, "LAB", 40, 99L, null);
        when(roomResourceRepo.findById(1L)).thenReturn(Optional.of(room));
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(roomResourceRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(roomResourceRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(roomResourceRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
