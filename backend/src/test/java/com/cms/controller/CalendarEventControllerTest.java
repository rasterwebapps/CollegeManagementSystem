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
import com.cms.dto.CalendarEventResponse;
import com.cms.service.CalendarEventService;

@WebMvcTest(CalendarEventController.class)
@Import(SecurityConfig.class)
class CalendarEventControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private CalendarEventService service;

    private final CalendarEventResponse resp = new CalendarEventResponse(1L, "Exam", "Desc", LocalDate.of(2024,12,1), "EXAM", 1L, "2024-25", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/calendar-events")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_student() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/calendar-events")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/calendar-events/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/calendar-events").contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Exam\",\"eventDate\":\"2024-12-01\",\"eventType\":\"EXAM\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/calendar-events/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Exam\",\"eventDate\":\"2024-12-01\",\"eventType\":\"EXAM\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteEvent() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/calendar-events/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void create_forbidden() throws Exception {
        mockMvc.perform(post("/api/v1/calendar-events").contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Exam\",\"eventDate\":\"2024-12-01\",\"eventType\":\"EXAM\"}"))
            .andExpect(status().isForbidden());
    }
}
