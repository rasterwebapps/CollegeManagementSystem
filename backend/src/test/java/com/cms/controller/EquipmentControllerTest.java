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
import com.cms.dto.EquipmentResponse;
import com.cms.service.EquipmentService;

@WebMvcTest(EquipmentController.class)
@Import(SecurityConfig.class)
class EquipmentControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private EquipmentService service;

    private final EquipmentResponse resp = new EquipmentResponse(1L, 1L, "Physics Lab", "Oscilloscope", "OSC-100", "SN001", LocalDate.of(2023, 1, 1), new BigDecimal("25000"), LocalDate.of(2026, 1, 1), "OPERATIONAL", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/equipments")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "TECHNICIAN")
    void findAll_technician() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/equipments")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void findAll_labIncharge() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/equipments")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void create_labIncharge() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/equipments").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"name\":\"Oscilloscope\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/equipments/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"name\":\"Updated\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteEquipment() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/equipments/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/equipments")).andExpect(status().isForbidden());
    }
}
