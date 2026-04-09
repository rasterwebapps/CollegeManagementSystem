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
import com.cms.dto.FacultyProfileResponse;
import com.cms.service.FacultyProfileService;

@WebMvcTest(FacultyProfileController.class)
@Import(SecurityConfig.class)
class FacultyProfileControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private FacultyProfileService service;

    private final FacultyProfileResponse resp = new FacultyProfileResponse(1L, "kc-001", "EMP01", "John", "Doe", "john@test.com", "1234567890", 1L, "CS", "Professor", LocalDate.of(2024,1,1), "ACTIVE", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/faculty-profiles")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/faculty-profiles")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/faculty-profiles/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/faculty-profiles").contentType(MediaType.APPLICATION_JSON)
            .content("{\"keycloakId\":\"kc-001\",\"employeeCode\":\"EMP01\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@test.com\",\"departmentId\":1,\"designation\":\"Professor\",\"joiningDate\":\"2024-01-01\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/faculty-profiles/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"keycloakId\":\"kc-001\",\"employeeCode\":\"EMP01\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@test.com\",\"departmentId\":1,\"designation\":\"Professor\",\"joiningDate\":\"2024-01-01\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteProfile() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/faculty-profiles/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/faculty-profiles")).andExpect(status().isForbidden());
    }
}
