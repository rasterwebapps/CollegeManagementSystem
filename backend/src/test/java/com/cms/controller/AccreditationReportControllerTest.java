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
import com.cms.dto.AccreditationReportResponse;
import com.cms.service.AccreditationReportService;

@WebMvcTest(AccreditationReportController.class)
@Import(SecurityConfig.class)
class AccreditationReportControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private AccreditationReportService service;

    private final AccreditationReportResponse resp = new AccreditationReportResponse(1L, 1L, "Computer Science", "NBA", "2023-24", LocalDate.of(2024, 3, 1), new BigDecimal("8.50"), "DRAFT", "Annual report", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/accreditation-reports")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/accreditation-reports")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/accreditation-reports/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/accreditation-reports").contentType(MediaType.APPLICATION_JSON)
            .content("{\"departmentId\":1,\"reportType\":\"NBA\",\"academicYear\":\"2023-24\",\"generatedDate\":\"2024-03-01\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/accreditation-reports/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"departmentId\":1,\"reportType\":\"NAAC\",\"academicYear\":\"2024-25\",\"generatedDate\":\"2024-06-01\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteAccreditationReport() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/accreditation-reports/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/accreditation-reports")).andExpect(status().isForbidden());
    }
}
