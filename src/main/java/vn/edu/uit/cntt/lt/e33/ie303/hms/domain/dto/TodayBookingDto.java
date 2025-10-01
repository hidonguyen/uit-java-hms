package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingChargeType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentStatus;

public class TodayBookingDto {
    private Long id;
    private String bookingNo;
    private BookingChargeType chargeType;
    private LocalDateTime checkin;
    private Long roomId;
    private String roomName;
    private Long roomTypeId;
    private String roomTypeName;
    private Long primaryGuestId;
    private String primaryGuestName;
    private String primaryGuestPhone;
    private int numAdults;
    private int numChildren;
    private Double totalRoomCharges;
    private Double totalServiceCharges;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
    private String notes;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public TodayBookingDto() {}

    public TodayBookingDto(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.bookingNo = rs.getString("booking_no");
        this.chargeType = BookingChargeType.valueOf(rs.getString("charge_type"));
        this.checkin = rs.getObject("checkin", LocalDateTime.class);
        this.roomId = rs.getObject("room_id") != null ? rs.getLong("room_id") : null;
        this.roomName = rs.getString("room_name");
        this.roomTypeId = rs.getLong("room_type_id");
        this.roomTypeName = rs.getString("room_type_name");
        this.primaryGuestId = rs.getLong("primary_guest_id");
        this.primaryGuestName = rs.getString("primary_guest_name");
        this.primaryGuestPhone = rs.getString("primary_guest_phone");
        this.numAdults = rs.getInt("num_adults");
        this.numChildren = rs.getInt("num_children");
        this.totalRoomCharges = rs.getDouble("total_room_charges");
        this.totalServiceCharges = rs.getDouble("total_service_charges");
        this.status = BookingStatus.valueOf(rs.getString("status"));
        this.paymentStatus = PaymentStatus.valueOf(rs.getString("payment_status"));
        this.notes = rs.getString("notes");
        this.createdAt = rs.getObject("created_at", LocalDateTime.class);
        this.createdBy = rs.getLong("created_by");
        this.updatedAt = rs.getObject("updated_at", LocalDateTime.class);
        this.updatedBy = rs.getLong("updated_by");
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public BookingChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(BookingChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDateTime checkin) {
        this.checkin = checkin;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Long getPrimaryGuestId() {
        return primaryGuestId;
    }

    public void setPrimaryGuestId(Long primaryGuestId) {
        this.primaryGuestId = primaryGuestId;
    }

    public String getPrimaryGuestName() {
        return primaryGuestName;
    }

    public void setPrimaryGuestName(String primaryGuestName) {
        this.primaryGuestName = primaryGuestName;
    }

    public String getPrimaryGuestPhone() {
        return primaryGuestPhone;
    }

    public void setPrimaryGuestPhone(String primaryGuestPhone) {
        this.primaryGuestPhone = primaryGuestPhone;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public Double getTotalRoomCharges() {
        return totalRoomCharges;
    }

    public void setTotalRoomCharges(Double totalRoomCharges) {
        this.totalRoomCharges = totalRoomCharges;
    }

    public Double getTotalServiceCharges() {
        return totalServiceCharges;
    }

    public void setTotalServiceCharges(Double totalServiceCharges) {
        this.totalServiceCharges = totalServiceCharges;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}