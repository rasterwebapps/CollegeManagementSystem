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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.config.SecurityConfig;
import com.cms.dto.AcademicYearResponse;
import com.cms.service.AcademicYearService;

@WebMvcTest(AcademicYearController.class)
@Import(SecurityConfig.class)
class AcademicYearControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private AcademicYearService service;

    private final AcademicYearResponse resp = new AcademicYearResponse(1L, "2024-25", LocalDate.of(2024,8,1), LocalDate.of(2025,5,31), true, Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/academic-years")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/academic-years/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/academic-years").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"2024-25\",\"startDate\":\"2024-08-01\",\"endDate\":\"2025-05-31\",\"active\":true}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/academic-years/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"2024-25\",\"startDate\":\"2024-08-01\",\"endDate\":\"2025-05-31\",\"active\":true}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteAy() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/academic-years/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/academic-years")).andExpect(status().isForbidden());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void create_forbidden() throws Exception {
        mockMvc.perform(post("/api/v1/academic-years").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"2024-25\",\"startDate\":\"2024-08-01\",\"endDate\":\"2025-05-31\"}"))
            .andExpect(status().isForbidden());
    }
}
