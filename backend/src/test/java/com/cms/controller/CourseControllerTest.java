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
import com.cms.dto.CourseResponse;
import com.cms.service.CourseService;

@WebMvcTest(CourseController.class)
@Import(SecurityConfig.class)
class CourseControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private CourseService courseService;

    private final CourseResponse resp = new CourseResponse(1L, "DS", "CS201", 1L, "B.Tech CS", 4, "THEORY", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(courseService.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/courses")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(courseService.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/courses/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(courseService.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/courses").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"DS\",\"code\":\"CS201\",\"programId\":1,\"credits\":4,\"courseType\":\"THEORY\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(courseService.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/courses/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"DS\",\"code\":\"CS201\",\"programId\":1,\"credits\":4,\"courseType\":\"THEORY\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteCourse() throws Exception {
        doNothing().when(courseService).delete(1L);
        mockMvc.perform(delete("/api/v1/courses/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/courses")).andExpect(status().isForbidden());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void create_forbidden() throws Exception {
        mockMvc.perform(post("/api/v1/courses").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"DS\",\"code\":\"CS201\",\"programId\":1,\"credits\":4,\"courseType\":\"THEORY\"}"))
            .andExpect(status().isForbidden());
    }
}
