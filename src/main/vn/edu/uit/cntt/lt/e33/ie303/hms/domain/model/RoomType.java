package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.time.Instant;

public record RoomType(Long id, String code, String name, int baseOccupancy, int maxOccupancy, double baseRate, double hourRate, double extraAdultFee, double extraChildFee, String description, Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy) { }