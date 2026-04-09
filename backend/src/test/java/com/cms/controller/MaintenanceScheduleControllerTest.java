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
import com.cms.dto.MaintenanceScheduleResponse;
import com.cms.service.MaintenanceScheduleService;

@WebMvcTest(MaintenanceScheduleController.class)
@Import(SecurityConfig.class)
class MaintenanceScheduleControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private MaintenanceScheduleService service;

    private final MaintenanceScheduleResponse resp = new MaintenanceScheduleResponse(1L, 1L, "Oscilloscope", "PREVENTIVE", LocalDate.of(2024, 6, 1), null, "Tech A", new BigDecimal("5000"), "notes", "SCHEDULED", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/maintenance-schedules")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "TECHNICIAN")
    void findAll_technician() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/maintenance-schedules")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void create_labIncharge() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/maintenance-schedules").contentType(MediaType.APPLICATION_JSON)
            .content("{\"equipmentId\":1,\"maintenanceType\":\"PREVENTIVE\",\"scheduledDate\":\"2024-06-01\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/maintenance-schedules/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"equipmentId\":1,\"maintenanceType\":\"CORRECTIVE\",\"scheduledDate\":\"2024-07-01\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteMs() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/maintenance-schedules/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/maintenance-schedules")).andExpect(status().isForbidden());
    }
}
