package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentMethod;

public class Payment {
    private Long id;
    private Long bookingId;
    private LocalDateTime paidAt;
    private PaymentMethod paymentMethod;
    private String referenceNo;
    private double amount;
    private String payerName;
    private String notes;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public Payment() {}

    public Payment(Long id, Long bookingId, LocalDateTime paidAt, PaymentMethod paymentMethod, String referenceNo, double amount, String payerName, String notes, LocalDateTime createdAt, Long createdBy, LocalDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.bookingId = bookingId;
        this.paidAt = paidAt;
        this.paymentMethod = paymentMethod;
        this.referenceNo = referenceNo;
        this.amount = amount;
        this.payerName = payerName;
        this.notes = notes;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Payment(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.bookingId = rs.getLong("booking_id");
        this.paidAt = rs.getObject("paid_at", LocalDateTime.class);
        this.paymentMethod = PaymentMethod.valueOf(rs.getString("payment_method"));
        this.referenceNo = rs.getString("reference_no");
        this.amount = rs.getDouble("amount");
        this.payerName = rs.getString("payer_name");
        this.notes = rs.getString("notes");
        this.createdAt = rs.getObject("created_at", LocalDateTime.class);
        this.createdBy = rs.getLong("created_by");
        this.updatedAt = rs.getObject("updated_at", LocalDateTime.class);
        this.updatedBy = rs.getLong("updated_by");
    }

    public Long getId() {
        return id;
    }

    public Payment setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Payment setBookingId(Long bookingId) {
        this.bookingId = bookingId;
        return this;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public Payment setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
        return this;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Payment setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public Payment setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Payment setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getPayerName() {
        return payerName;
    }

    public Payment setPayerName(String payerName) {
        this.payerName = payerName;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Payment setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public Payment setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Payment setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Payment setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Payment setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public static String findAllQuery() {
        return "SELECT id, booking_id, paid_at, payment_method, reference_no, amount, payer_name, notes, created_at, created_by, updated_at, updated_by FROM payments";
    }

    public static String findByIdQuery() {
        return "SELECT id, booking_id, paid_at, payment_method, reference_no, amount, payer_name, notes, created_at, created_by, updated_at, updated_by FROM payments WHERE id = ?";
    }

    public static String insertQuery() {
        return "INSERT INTO payments (booking_id, paid_at, payment_method, reference_no, amount, payer_name, notes, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public void setInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setLong(1, this.bookingId);
        ps.setObject(2, this.paidAt);
        ps.setString(3, this.paymentMethod.name());
        ps.setString(4, this.referenceNo);
        ps.setDouble(5, this.amount);
        ps.setString(6, this.payerName);
        ps.setString(7, this.notes);
        ps.setObject(8, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(9, this.createdBy);
        } else {
            ps.setNull(9, java.sql.Types.BIGINT);
        }
        ps.setObject(10, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(11, this.updatedBy);
        } else {
            ps.setNull(11, java.sql.Types.BIGINT);
        }
    }

    public static String updateQuery() {
        return "UPDATE payments SET booking_id = ?, paid_at = ?, payment_method = ?, reference_no = ?, amount = ?, payer_name = ?, notes = ?, created_at = ?, created_by = ?, updated_at = ?, updated_by = ? WHERE id = ?";
    }

    public void setUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setLong(1, this.bookingId);
        ps.setObject(2, this.paidAt);
        ps.setString(3, this.paymentMethod.name());
        ps.setString(4, this.referenceNo);
        ps.setDouble(5, this.amount);
        ps.setString(6, this.payerName);
        ps.setString(7, this.notes);
        ps.setObject(8, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(9, this.createdBy);
        } else {
            ps.setNull(9, java.sql.Types.BIGINT);
        }
        ps.setObject(10, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(11, this.updatedBy);
        } else {
            ps.setNull(11, java.sql.Types.BIGINT);
        }
        ps.setLong(12, this.id);
    }

    public static String deleteQuery() {
        return "DELETE FROM payments WHERE id = ?";
    }
}