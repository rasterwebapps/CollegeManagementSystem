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
import com.cms.dto.ProgramResponse;
import com.cms.service.ProgramService;

@WebMvcTest(ProgramController.class)
@Import(SecurityConfig.class)
class ProgramControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ProgramService programService;

    private final ProgramResponse resp = new ProgramResponse(1L, "B.Tech CS", "BTCS", 1L, "CS", 4, "B.Tech", Instant.now(), Instant.now());

    @Test @WithMockUser(roles = "ADMIN")
    void findAll() throws Exception {
        when(programService.findAll()).thenReturn(List.of(resp));
        mockMvc.perform(get("/api/v1/programs")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("B.Tech CS"));
    }

    @Test @WithMockUser(roles = "FACULTY")
    void findAll_faculty() throws Exception {
        when(programService.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/v1/programs")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "STUDENT")
    void findAll_forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/programs")).andExpect(status().isForbidden());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(programService.findById(1L)).thenReturn(resp);
        mockMvc.perform(get("/api/v1/programs/1")).andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(programService.create(any())).thenReturn(resp);
        mockMvc.perform(post("/api/v1/programs").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"B.Tech CS\",\"code\":\"BTCS\",\"departmentId\":1,\"durationYears\":4,\"degreeType\":\"B.Tech\"}"))
            .andExpect(status().isCreated());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void create_invalid() throws Exception {
        mockMvc.perform(post("/api/v1/programs").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\",\"code\":\"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        when(programService.update(eq(1L), any())).thenReturn(resp);
        mockMvc.perform(put("/api/v1/programs/1").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"B.Tech CS\",\"code\":\"BTCS\",\"departmentId\":1,\"durationYears\":4,\"degreeType\":\"B.Tech\"}"))
            .andExpect(status().isOk());
    }

    @Test @WithMockUser(roles = "ADMIN")
    void deleteProgram() throws Exception {
        doNothing().when(programService).delete(1L);
        mockMvc.perform(delete("/api/v1/programs/1")).andExpect(status().isNoContent());
    }

    @Test @WithMockUser(roles = "FACULTY")
    void create_forbidden() throws Exception {
        mockMvc.perform(post("/api/v1/programs").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"B.Tech CS\",\"code\":\"BTCS\",\"departmentId\":1,\"durationYears\":4,\"degreeType\":\"B.Tech\"}"))
            .andExpect(status().isForbidden());
    }
}
