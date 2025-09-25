package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.time.Instant;

public record Booking(Long id, String bookingNo, BookingChargeType chargeType, Instant checkin, Instant checkout, Long roomId, Long roomTypeId, Long primaryGuestId, int numAdults, int numChildren, BookingStatus status, PaymentStatus paymentStatus, String notes, Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy) { }