package com.cms.controller;

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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.config.SecurityConfig;
import com.cms.dto.LabResponse;
import com.cms.service.LabService;

@WebMvcTest(LabController.class)
@Import(SecurityConfig.class)
class LabControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private LabService service;

    private final LabResponse resp = new LabResponse(1L, "Physics Lab 1", "PL1", 1L, "Physics Lab", 1L, "Physics", "Room 101", 30, "ACTIVE", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/labs")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void findAll_labIncharge() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/labs")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/labs/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/labs").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Physics Lab 1\",\"code\":\"PL1\",\"location\":\"Room 101\",\"capacity\":30,\"labTypeId\":1,\"departmentId\":1}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void create_labIncharge() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/labs").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Physics Lab 1\",\"code\":\"PL1\",\"location\":\"Room 101\",\"capacity\":30,\"labTypeId\":1,\"departmentId\":1}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/labs/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Physics Lab 1\",\"code\":\"PL1\",\"location\":\"Room 101\",\"capacity\":30,\"labTypeId\":1,\"departmentId\":1}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteLab() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/labs/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/labs")).andExpect(status().isForbidden());
    }
}
