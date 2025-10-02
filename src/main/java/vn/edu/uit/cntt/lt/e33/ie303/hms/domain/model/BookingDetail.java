package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingDetailType;

public class BookingDetail {
    private Long id;
    private Long bookingId;
    private BookingDetailType type;
    private Long serviceId;
    private LocalDateTime issuedAt;
    private String description;
    private String unit;
    private int quantity;
    private double unitPrice;
    private double discountAmount;
    private double amount;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public BookingDetail() {}

    public BookingDetail(Long id, Long bookingId, BookingDetailType type, Long serviceId, LocalDateTime issuedAt, String description, String unit, int quantity, double unitPrice, double discountAmount, double amount, LocalDateTime createdAt, Long createdBy, LocalDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.bookingId = bookingId;
        this.type = type;
        this.serviceId = serviceId;
        this.issuedAt = issuedAt;
        this.description = description;
        this.unit = unit;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discountAmount = discountAmount;
        this.amount = amount;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public BookingDetail(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.bookingId = rs.getLong("booking_id");
        this.type = BookingDetailType.valueOf(rs.getString("type"));
        this.serviceId = rs.getLong("service_id");
        this.issuedAt = rs.getObject("issued_at", LocalDateTime.class);
        this.description = rs.getString("description");
        this.unit = rs.getString("unit");
        this.quantity = rs.getInt("quantity");
        this.unitPrice = rs.getDouble("unit_price");
        this.discountAmount = rs.getDouble("discount_amount");
        this.amount = rs.getDouble("amount");
        this.createdAt = rs.getObject("created_at", LocalDateTime.class);
        this.createdBy = rs.getLong("created_by");
        this.updatedAt = rs.getObject("updated_at", LocalDateTime.class);
        this.updatedBy = rs.getLong("updated_by");
    }

    public Long getId() {
        return id;
    }

    public BookingDetail setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public BookingDetail setBookingId(Long bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public BookingDetailType getType() {
        return type;
    }

    public BookingDetail setType(BookingDetailType type) {
        this.type = type;
        return this;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public BookingDetail setServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public BookingDetail setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BookingDetail setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public BookingDetail setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public BookingDetail setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public BookingDetail setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public BookingDetail setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public BookingDetail setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public BookingDetail setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public BookingDetail setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BookingDetail setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public BookingDetail setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public static String findAllQuery() {
        return "SELECT id, booking_id, type, service_id, issued_at, description, unit, quantity, unit_price, discount_amount, amount, created_at, created_by, updated_at, updated_by FROM booking_details";
    }

    public static String findAllByBookingIdQuery() {
        return "SELECT id, booking_id, type, service_id, issued_at, description, unit, quantity, unit_price, discount_amount, amount, created_at, created_by, updated_at, updated_by FROM booking_details WHERE booking_id = ?";
    }

    public static String findByIdQuery() {
        return "SELECT id, booking_id, type, service_id, issued_at, description, unit, quantity, unit_price, discount_amount, amount, created_at, created_by, updated_at, updated_by FROM booking_details WHERE id = ?";
    }

    public static String insertQuery() {
        return "INSERT INTO booking_details (booking_id, type, service_id, issued_at, description, unit, quantity, unit_price, discount_amount, amount, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public void setInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setLong(1, this.bookingId);
        ps.setString(2, this.type.name());
        ps.setObject(3, this.serviceId);
        ps.setObject(4, this.issuedAt);
        ps.setString(5, this.description);
        ps.setString(6, this.unit);
        ps.setInt(7, this.quantity);
        ps.setDouble(8, this.unitPrice);
        ps.setDouble(9, this.discountAmount);
        ps.setDouble(10, this.amount);
        ps.setObject(11, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(12, this.createdBy);
        } else {
            ps.setNull(12, java.sql.Types.BIGINT);
        }
        ps.setObject(13, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(14, this.updatedBy);
        } else {
            ps.setNull(14, java.sql.Types.BIGINT);
        }
    }

    public static String updateQuery() {
        return "UPDATE booking_details SET booking_id = ?, type = ?, service_id = ?, issued_at = ?, description = ?, unit = ?, quantity = ?, unit_price = ?, discount_amount = ?, amount = ?, created_at = ?, created_by = ?, updated_at = ?, updated_by = ? WHERE id = ?";
    }

    public void setUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setLong(1, this.bookingId);
        ps.setString(2, this.type.name());
        ps.setObject(3, this.serviceId);
        ps.setObject(4, this.issuedAt);
        ps.setString(5, this.description);
        ps.setString(6, this.unit);
        ps.setInt(7, this.quantity);
        ps.setDouble(8, this.unitPrice);
        ps.setDouble(9, this.discountAmount);
        ps.setDouble(10, this.amount);
        ps.setObject(11, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(12, this.createdBy);
        } else {
            ps.setNull(12, java.sql.Types.BIGINT);
        }
        ps.setObject(13, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(14, this.updatedBy);
        } else {
            ps.setNull(14, java.sql.Types.BIGINT);
        }
        ps.setLong(15, this.id);
    }

    public static String deleteQuery() {
        return "DELETE FROM booking_details WHERE id = ?";
    }
}