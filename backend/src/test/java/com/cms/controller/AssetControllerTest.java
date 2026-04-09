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
import com.cms.dto.AssetResponse;
import com.cms.service.AssetService;

@WebMvcTest(AssetController.class)
@Import(SecurityConfig.class)
class AssetControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private AssetService service;

    private final AssetResponse resp = new AssetResponse(1L, 1L, "Oscilloscope", "AST-001", "Lab Oscilloscope", "Electronics", LocalDate.of(2023, 1, 1), new BigDecimal("25000"), new BigDecimal("20000"), 10, "STRAIGHT_LINE", "Lab A", "ACTIVE", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/assets")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void findAll_labIncharge() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/assets")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/assets/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/assets").contentType(MediaType.APPLICATION_JSON)
            .content("{\"assetCode\":\"AST-001\",\"name\":\"Lab Oscilloscope\",\"category\":\"Electronics\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/assets/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"assetCode\":\"AST-001\",\"name\":\"Updated\",\"category\":\"Electronics\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteAsset() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/assets/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/assets")).andExpect(status().isForbidden());
    }
}
