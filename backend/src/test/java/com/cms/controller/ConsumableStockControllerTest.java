package com.cms.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.config.SecurityConfig;
import com.cms.dto.ConsumableStockResponse;
import com.cms.service.ConsumableStockService;

@WebMvcTest(ConsumableStockController.class)
@Import(SecurityConfig.class)
class ConsumableStockControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ConsumableStockService service;

    private final ConsumableStockResponse resp = new ConsumableStockResponse(1L, 1L, "Chemistry Lab", "Beakers", 100, "pcs", 10, new BigDecimal("50"), LocalDate.of(2024, 1, 1), "IN_STOCK", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/consumable-stocks")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "TECHNICIAN")
    void findAll_technician() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/consumable-stocks")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void create_labIncharge() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/consumable-stocks").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"itemName\":\"Beakers\",\"quantity\":100,\"unit\":\"pcs\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/consumable-stocks/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"itemName\":\"Flasks\",\"quantity\":200,\"unit\":\"pcs\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteCs() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/consumable-stocks/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/consumable-stocks")).andExpect(status().isForbidden());
    }
}
