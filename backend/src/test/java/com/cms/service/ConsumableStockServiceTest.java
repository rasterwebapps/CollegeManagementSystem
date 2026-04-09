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

import com.cms.dto.ConsumableStockRequest;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.ConsumableStock;
import com.cms.model.Lab;
import com.cms.repository.ConsumableStockRepository;
import com.cms.repository.LabRepository;

@ExtendWith(MockitoExtension.class)
class ConsumableStockServiceTest {

    @Mock private ConsumableStockRepository consumableStockRepo;
    @Mock private LabRepository labRepo;
    @InjectMocks private ConsumableStockService service;

    private ConsumableStock stock;
    private Lab lab;

    @BeforeEach
    void setUp() {
        lab = new Lab(); lab.setId(1L); lab.setName("Chemistry Lab");
        stock = new ConsumableStock();
        stock.setId(1L); stock.setLab(lab); stock.setItemName("Beakers");
        stock.setQuantity(100); stock.setUnit("pcs"); stock.setMinimumThreshold(10);
        stock.setUnitCost(new BigDecimal("50.00")); stock.setStatus("IN_STOCK");
    }

    @Test void findAll() {
        when(consumableStockRepo.findAll()).thenReturn(List.of(stock));
        assertThat(service.findAll()).hasSize(1);
    }

    @Test void findById_found() {
        when(consumableStockRepo.findById(1L)).thenReturn(Optional.of(stock));
        assertThat(service.findById(1L).itemName()).isEqualTo("Beakers");
    }

    @Test void findById_notFound() {
        when(consumableStockRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void create_success() {
        ConsumableStockRequest req = new ConsumableStockRequest(1L, "Beakers", 100, "pcs", 10, new BigDecimal("50"), LocalDate.of(2024, 1, 1), "IN_STOCK");
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(consumableStockRepo.save(any())).thenReturn(stock);
        assertThat(service.create(req).itemName()).isEqualTo("Beakers");
    }

    @Test void create_nullThresholdAndStatus() {
        ConsumableStockRequest req = new ConsumableStockRequest(1L, "Beakers", 100, "pcs", null, new BigDecimal("50"), null, null);
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(consumableStockRepo.save(any())).thenReturn(stock);
        service.create(req);
        verify(consumableStockRepo).save(any());
    }

    @Test void create_labNotFound() {
        ConsumableStockRequest req = new ConsumableStockRequest(99L, "Beakers", 100, "pcs", null, null, null, null);
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_success() {
        ConsumableStockRequest req = new ConsumableStockRequest(1L, "Flasks", 200, "pcs", 20, new BigDecimal("75"), LocalDate.of(2024, 6, 1), "IN_STOCK");
        when(consumableStockRepo.findById(1L)).thenReturn(Optional.of(stock));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(consumableStockRepo.save(any())).thenReturn(stock);
        assertThat(service.update(1L, req)).isNotNull();
    }

    @Test void update_nullThresholdAndStatus() {
        ConsumableStockRequest req = new ConsumableStockRequest(1L, "Flasks", 200, "pcs", null, null, null, null);
        when(consumableStockRepo.findById(1L)).thenReturn(Optional.of(stock));
        when(labRepo.findById(1L)).thenReturn(Optional.of(lab));
        when(consumableStockRepo.save(any())).thenReturn(stock);
        service.update(1L, req);
        assertThat(stock.getMinimumThreshold()).isEqualTo(10);
        assertThat(stock.getStatus()).isEqualTo("IN_STOCK");
    }

    @Test void update_notFound() {
        ConsumableStockRequest req = new ConsumableStockRequest(1L, "Flasks", 200, "pcs", null, null, null, null);
        when(consumableStockRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void update_labNotFound() {
        ConsumableStockRequest req = new ConsumableStockRequest(99L, "Flasks", 200, "pcs", null, null, null, null);
        when(consumableStockRepo.findById(1L)).thenReturn(Optional.of(stock));
        when(labRepo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(1L, req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test void delete_success() {
        when(consumableStockRepo.existsById(1L)).thenReturn(true);
        service.delete(1L);
        verify(consumableStockRepo).deleteById(1L);
    }

    @Test void delete_notFound() {
        when(consumableStockRepo.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(ResourceNotFoundException.class);
    }
}
