package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.time.Instant;

public record Payment(Long id, Long bookingId, Instant paidAt, PaymentMethod paymentMethod, String referenceNo, double amount, String payerName, String notes, Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy) { }