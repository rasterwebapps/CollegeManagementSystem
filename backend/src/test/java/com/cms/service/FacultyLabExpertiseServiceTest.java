package com.cms.service;

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

import com.cms.dto.FacultyLabExpertiseRequest;
import com.cms.dto.FacultyLabExpertiseResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FacultyLabExpertise;
import com.cms.model.FacultyProfile;
import com.cms.model.LabType;
import com.cms.repository.FacultyLabExpertiseRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.LabTypeRepository;

@ExtendWith(MockitoExtension.class)
class FacultyLabExpertiseServiceTest {

    @Mock private FacultyLabExpertiseRepository expRepo;
    @Mock private FacultyProfileRepository fpRepo;
    @Mock private LabTypeRepository ltRepo;
    @InjectMocks private FacultyLabExpertiseService service;

    private FacultyLabExpertise expertise;
    private FacultyProfile faculty;
    private LabType labType;

    @BeforeEach
    void setUp() {
        faculty = new FacultyProfile();
        faculty.setId(1L);
        faculty.setFirstName("John");
        faculty.setLastName("Doe");

        labType = new LabType();
        labType.setId(1L);
        labType.setName("Computer Lab");

        expertise = new FacultyLabExpertise();
        expertise.setId(1L);
        expertise.setFaculty(faculty);
        expertise.setLabType(labType);
        expertise.setProficiencyLevel("EXPERT");
        expertise.setCertifiedDate(LocalDate.of(2023, 6, 1));
    }

    @Test void findAll() {
        when(expRepo.findAll()).thenReturn(List.of(expertise));
        List<FacultyLabExpertiseResponse> result = service.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).facultyName()).isEqualTo("John Doe");
    }

    @Test void findById_found() {
        when(expRepo.findById(1L)).thenReturn(Optional.of(expertise));
        assertThat(service.findById(1L).proficiencyLevel()).isEqualTo("EXPERT");
    }

    @Test void findById_notFound() {
        when(expRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        FacultyLabExpertiseRequest req = new FacultyLabExpertiseRequest(1L, 1L, "EXPERT", LocalDate.of(2023, 6, 1));
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(ltRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(expRepo.save(any())).thenReturn(expertise);
        assertThat(service.create(req).proficiencyLevel()).isEqualTo("EXPERT");
    }

    @Test void create_facultyNotFound() {
        FacultyLabExpertiseRequest req = new FacultyLabExpertiseRequest(99L, 1L, "EXPERT", null);
        when(fpRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_labTypeNotFound() {
        FacultyLabExpertiseRequest req = new FacultyLabExpertiseRequest(1L, 99L, "EXPERT", null);
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(ltRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        FacultyLabExpertiseRequest req = new FacultyLabExpertiseRequest(1L, 1L, "INTERMEDIATE", LocalDate.of(2024, 1, 1));
        when(expRepo.findById(1L)).thenReturn(Optional.of(expertise));
        when(fpRepo.findById(1L)).thenReturn(Optional.of(faculty));
        when(ltRepo.findById(1L)).thenReturn(Optional.of(labType));
        when(expRepo.save(any())).thenReturn(expertise);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_notFound() {
        FacultyLabExpertiseRequest req = new FacultyLabExpertiseRequest(1L, 1L, "INTERMEDIATE", null);
        when(expRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_facultyNotFound() {
        FacultyLabExpertiseRequest req = new FacultyLabExpertiseRequest(99L, 1L, "INTERMEDIATE", null);
        when(expRepo.findById(1L)).thenReturn(Optional.of(expertise));
        when(fpRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(expRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(expRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(expRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
