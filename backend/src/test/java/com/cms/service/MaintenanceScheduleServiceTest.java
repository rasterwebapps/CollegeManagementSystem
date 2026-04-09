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

import com.cms.dto.MaintenanceScheduleRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Equipment;
import com.cms.model.MaintenanceSchedule;
import com.cms.repository.EquipmentRepository;
import com.cms.repository.MaintenanceScheduleRepository;

@ExtendWith(MockitoExtension.class)
class MaintenanceScheduleServiceTest {

    @Mock private MaintenanceScheduleRepository maintenanceRepo;
    @Mock private EquipmentRepository equipmentRepo;
    @InjectMocks private MaintenanceScheduleService service;

    private MaintenanceSchedule schedule;
    private Equipment equipment;

    @BeforeEach
    void setUp() {
        equipment = new Equipment(); equipment.setId(1L); equipment.setName("Oscilloscope");
        schedule = new MaintenanceSchedule();
        schedule.setId(1L); schedule.setEquipment(equipment);
        schedule.setMaintenanceType("PREVENTIVE"); schedule.setScheduledDate(LocalDate.of(2024, 6, 1));
        schedule.setCost(new BigDecimal("5000.00")); schedule.setStatus("SCHEDULED");
    }

    @Test void findAll() {
        when(maintenanceRepo.findAll()).thenReturn(List.of(schedule));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(maintenanceRepo.findById(1L)).thenReturn(Optional.of(schedule));
        assertThat(service.findById(1L).equipmentName()).isEqualTo("Oscilloscope");
    }

    @Test void findById_notFound() {
        when(maintenanceRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        MaintenanceScheduleRequest req = new MaintenanceScheduleRequest(1L, "PREVENTIVE", LocalDate.of(2024, 6, 1), null, "Tech A", new BigDecimal("5000"), "notes", "SCHEDULED");
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(maintenanceRepo.save(any())).thenReturn(schedule);
        assertThat(service.create(req).maintenanceType()).isEqualTo("PREVENTIVE");
    }

    @Test void create_nullStatus() {
        MaintenanceScheduleRequest req = new MaintenanceScheduleRequest(1L, "PREVENTIVE", LocalDate.of(2024, 6, 1), null, null, null, null, null);
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(maintenanceRepo.save(any())).thenReturn(schedule);
        service.create(req);
        verify(maintenanceRepo).save(any());
    }

    @Test void create_equipmentNotFound() {
        MaintenanceScheduleRequest req = new MaintenanceScheduleRequest(99L, "PREVENTIVE", LocalDate.of(2024, 6, 1), null, null, null, null, null);
        when(equipmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        MaintenanceScheduleRequest req = new MaintenanceScheduleRequest(1L, "CORRECTIVE", LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 2), "Tech B", new BigDecimal("8000"), "fixed", "COMPLETED");
        when(maintenanceRepo.findById(1L)).thenReturn(Optional.of(schedule));
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(maintenanceRepo.save(any())).thenReturn(schedule);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullStatus() {
        MaintenanceScheduleRequest req = new MaintenanceScheduleRequest(1L, "CORRECTIVE", LocalDate.of(2024, 7, 1), null, null, null, null, null);
        when(maintenanceRepo.findById(1L)).thenReturn(Optional.of(schedule));
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(maintenanceRepo.save(any())).thenReturn(schedule);
        service.update(1L, req);
        assertThat(schedule.getStatus()).isEqualTo("SCHEDULED");
    }

    @Test void update_notFound() {
        MaintenanceScheduleRequest req = new MaintenanceScheduleRequest(1L, "CORRECTIVE", LocalDate.of(2024, 7, 1), null, null, null, null, null);
        when(maintenanceRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_equipmentNotFound() {
        MaintenanceScheduleRequest req = new MaintenanceScheduleRequest(99L, "CORRECTIVE", LocalDate.of(2024, 7, 1), null, null, null, null, null);
        when(maintenanceRepo.findById(1L)).thenReturn(Optional.of(schedule));
        when(equipmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(maintenanceRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(maintenanceRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(maintenanceRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
