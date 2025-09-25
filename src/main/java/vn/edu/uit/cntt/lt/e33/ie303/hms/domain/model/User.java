package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserRole;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.UserStatus;

public class User {
    private Long id;
    private String username;
    private UserRole role;
    private String passwordHash;
    private UserStatus status;
    private OffsetDateTime lastLoginAt;
    private OffsetDateTime createdAt;
    private Long createdBy;
    private OffsetDateTime updatedAt;
    private Long updatedBy;

    public User() {}

    public User(Long id, String username, UserRole role, String passwordHash, UserStatus status, OffsetDateTime lastLoginAt, OffsetDateTime createdAt, Long createdBy, OffsetDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.passwordHash = passwordHash;
        this.status = status;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public User(ResultSet rs) {
        try {
            this.id = rs.getLong("id");
            this.username = rs.getString("username");
            this.role = UserRole.valueOf(rs.getString("role"));
            this.passwordHash = rs.getString("password_hash");
            this.status = UserStatus.valueOf(rs.getString("status"));
            this.lastLoginAt = rs.getObject("last_login_at", OffsetDateTime.class);
            this.createdAt = rs.getObject("created_at", OffsetDateTime.class);
            this.createdBy = rs.getLong("created_by");
            this.updatedAt = rs.getObject("updated_at", OffsetDateTime.class);
            this.updatedBy = rs.getLong("updated_by");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public User setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public UserStatus getStatus() {
        return status;
    }

    public User setStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    public OffsetDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public User setLastLoginAt(OffsetDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public User setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public User setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public User setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public static String findAllQuery() {
        return "SELECT id, username, role, password_hash, status, last_login_at, created_at, created_by, updated_at, updated_by FROM users";
    }

    public static String findByIdQuery() {
        return "SELECT id, username, role, password_hash, status, last_login_at, created_at, created_by, updated_at, updated_by FROM users WHERE id = ?";
    }

    public static String findByUsernameQuery() {
        return "SELECT id, username, role, password_hash, status, last_login_at, created_at, created_by, updated_at, updated_by FROM users WHERE username = ?";
    }

    public static String insertQuery() {
        return "INSERT INTO users (username, role, password_hash, status, last_login_at, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public void setInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.username);
        ps.setString(2, this.role.name());
        ps.setString(3, this.passwordHash);
        ps.setString(4, this.status.name());
        ps.setObject(5, this.lastLoginAt);
        ps.setObject(6, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(7, this.createdBy);
        } else {
            ps.setNull(7, java.sql.Types.BIGINT);
        }
        ps.setObject(8, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(9, this.updatedBy);
        } else {
            ps.setNull(9, java.sql.Types.BIGINT);
        }
    }

    public static String updateQuery() {
        return "UPDATE users SET username = ?, role = ?, password_hash = ?, status = ?, last_login_at = ?, created_at = ?, created_by = ?, updated_at = ?, updated_by = ? WHERE id = ?";
    }

    public void setUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.username);
        ps.setString(2, this.role.name());
        ps.setString(3, this.passwordHash);
        ps.setString(4, this.status.name());
        ps.setObject(5, this.lastLoginAt);
        ps.setObject(6, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(7, this.createdBy);
        } else {
            ps.setNull(7, java.sql.Types.BIGINT);
        }
        ps.setObject(8, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(9, this.updatedBy);
        } else {
            ps.setNull(9, java.sql.Types.BIGINT);
        }
        ps.setLong(10, this.id);
    }

    public static String deleteQuery() {
        return "DELETE FROM users WHERE id = ?";
    }
}