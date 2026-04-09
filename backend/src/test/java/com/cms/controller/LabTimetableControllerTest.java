package com.cms.controller;

import java.time.Instant;
import java.time.LocalTime;
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
import com.cms.dto.LabTimetableResponse;
import com.cms.service.LabTimetableService;

@WebMvcTest(LabTimetableController.class)
@Import(SecurityConfig.class)
class LabTimetableControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private LabTimetableService service;

    private final LabTimetableResponse resp = new LabTimetableResponse(1L, 1L, "Lab A", 1L, "CS101", 1L, "Fall 2024", 1L, "John Doe", "MONDAY", LocalTime.of(9,0), LocalTime.of(11,0), "A", "SCHEDULED", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/lab-timetables")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "LAB_INCHARGE")
    void findAll_labIncharge() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/lab-timetables")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/lab-timetables/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/lab-timetables").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"courseId\":1,\"semesterId\":1,\"facultyId\":1,\"dayOfWeek\":\"MONDAY\",\"startTime\":\"09:00\",\"endTime\":\"11:00\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/lab-timetables/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"labId\":1,\"courseId\":1,\"semesterId\":1,\"facultyId\":1,\"dayOfWeek\":\"MONDAY\",\"startTime\":\"09:00\",\"endTime\":\"11:00\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteLabTimetable() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/lab-timetables/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/lab-timetables")).andExpect(status().isForbidden());
    }
}
