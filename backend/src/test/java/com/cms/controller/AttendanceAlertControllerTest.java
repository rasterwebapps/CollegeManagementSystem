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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.config.SecurityConfig;
import com.cms.dto.AttendanceAlertResponse;
import com.cms.service.AttendanceAlertService;

@WebMvcTest(AttendanceAlertController.class)
@Import(SecurityConfig.class)
class AttendanceAlertControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private AttendanceAlertService service;

    private final AttendanceAlertResponse resp = new AttendanceAlertResponse(1L, 1L, "Alice Smith", 1L, "CS101", "LOW_ATTENDANCE", "Below threshold", new BigDecimal("75.00"), new BigDecimal("60.00"), false, Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/attendance-alerts")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/attendance-alerts")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findById_student() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/attendance-alerts/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/attendance-alerts").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"courseId\":1,\"alertType\":\"LOW_ATTENDANCE\",\"message\":\"Below threshold\",\"thresholdPercentage\":75.00,\"currentPercentage\":60.00}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/attendance-alerts/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"courseId\":1,\"alertType\":\"CRITICAL\",\"message\":\"Very low\",\"thresholdPercentage\":75.00,\"currentPercentage\":40.00}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void resolve() throws Exception {
        when(service.resolve(1L)).thenReturn(resp);
        mockMvc.perform(patch("/api/v1/attendance-alerts/1/resolve")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteAlert() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/attendance-alerts/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/attendance-alerts")).andExpect(status().isForbidden());
    }
}
