package com.cms.controller;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.cms.config.SecurityConfig;

@WebMvcTest(ApiController.class)
@Import(SecurityConfig.class)
class ApiControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void currentUser_withRoles() throws Exception {
        mockMvc.perform(get("/api/v1/me")
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                    .jwt(jwt -> jwt
                        .subject("user-123")
                        .claim("preferred_username", "testuser")
                        .claim("email", "test@example.com")
                        .claim("realm_access", Map.of("roles", List.of("ROLE_ADMIN"))))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sub").value("user-123"))
            .andExpect(jsonPath("$.name").value("testuser"))
            .andExpect(jsonPath("$.email").value("test@example.com"))
            .andExpect(jsonPath("$.roles[0]").value("ROLE_ADMIN"));
    }

    @Test
    void currentUser_withoutRealmAccess() throws Exception {
        mockMvc.perform(get("/api/v1/me")
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                    .jwt(jwt -> jwt
                        .subject("user-456")
                        .claim("preferred_username", "testuser2")
                        .claim("email", "test2@example.com"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sub").value("user-456"))
            .andExpect(jsonPath("$.roles").isEmpty());
    }

    @Test
    void currentUser_unauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/me"))
            .andExpect(status().isUnauthorized());
    }
}
