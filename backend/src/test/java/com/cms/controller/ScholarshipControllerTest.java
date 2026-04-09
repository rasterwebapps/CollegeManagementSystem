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
import com.cms.dto.ScholarshipResponse;
import com.cms.service.ScholarshipService;

@WebMvcTest(ScholarshipController.class)
@Import(SecurityConfig.class)
class ScholarshipControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ScholarshipService service;

    private final ScholarshipResponse resp = new ScholarshipResponse(1L, 1L, "John Doe", "Merit", new BigDecimal("10000"), "MERIT", LocalDate.of(2024, 1, 1), null, "ACTIVE", null, Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/scholarships")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/scholarships")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findById_student() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/scholarships/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/scholarships").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"name\":\"Merit\",\"amount\":10000,\"scholarshipType\":\"MERIT\",\"awardedDate\":\"2024-01-01\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/scholarships/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"name\":\"Merit\",\"amount\":15000,\"scholarshipType\":\"MERIT\",\"awardedDate\":\"2024-01-01\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteScholarship() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/scholarships/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "TECHNICIAN")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/scholarships")).andExpect(status().isForbidden());
    }
}
