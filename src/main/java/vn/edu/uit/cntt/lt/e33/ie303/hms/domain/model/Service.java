package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.ServiceStatus;

public class Service {
    private Long id;
    private String name;
    private String unit;
    private double price;
    private String description;
    private ServiceStatus status;
    private OffsetDateTime createdAt;
    private Long createdBy;
    private OffsetDateTime updatedAt;
    private Long updatedBy;

    public Service() {
    }

    public Service(Long id, String name, String unit, double price, String description, ServiceStatus status,
            OffsetDateTime createdAt, Long createdBy, OffsetDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Service(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.name = rs.getString("name");
        this.unit = rs.getString("unit");
        this.price = rs.getDouble("price");
        this.description = rs.getString("description");
        this.status = ServiceStatus.valueOf(rs.getString("status"));
        this.createdAt = rs.getObject("created_at", OffsetDateTime.class);

        long createdByVal = rs.getLong("created_by");
        this.createdBy = rs.wasNull() ? null : createdByVal;

        this.updatedAt = rs.getObject("updated_at", OffsetDateTime.class);
        long updatedByVal = rs.getLong("updated_by");
        this.updatedBy = rs.wasNull() ? null : updatedByVal;
    }

    public Long getId() {
        return id;
    }

    public Service setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Service setName(String name) {
        this.name = name;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Service setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Service setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Service setDescription(String description) {
        this.description = description;
        return this;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public Service setStatus(ServiceStatus status) {
        this.status = status;
        return this;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Service setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Service setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Service setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Service setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public static String findAllQuery() {
        return "SELECT id, name, unit, price, description, status, created_at, created_by, updated_at, updated_by " +
                "FROM services ORDER BY id";
    }

    public static String findByIdQuery() {
        return "SELECT id, name, unit, price, description, status, created_at, created_by, updated_at, updated_by " +
                "FROM services WHERE id = ?";
    }

    public static String insertQuery() {
        return "INSERT INTO services (name, unit, price, description, status, created_by, updated_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public void setInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.name);
        ps.setString(2, this.unit);
        ps.setDouble(3, this.price);
        ps.setString(4, this.description);
        ps.setString(5, this.status.name());
        if (this.createdBy != null)
            ps.setLong(6, this.createdBy);
        else
            ps.setNull(6, java.sql.Types.BIGINT);
        if (this.updatedBy != null)
            ps.setLong(7, this.updatedBy);
        else
            ps.setNull(7, java.sql.Types.BIGINT);
    }

    public static String updateQuery() {
        return "UPDATE services " +
                "SET name = ?, unit = ?, price = ?, description = ?, status = ?, updated_at = NOW(), updated_by = ? " +
                "WHERE id = ?";
    }

    public void setUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.name);
        ps.setString(2, this.unit);
        ps.setDouble(3, this.price);
        ps.setString(4, this.description);
        ps.setString(5, this.status.name());
        if (this.updatedBy != null)
            ps.setLong(6, this.updatedBy);
        else
            ps.setNull(6, java.sql.Types.BIGINT);
        ps.setLong(7, this.id);
    }

    public static String deleteQuery() {
        return "DELETE FROM services WHERE id = ?";
    }
}
