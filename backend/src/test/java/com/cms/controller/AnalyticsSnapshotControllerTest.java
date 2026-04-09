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
import com.cms.dto.AnalyticsSnapshotResponse;
import com.cms.service.AnalyticsSnapshotService;

@WebMvcTest(AnalyticsSnapshotController.class)
@Import(SecurityConfig.class)
class AnalyticsSnapshotControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private AnalyticsSnapshotService service;

    private final AnalyticsSnapshotResponse resp = new AnalyticsSnapshotResponse(1L, 1L, "Computer Science", 1L, "B.Tech CS", 1L, "Fall 2024", "ENROLLMENT", "Total Students", new BigDecimal("250"), LocalDate.of(2024, 6, 30), "Enrollment metrics", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/analytics-snapshots")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/analytics-snapshots")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/analytics-snapshots/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/analytics-snapshots").contentType(MediaType.APPLICATION_JSON)
            .content("{\"departmentId\":1,\"semesterId\":1,\"snapshotType\":\"ENROLLMENT\",\"metricName\":\"Total Students\",\"metricValue\":250,\"snapshotDate\":\"2024-06-30\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/analytics-snapshots/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"departmentId\":1,\"semesterId\":1,\"snapshotType\":\"PERFORMANCE\",\"metricName\":\"Avg GPA\",\"metricValue\":3.50,\"snapshotDate\":\"2024-07-01\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteAnalyticsSnapshot() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/analytics-snapshots/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/analytics-snapshots")).andExpect(status().isForbidden());
    }
}
