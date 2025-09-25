package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.time.Instant;

public record Service(Long id, String name, String category, String unit, double price, String description, ServiceStatus status, Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy) { }