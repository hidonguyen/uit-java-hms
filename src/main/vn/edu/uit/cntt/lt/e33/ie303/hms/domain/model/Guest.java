package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.time.Instant;

public record Guest(Long id, String name, GuestGender gender, Instant dateOfBirth, String nationality, String phone, String email, String address, String description, Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy) { }