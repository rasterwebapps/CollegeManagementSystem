package com.cms.controller;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
import com.cms.dto.ExperimentOutcomeMappingResponse;
import com.cms.service.ExperimentOutcomeMappingService;

@WebMvcTest(ExperimentOutcomeMappingController.class)
@Import(SecurityConfig.class)
class ExperimentOutcomeMappingControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ExperimentOutcomeMappingService service;

    private final ExperimentOutcomeMappingResponse resp = new ExperimentOutcomeMappingResponse(1L, 1L, "Exp1", 1L, "CO1", Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/experiment-outcome-mappings")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/experiment-outcome-mappings")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/experiment-outcome-mappings/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/experiment-outcome-mappings").contentType(MediaType.APPLICATION_JSON)
            .content("{\"experimentId\":1,\"courseOutcomeId\":1}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteMapping() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/experiment-outcome-mappings/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/experiment-outcome-mappings")).andExpect(status().isForbidden());
    }
}
