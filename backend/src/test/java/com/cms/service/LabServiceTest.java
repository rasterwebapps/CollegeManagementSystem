package com.cms.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cms.dto.LabRequest;
import com.cms.dto.LabResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.Lab;
import com.cms.model.LabType;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.LabTypeRepository;

@ExtendWith(MockitoExtension.class)
class LabServiceTest {

    @Mock private LabRepository labRepo;
    @Mock private LabTypeRepository labTypeRepo;
    @Mock private DepartmentRepository deptRepo;
    @InjectMocks private LabService service;

    private Lab lab;
    private LabType labType;
    private Department dept;

    @BeforeEach
    void setUp() {
        labType = new LabType();
        labType.setId(1L);
        labType.setName("Computer Lab");

        dept = new Department();
        dept.setId(1L);
        dept.setName("CS");

        lab = new Lab();
        lab.setId(1L);
        lab.setName("CS Lab 1");
        lab.setCode("CSL1");
        lab.setLabType(labType);
        lab.setDepartment(dept);
        lab.setLocation("Block A");
        lab.setCapacity(30);
        lab.setStatus("AVAILABLE");
    }

    @Test
    void findAll() {
        when(labRepo.findAll()).thenReturn(List.of(lab));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    void findById_found() {
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        LabResponse r = service.findById(1L);
        assertThat(r.labTypeName()).isEqualTo("Computer Lab");
        assertThat(r.departmentName()).isEqualTo("CS");
    }

    @Test
    void findById_notFound() {
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_success() {
        LabRequest req = new LabRequest("CS Lab 1", "CSL1", 1L, 1L, "Block A", 30, "AVAILABLE");
        when(labRepo.existsByCode("CSL1")).thenReturn(false);
        when(labTypeRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(labRepo.save(any())).thenReturn(lab);
        assertThat(service.create(req).name()).isEqualTo("CS Lab 1");
    }

    @Test
    void create_nullStatus_defaultsAvailable() {
        LabRequest req = new LabRequest("CS Lab 1", "CSL1", 1L, 1L, "Block A", 30, null);
        when(labRepo.existsByCode("CSL1")).thenReturn(false);
        when(labTypeRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(labRepo.save(any())).thenReturn(lab);
        service.create(req);
        verify(labRepo).save(any());
    }

    @Test
    void create_duplicateCode() {
        LabRequest req = new LabRequest("CS Lab 1", "CSL1", 1L, 1L, "Block A", 30, null);
        when(labRepo.existsByCode("CSL1")).thenReturn(true);
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(IllegalArgumentException.class);
        verify(labRepo, never()).save(any());
    }

    @Test
    void create_labTypeNotFound() {
        LabRequest req = new LabRequest("CS Lab 1", "CSL1", 99L, 1L, "Block A", 30, null);
        when(labRepo.existsByCode("CSL1")).thenReturn(false);
        when(labTypeRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_deptNotFound() {
        LabRequest req = new LabRequest("CS Lab 1", "CSL1", 1L, 99L, "Block A", 30, null);
        when(labRepo.existsByCode("CSL1")).thenReturn(false);
        when(labTypeRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(deptRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_success() {
        LabRequest req = new LabRequest("Updated", "CSL2", 1L, 1L, "Block B", 40, "MAINTENANCE");
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(labRepo.findByCode("CSL2")).thenReturn(Optional.empty());
        when(labTypeRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(labRepo.save(any())).thenReturn(lab);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test
    void update_nullStatus_keepsCurrent() {
        LabRequest req = new LabRequest("Updated", "CSL1", 1L, 1L, "Block B", 40, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(labRepo.findByCode("CSL1")).thenReturn(Optional.of(lab));
        when(labTypeRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(deptRepo.findById(1L)).thenReturn(Optional.of(dept));
        when(labRepo.save(any())).thenReturn(lab);
        service.update(1L, req);
        assertThat(lab.getStatus()).isEqualTo("AVAILABLE");
    }

    @Test
    void update_duplicateCode() {
        Lab other = new Lab();
        other.setId(2L);
        other.setCode("CSL2");

        LabRequest req = new LabRequest("Updated", "CSL2", 1L, 1L, "Block B", 40, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(labRepo.findByCode("CSL2")).thenReturn(Optional.of(other));
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void update_notFound() {
        LabRequest req = new LabRequest("Updated", "CSL2", 1L, 1L, "Block B", 40, null);
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_success() {
        when(labRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(labRepo).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(labRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
