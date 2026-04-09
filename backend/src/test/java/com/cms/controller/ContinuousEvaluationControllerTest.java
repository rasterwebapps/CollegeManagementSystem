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
import com.cms.dto.ContinuousEvaluationResponse;
import com.cms.service.ContinuousEvaluationService;

@WebMvcTest(ContinuousEvaluationController.class)
@Import(SecurityConfig.class)
class ContinuousEvaluationControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ContinuousEvaluationService service;

    private final ContinuousEvaluationResponse resp = new ContinuousEvaluationResponse(1L, 1L, "John Doe", 1L, "Physics", 1L, "Ohm's Law", 1L, "Fall 2024", "LAB_WORK", new BigDecimal("85"), new BigDecimal("100"), LocalDate.of(2024, 3, 15), "Good work", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/continuous-evaluations")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/continuous-evaluations")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/continuous-evaluations/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/continuous-evaluations").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"courseId\":1,\"semesterId\":1,\"evaluationType\":\"LAB_WORK\",\"marksObtained\":85,\"maxMarks\":100,\"evaluationDate\":\"2024-03-15\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/continuous-evaluations/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"courseId\":1,\"semesterId\":1,\"evaluationType\":\"LAB_WORK\",\"marksObtained\":90,\"maxMarks\":100,\"evaluationDate\":\"2024-04-01\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteContinuousEvaluation() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/continuous-evaluations/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/continuous-evaluations")).andExpect(status().isForbidden());
    }
}
