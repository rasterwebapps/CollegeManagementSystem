package com.cms.exception;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleResourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Department", 99L);
        ResponseEntity<ErrorResponse> resp = handler.handleResourceNotFound(ex);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().status()).isEqualTo(404);
        assertThat(resp.getBody().message()).contains("Department");
    }

    @Test
    void handleIllegalArgument() {
        IllegalArgumentException ex = new IllegalArgumentException("Duplicate code");
        ResponseEntity<ErrorResponse> resp = handler.handleIllegalArgument(ex);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(resp.getBody().message()).isEqualTo("Duplicate code");
    }

    @Test
    void handleGenericException() {
        Exception ex = new RuntimeException("Unexpected");
        ResponseEntity<ErrorResponse> resp = handler.handleGeneric(ex);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(resp.getBody().message()).isEqualTo("An unexpected error occurred");
    }

    @Test
    void handleAccessDenied() {
        org.springframework.security.access.AccessDeniedException ex =
            new org.springframework.security.access.AccessDeniedException("Access denied");
        ResponseEntity<ErrorResponse> resp = handler.handleAccessDenied(ex);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(resp.getBody().status()).isEqualTo(403);
    }

    @Test
    void errorResponse_record() {
        Instant now = Instant.now();
        ErrorResponse error = new ErrorResponse(404, "not found", now);
        assertThat(error.status()).isEqualTo(404);
        assertThat(error.message()).isEqualTo("not found");
        assertThat(error.timestamp()).isEqualTo(now);
    }

    @Test
    void resourceNotFoundException_simpleMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");
        assertThat(ex.getMessage()).isEqualTo("Not found");
    }
}
