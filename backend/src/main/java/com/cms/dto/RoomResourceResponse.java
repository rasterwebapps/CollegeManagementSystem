package com.cms.dto;

import java.time.Instant;

public record RoomResourceResponse(
    Long id, String roomNumber, String building, Integer floor,
    String roomType, Integer capacity, Long labId, String labName,
    String status, Instant createdAt, Instant updatedAt
) {}
