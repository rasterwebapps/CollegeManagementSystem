package com.cms.controller;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.config.SecurityConfig;
import com.cms.dto.DepartmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.service.DepartmentService;

@WebMvcTest(DepartmentController.class)
@Import(SecurityConfig.class)
class DepartmentControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private DepartmentService departmentService;

    private final DepartmentResponse response = new DepartmentResponse(1L, "CS", "CS001", Instant.now(), Instant.now());

    @Test
    @WithMockUser(roles = "ADMIN")
    void findAll_admin() throws Exception {
        when(departmentService.findAll()).thenReturn(List.of(response));
        mockMvc.perform(get("/api/v1/departments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("CS"));
    }

    @Test
    @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(departmentService.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/departments")).andExpect(status().isOk());
    }

    @Test
    void findAll_unauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/departments")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/departments")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById_found() throws Exception {
        when(departmentService.findById(1L)).thenReturn(response);
        mockMvc.perform(get("/api/v1/departments/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("CS"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById_notFound() throws Exception {
        when(departmentService.findById(99L)).thenThrow(new ResourceNotFoundException("Department", 99L));
        mockMvc.perform(get("/api/v1/departments/99")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_success() throws Exception {
        when(departmentService.create(any())).thenReturn(response);
        mockMvc.perform(post("/api/v1/departments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"CS\",\"code\":\"CS001\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("CS"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_invalidRequest() throws Exception {
        mockMvc.perform(post("/api/v1/departments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\",\"code\":\"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "FACULTY")
    void create_forbidden() throws Exception {
        mockMvc.perform(post("/api/v1/departments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"CS\",\"code\":\"CS001\"}"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_duplicate() throws Exception {
        when(departmentService.create(any())).thenThrow(new IllegalArgumentException("already exists"));
        mockMvc.perform(post("/api/v1/departments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"CS\",\"code\":\"CS001\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_success() throws Exception {
        when(departmentService.update(eq(1L), any())).thenReturn(response);
        mockMvc.perform(put("/api/v1/departments/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"CS Updated\",\"code\":\"CS002\"}"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_success() throws Exception {
        doNothing().when(departmentService).delete(1L);
        mockMvc.perform(delete("/api/v1/departments/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_notFound() throws Exception {
        doThrow(new ResourceNotFoundException("Department", 99L)).when(departmentService).delete(99L);
        mockMvc.perform(delete("/api/v1/departments/99"))
            .andExpect(status().isNotFound());
    }
}
