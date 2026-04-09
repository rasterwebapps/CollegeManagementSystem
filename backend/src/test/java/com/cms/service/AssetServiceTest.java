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

import com.cms.dto.AssetRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Asset;
import com.cms.model.Equipment;
import com.cms.repository.AssetRepository;
import com.cms.repository.EquipmentRepository;

@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock private AssetRepository assetRepo;
    @Mock private EquipmentRepository equipmentRepo;
    @InjectMocks private AssetService service;

    private Asset asset;
    private Equipment equipment;

    @BeforeEach
    void setUp() {
        equipment = new Equipment(); equipment.setId(1L); equipment.setName("Oscilloscope");
        asset = new Asset();
        asset.setId(1L); asset.setEquipment(equipment); asset.setAssetCode("AST-001");
        asset.setName("Lab Oscilloscope"); asset.setCategory("Electronics");
        asset.setPurchaseDate(LocalDate.of(2023, 1, 1));
        asset.setPurchaseCost(new BigDecimal("25000.00")); asset.setCurrentValue(new BigDecimal("20000.00"));
        asset.setUsefulLifeYears(10); asset.setDepreciationMethod("STRAIGHT_LINE");
        asset.setLocation("Lab A"); asset.setStatus("ACTIVE");
    }

    @Test void findAll() {
        when(assetRepo.findAll()).thenReturn(List.of(asset));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        var resp = service.findById(1L);
        assertThat(resp.assetCode()).isEqualTo("AST-001");
        assertThat(resp.equipmentName()).isEqualTo("Oscilloscope");
    }

    @Test void findById_nullEquipment() {
        asset.setEquipment(null);
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        var resp = service.findById(1L);
        assertThat(resp.equipmentId()).isNull();
        assertThat(resp.equipmentName()).isNull();
    }

    @Test void findById_notFound() {
        when(assetRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        AssetRequest req = new AssetRequest(1L, "AST-001", "Lab Oscilloscope", "Electronics", LocalDate.of(2023, 1, 1), new BigDecimal("25000"), new BigDecimal("20000"), 10, "STRAIGHT_LINE", "Lab A", "ACTIVE");
        when(assetRepo.existsByAssetCode("AST-001")).thenReturn(false);
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(assetRepo.save(any())).thenReturn(asset);
        assertThat(service.create(req).assetCode()).isEqualTo("AST-001");
    }

    @Test void create_nullEquipmentId() {
        AssetRequest req = new AssetRequest(null, "AST-002", "Standalone", "Furniture", null, null, null, null, null, null, null);
        when(assetRepo.existsByAssetCode("AST-002")).thenReturn(false);
        when(assetRepo.save(any())).thenReturn(asset);
        service.create(req);
        verify(assetRepo).save(any());
    }

    @Test void create_nullDepreciationMethodAndStatus() {
        AssetRequest req = new AssetRequest(null, "AST-003", "Item", "Cat", null, null, null, null, null, null, null);
        when(assetRepo.existsByAssetCode("AST-003")).thenReturn(false);
        when(assetRepo.save(any())).thenReturn(asset);
        service.create(req);
        verify(assetRepo).save(any());
    }

    @Test void create_duplicateAssetCode() {
        AssetRequest req = new AssetRequest(null, "AST-001", "Dup", "Cat", null, null, null, null, null, null, null);
        when(assetRepo.existsByAssetCode("AST-001")).thenReturn(true);
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test void create_equipmentNotFound() {
        AssetRequest req = new AssetRequest(99L, "AST-004", "Item", "Cat", null, null, null, null, null, null, null);
        when(assetRepo.existsByAssetCode("AST-004")).thenReturn(false);
        when(equipmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        AssetRequest req = new AssetRequest(1L, "AST-001", "Updated", "Electronics", LocalDate.of(2023, 1, 1), new BigDecimal("25000"), new BigDecimal("18000"), 10, "DECLINING", "Lab B", "ACTIVE");
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(assetRepo.save(any())).thenReturn(asset);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_changeAssetCode() {
        AssetRequest req = new AssetRequest(null, "AST-NEW", "Updated", "Cat", null, null, null, null, null, null, null);
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(assetRepo.existsByAssetCode("AST-NEW")).thenReturn(false);
        when(assetRepo.save(any())).thenReturn(asset);
        service.update(1L, req);
        assertThat(asset.getEquipment()).isNull();
    }

    @Test void update_duplicateAssetCode() {
        AssetRequest req = new AssetRequest(null, "AST-EXISTING", "Updated", "Cat", null, null, null, null, null, null, null);
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(assetRepo.existsByAssetCode("AST-EXISTING")).thenReturn(true);
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test void update_sameAssetCode() {
        AssetRequest req = new AssetRequest(1L, "AST-001", "Updated", "Cat", null, null, null, null, "DECLINING", null, "INACTIVE");
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(equipmentRepo.findById(1L)).thenReturn(Optional.of(equipment));
        when(assetRepo.save(any())).thenReturn(asset);
        service.update(1L, req);
        verify(assetRepo).save(any());
    }

    @Test void update_nullDepreciationMethodAndStatus() {
        AssetRequest req = new AssetRequest(null, "AST-001", "Updated", "Cat", null, null, null, null, null, null, null);
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(assetRepo.save(any())).thenReturn(asset);
        service.update(1L, req);
        assertThat(asset.getDepreciationMethod()).isEqualTo("STRAIGHT_LINE");
        assertThat(asset.getStatus()).isEqualTo("ACTIVE");
    }

    @Test void update_notFound() {
        AssetRequest req = new AssetRequest(null, "AST-001", "Updated", "Cat", null, null, null, null, null, null, null);
        when(assetRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_equipmentNotFound() {
        AssetRequest req = new AssetRequest(99L, "AST-001", "Updated", "Cat", null, null, null, null, null, null, null);
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(equipmentRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(assetRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(assetRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(assetRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
