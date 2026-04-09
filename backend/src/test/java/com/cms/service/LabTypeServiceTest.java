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

import com.cms.dto.LabTypeRequest;
import com.cms.dto.LabTypeResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.LabType;
import com.cms.repository.LabTypeRepository;

@ExtendWith(MockitoExtension.class)
class LabTypeServiceTest {

    @Mock private LabTypeRepository repo;
    @InjectMocks private LabTypeService service;

    private LabType labType;

    @BeforeEach
    void setUp() {
        labType = new LabType();
        labType.setId(1L);
        labType.setName("Computer Lab");
        labType.setDescription("General purpose computing");
    }

    @Test
    void findAll() {
        when(repo.findAll()).thenReturn(List.of(labType));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    void findById_found() {
        when(repo.findById(1L)).thenReturn(Optional.of(labType));
        assertThat(service.findById(1L).name()).isEqualTo("Computer Lab");
    }

    @Test
    void findById_notFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void create_success() {
        LabTypeRequest req = new LabTypeRequest("Computer Lab", "Desc");
        when(repo.existsByName("Computer Lab")).thenReturn(false);
        when(repo.save(any())).thenReturn(labType);
        assertThat(service.create(req).name()).isEqualTo("Computer Lab");
    }

    @Test
    void create_duplicate() {
        LabTypeRequest req = new LabTypeRequest("Computer Lab", "Desc");
        when(repo.existsByName("Computer Lab")).thenReturn(true);
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(IllegalArgumentException.class);
        verify(repo, never()).save(any());
    }

    @Test
    void update_success() {
        LabTypeRequest req = new LabTypeRequest("Updated Lab", "New desc");
        when(repo.findById(1L)).thenReturn(Optional.of(labType));
        when(repo.findByName("Updated Lab")).thenReturn(Optional.empty());
        when(repo.save(any())).thenReturn(labType);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test
    void update_notFound() {
        LabTypeRequest req = new LabTypeRequest("Updated", "New desc");
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_duplicateName() {
        LabType other = new LabType();
        other.setId(2L);
        other.setName("Other Lab");

        LabTypeRequest req = new LabTypeRequest("Other Lab", "Desc");
        when(repo.findById(1L)).thenReturn(Optional.of(labType));
        when(repo.findByName("Other Lab")).thenReturn(Optional.of(other));
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void update_sameName() {
        LabTypeRequest req = new LabTypeRequest("Computer Lab", "Updated desc");
        when(repo.findById(1L)).thenReturn(Optional.of(labType));
        when(repo.findByName("Computer Lab")).thenReturn(Optional.of(labType));
        when(repo.save(any())).thenReturn(labType);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test
    void delete_success() {
        when(repo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
