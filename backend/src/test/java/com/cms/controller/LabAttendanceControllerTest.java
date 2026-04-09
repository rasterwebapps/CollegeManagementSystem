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
import com.cms.dto.LabAttendanceResponse;
import com.cms.service.LabAttendanceService;

@WebMvcTest(LabAttendanceController.class)
@Import(SecurityConfig.class)
class LabAttendanceControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private LabAttendanceService service;

    private final LabAttendanceResponse resp = new LabAttendanceResponse(1L, 1L, "Alice Smith", 1L, "Exp 1", 1L, LocalDate.of(2024,9,15), "PRESENT", "On time", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/lab-attendances")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void findAll_labIncharge() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/lab-attendances")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/lab-attendances").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"experimentId\":1,\"labTimetableId\":1,\"attendanceDate\":\"2024-09-15\",\"status\":\"PRESENT\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/lab-attendances/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"studentId\":1,\"experimentId\":1,\"labTimetableId\":1,\"attendanceDate\":\"2024-09-15\",\"status\":\"ABSENT\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteAttendance() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/lab-attendances/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/lab-attendances")).andExpect(status().isForbidden());
    }
}
