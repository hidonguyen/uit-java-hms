package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.GuestGender;

public class Guest {
    private Long id;
    private String name;
    private GuestGender gender;
    private OffsetDateTime dateOfBirth;
    private String nationality;
    private String phone;
    private String email;
    private String address;
    private String description;
    private OffsetDateTime createdAt;
    private Long createdBy;
    private OffsetDateTime updatedAt;
    private Long updatedBy;

    public Guest() {}

    public Guest(Long id, String name, GuestGender gender, OffsetDateTime dateOfBirth, String nationality, String phone, String email, String address, String description, OffsetDateTime createdAt, Long createdBy, OffsetDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Guest(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.name = rs.getString("name");
        this.gender = GuestGender.valueOf(rs.getString("gender"));
        this.dateOfBirth = rs.getObject("date_of_birth", OffsetDateTime.class);
        this.nationality = rs.getString("nationality");
        this.phone = rs.getString("phone");
        this.email = rs.getString("email");
        this.address = rs.getString("address");
        this.description = rs.getString("description");
        this.createdAt = rs.getObject("created_at", OffsetDateTime.class);
        this.createdBy = rs.getLong("created_by");
        this.updatedAt = rs.getObject("updated_at", OffsetDateTime.class);
        this.updatedBy = rs.getLong("updated_by");
    }

    public Long getId() {
        return id;
    }

    public Guest setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Guest setName(String name) {
        this.name = name;
        return this;
    }

    public GuestGender getGender() {
        return gender;
    }

    public Guest setGender(GuestGender gender) {
        this.gender = gender;
        return this;
    }

    public OffsetDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public Guest setDateOfBirth(OffsetDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public Guest setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Guest setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Guest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Guest setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Guest setDescription(String description) {
        this.description = description;
        return this;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Guest setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Guest setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Guest setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Guest setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public static String findAllQuery() {
        return "SELECT id, name, gender, date_of_birth, nationality, phone, email, address, description, created_at, created_by, updated_at, updated_by FROM guests";
    }

    public static String findByIdQuery() {
        return "SELECT id, name, gender, date_of_birth, nationality, phone, email, address, description, created_at, created_by, updated_at, updated_by FROM guests WHERE id = ?";
    }

    public static String insertQuery() {
        return "INSERT INTO guests (name, gender, date_of_birth, nationality, phone, email, address, description, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public void setInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.name);
        ps.setString(2, this.gender.name());
        ps.setObject(3, this.dateOfBirth);
        ps.setString(4, this.nationality);
        ps.setString(5, this.phone);
        ps.setString(6, this.email);
        ps.setString(7, this.address);
        ps.setString(8, this.description);
        ps.setObject(9, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(10, this.createdBy);
        } else {
            ps.setNull(10, java.sql.Types.BIGINT);
        }
        ps.setObject(11, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(12, this.updatedBy);
        } else {
            ps.setNull(12, java.sql.Types.BIGINT);
        }
    }

    public static String updateQuery() {
        return "UPDATE guests SET name = ?, gender = ?, date_of_birth = ?, nationality = ?, phone = ?, email = ?, address = ?, description = ?, created_at = ?, created_by = ?, updated_at = ?, updated_by = ? WHERE id = ?";
    }

    public void setUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.name);
        ps.setString(2, this.gender.name());
        ps.setObject(3, this.dateOfBirth);
        ps.setString(4, this.nationality);
        ps.setString(5, this.phone);
        ps.setString(6, this.email);
        ps.setString(7, this.address);
        ps.setString(8, this.description);
        ps.setObject(9, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(10, this.createdBy);
        } else {
            ps.setNull(10, java.sql.Types.BIGINT);
        }
        ps.setObject(11, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(12, this.updatedBy);
        } else {
            ps.setNull(12, java.sql.Types.BIGINT);
        }
        ps.setLong(13, this.id);
    }

    public static String deleteQuery() {
        return "DELETE FROM guests WHERE id = ?";
    }
}