package com.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoomResourceRequest(
    @NotBlank(message = "Room number is required") @Size(max = 50) String roomNumber,
    @NotBlank(message = "Building is required") @Size(max = 100) String building,
    Integer floor,
    @NotBlank(message = "Room type is required") @Size(max = 50) String roomType,
    Integer capacity,
    Long labId,
    String status
) {}
