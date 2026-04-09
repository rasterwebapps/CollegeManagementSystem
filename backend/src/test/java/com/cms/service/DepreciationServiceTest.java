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

import com.cms.dto.DepreciationRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Asset;
import com.cms.model.Depreciation;
import com.cms.repository.AssetRepository;
import com.cms.repository.DepreciationRepository;

@ExtendWith(MockitoExtension.class)
class DepreciationServiceTest {

    @Mock private DepreciationRepository depreciationRepo;
    @Mock private AssetRepository assetRepo;
    @InjectMocks private DepreciationService service;

    private Depreciation depreciation;
    private Asset asset;

    @BeforeEach
    void setUp() {
        asset = new Asset(); asset.setId(1L); asset.setName("Lab Oscilloscope");
        depreciation = new Depreciation();
        depreciation.setId(1L); depreciation.setAsset(asset); depreciation.setFiscalYear("2024-25");
        depreciation.setOpeningValue(new BigDecimal("25000.00")); depreciation.setDepreciationAmount(new BigDecimal("2500.00"));
        depreciation.setClosingValue(new BigDecimal("22500.00")); depreciation.setCalculatedDate(LocalDate.of(2025, 3, 31));
    }

    @Test void findAll() {
        when(depreciationRepo.findAll()).thenReturn(List.of(depreciation));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(depreciationRepo.findById(1L)).thenReturn(Optional.of(depreciation));
        var resp = service.findById(1L);
        assertThat(resp.fiscalYear()).isEqualTo("2024-25");
        assertThat(resp.assetName()).isEqualTo("Lab Oscilloscope");
    }

    @Test void findById_notFound() {
        when(depreciationRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        DepreciationRequest req = new DepreciationRequest(1L, "2024-25", new BigDecimal("25000"), new BigDecimal("2500"), new BigDecimal("22500"), LocalDate.of(2025, 3, 31));
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(depreciationRepo.save(any())).thenReturn(depreciation);
        assertThat(service.create(req).fiscalYear()).isEqualTo("2024-25");
    }

    @Test void create_assetNotFound() {
        DepreciationRequest req = new DepreciationRequest(99L, "2024-25", new BigDecimal("25000"), new BigDecimal("2500"), new BigDecimal("22500"), LocalDate.of(2025, 3, 31));
        when(assetRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        DepreciationRequest req = new DepreciationRequest(1L, "2025-26", new BigDecimal("22500"), new BigDecimal("2500"), new BigDecimal("20000"), LocalDate.of(2026, 3, 31));
        when(depreciationRepo.findById(1L)).thenReturn(Optional.of(depreciation));
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(depreciationRepo.save(any())).thenReturn(depreciation);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_notFound() {
        DepreciationRequest req = new DepreciationRequest(1L, "2025-26", new BigDecimal("22500"), new BigDecimal("2500"), new BigDecimal("20000"), LocalDate.of(2026, 3, 31));
        when(depreciationRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_assetNotFound() {
        DepreciationRequest req = new DepreciationRequest(99L, "2025-26", new BigDecimal("22500"), new BigDecimal("2500"), new BigDecimal("20000"), LocalDate.of(2026, 3, 31));
        when(depreciationRepo.findById(1L)).thenReturn(Optional.of(depreciation));
        when(assetRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(depreciationRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(depreciationRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(depreciationRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
