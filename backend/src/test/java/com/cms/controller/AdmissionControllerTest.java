package com.cms.controller;

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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.config.SecurityConfig;
import com.cms.dto.AdmissionResponse;
import com.cms.service.AdmissionService;

@WebMvcTest(AdmissionController.class)
@Import(SecurityConfig.class)
class AdmissionControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private AdmissionService service;

    private final AdmissionResponse resp = new AdmissionResponse(1L, 1L, "Alice Smith", "APP001", 1L, "B.Tech", LocalDate.of(2024,8,1), "REGULAR", "PENDING", "Good student", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/admissions")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/admissions")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/admissions/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/admissions").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"applicationNumber\":\"APP001\",\"programId\":1,\"admissionDate\":\"2024-08-01\",\"admissionType\":\"REGULAR\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/admissions/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"applicationNumber\":\"APP001\",\"programId\":1,\"admissionDate\":\"2024-08-01\",\"admissionType\":\"REGULAR\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteAdmission() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/admissions/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/admissions")).andExpect(status().isForbidden());
    }
}
