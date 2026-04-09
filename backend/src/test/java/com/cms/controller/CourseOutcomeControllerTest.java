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
import com.cms.dto.CourseOutcomeResponse;
import com.cms.service.CourseOutcomeService;

@WebMvcTest(CourseOutcomeController.class)
@Import(SecurityConfig.class)
class CourseOutcomeControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private CourseOutcomeService service;

    private final CourseOutcomeResponse resp = new CourseOutcomeResponse(1L, 1L, "CO1", "Understand arrays", "APPLY", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/course-outcomes")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(service.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/course-outcomes")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(service.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/course-outcomes/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(service.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/course-outcomes").contentType(MediaType.APPLICATION_JSON)
            .content("{\"syllabusId\":1,\"code\":\"CO1\",\"description\":\"Understand arrays\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/course-outcomes/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"syllabusId\":1,\"code\":\"CO1\",\"description\":\"Understand arrays\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteCO() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/v1/course-outcomes/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/course-outcomes")).andExpect(status().isForbidden());
    }
}
