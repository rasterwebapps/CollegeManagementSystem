package com.cms.service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.cms.dto.EquipmentRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Equipment;
import com.cms.model.Lab;
import com.cms.repository.EquipmentRepository;
import com.cms.repository.LabRepository;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock private EquipmentRepository equipmentRepo;
    @Mock private LabRepository labRepo;
    @InjectMocks private EquipmentService service;

    private Equipment equipment;
    private Lab lab;

    @BeforeEach
    void setUp() {
        lab = new Lab(); lab.setId(1L); lab.setName("Physics Lab");
        equipment = new Equipment();
        equipment.setId(1L); equipment.setLab(lab); equipment.setName("Oscilloscope");
        equipment.setModelNumber("OSC-100"); equipment.setSerialNumber("SN001");
        equipment.setPurchaseDate(LocalDate.of(2023, 1, 1));
        equipment.setPurchaseCost(new BigDecimal("25000.00")); equipment.setStatus("OPERATIONAL");
    }

    @Test void findAll() {
        when(equipmentRepo.findAll()).thenReturn(List.of(equipment));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        assertThat(service.findById(1L).name()).isEqualTo("Oscilloscope");
    }

    @Test void findById_notFound() {
        when(equipmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        EquipmentRequest req = new EquipmentRequest(1L, "Oscilloscope", "OSC-100", "SN001", LocalDate.of(2023, 1, 1), new BigDecimal("25000"), null, "OPERATIONAL");
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(equipmentRepo.save(any())).thenReturn(equipment);
        assertThat(service.create(req).name()).isEqualTo("Oscilloscope");
    }

    @Test void create_nullStatus() {
        EquipmentRequest req = new EquipmentRequest(1L, "Oscilloscope", "OSC-100", "SN001", null, null, null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(equipmentRepo.save(any())).thenReturn(equipment);
        service.create(req);
        verify(equipmentRepo).save(any());
    }

    @Test void create_labNotFound() {
        EquipmentRequest req = new EquipmentRequest(99L, "Oscilloscope", "OSC-100", "SN001", null, null, null, null);
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        EquipmentRequest req = new EquipmentRequest(1L, "Updated", "OSC-200", "SN002", LocalDate.of(2023, 6, 1), new BigDecimal("30000"), LocalDate.of(2026, 6, 1), "OPERATIONAL");
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(equipmentRepo.save(any())).thenReturn(equipment);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus() {
        EquipmentRequest req = new EquipmentRequest(1L, "Updated", "OSC-200", "SN002", null, null, null, null);
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(equipmentRepo.save(any())).thenReturn(equipment);
        service.update(1L, req);
        assertThat(equipment.getStatus()).isEqualTo("OPERATIONAL");
    }

    @Test void update_notFound() {
        EquipmentRequest req = new EquipmentRequest(1L, "Updated", null, null, null, null, null, null);
        when(equipmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_labNotFound() {
        EquipmentRequest req = new EquipmentRequest(99L, "Updated", null, null, null, null, null, null);
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(equipmentRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(equipmentRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(equipmentRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
