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
import com.cms.dto.LabUtilizationKpiResponse;
import com.cms.service.LabUtilizationKpiService;

@WebMvcTest(LabUtilizationKpiController.class)
@Import(SecurityConfig.class)
class LabUtilizationKpiControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private LabUtilizationKpiService service;

    private final LabUtilizationKpiResponse resp = new LabUtilizationKpiResponse(1L, 1L, "Physics Lab", 1L, "Fall 2024", 100, 85, new BigDecimal("85.00"), "10:00-14:00", new BigDecimal("75.50"), LocalDate.of(2024, 6, 30), Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/lab-utilization-kpis")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void findAll_labIncharge() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/lab-utilization-kpis")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/lab-utilization-kpis/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/lab-utilization-kpis").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"semesterId\":1,\"totalSlots\":100,\"utilizedSlots\":85,\"measurementDate\":\"2024-06-30\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/lab-utilization-kpis/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"semesterId\":1,\"totalSlots\":120,\"utilizedSlots\":100,\"measurementDate\":\"2024-07-15\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteLabUtilizationKpi() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/lab-utilization-kpis/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/lab-utilization-kpis")).andExpect(status().isForbidden());
    }
}
