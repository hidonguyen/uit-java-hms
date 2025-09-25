package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.time.Instant;

public record User(Long id, String username, String role, String passwordHash, String passwordSalt, UserStatus status, Instant lastLoginAt, Instant createdAt, Long createdBy, Instant updatedAt, Long updatedBy) { }