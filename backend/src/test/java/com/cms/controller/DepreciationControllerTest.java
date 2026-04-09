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
import com.cms.dto.DepreciationResponse;
import com.cms.service.DepreciationService;

@WebMvcTest(DepreciationController.class)
@Import(SecurityConfig.class)
class DepreciationControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private DepreciationService service;

    private final DepreciationResponse resp = new DepreciationResponse(1L, 1L, "Lab Oscilloscope", "2024-25", new BigDecimal("25000"), new BigDecimal("2500"), new BigDecimal("22500"), LocalDate.of(2025, 3, 31), Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/depreciations")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void findAll_labIncharge() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/depreciations")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/depreciations/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/depreciations").contentType(MediaType.APPLICATION_JSON)
            .content("{\"assetId\":1,\"fiscalYear\":\"2024-25\",\"openingValue\":25000,\"depreciationAmount\":2500,\"closingValue\":22500,\"calculatedDate\":\"2025-03-31\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/depreciations/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"assetId\":1,\"fiscalYear\":\"2025-26\",\"openingValue\":22500,\"depreciationAmount\":2500,\"closingValue\":20000,\"calculatedDate\":\"2026-03-31\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteDepreciation() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/depreciations/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/depreciations")).andExpect(status().isForbidden());
    }
}
