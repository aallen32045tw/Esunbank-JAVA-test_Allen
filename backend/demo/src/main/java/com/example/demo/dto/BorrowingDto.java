package com.example.demo.dto;

import jakarta.validation.constraints.*;

public class BorrowingDto {
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotNull(message = "Inventory ID is required")
    @Positive(message = "Inventory ID must be a positive number")
    private Long inventoryId;

    // Default constructor
    public BorrowingDto() {}

    // Constructor with all fields
    public BorrowingDto(Long userId, Long inventoryId) {
        this.userId = userId;
        this.inventoryId = inventoryId;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }
}