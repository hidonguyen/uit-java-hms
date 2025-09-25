package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.time.Instant;

public record BookingDetail(Long id, Long bookingId, BookingDetailType type, Long serviceId, Instant issuedAt, String description, int quantity, double unitPrice, double discountAmount, double amount, Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy) { }