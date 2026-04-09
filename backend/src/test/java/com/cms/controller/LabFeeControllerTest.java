package com.cms.controller;

import java.math.BigDecimal;
import java.time.Instant;
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
import com.cms.dto.LabFeeResponse;
import com.cms.service.LabFeeService;

@WebMvcTest(LabFeeController.class)
@Import(SecurityConfig.class)
class LabFeeControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private LabFeeService service;

    private final LabFeeResponse resp = new LabFeeResponse(1L, 1L, "Physics Lab", 1L, "Physics 101", 1L, "Sem 1", new BigDecimal("5000"), "Lab fee", "ACTIVE", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/lab-fees")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void findAll_labIncharge() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/lab-fees")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/lab-fees/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/lab-fees").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"courseId\":1,\"semesterId\":1,\"amount\":5000}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/lab-fees/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"courseId\":1,\"semesterId\":1,\"amount\":6000}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteLf() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/lab-fees/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/lab-fees")).andExpect(status().isForbidden());
    }
}
