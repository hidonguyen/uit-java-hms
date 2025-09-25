package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.time.Instant;

public record Room(Long id, String name, Long roomTypeId, String description, HousekeepingStatus housekeepingStatus, RoomStatus status, Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy) { }