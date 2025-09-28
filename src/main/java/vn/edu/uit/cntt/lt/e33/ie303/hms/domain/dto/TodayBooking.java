package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingChargeType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentStatus;

public class TodayBooking {
    private Long id;
    private String bookingNo;
    private BookingChargeType chargeType;
    private OffsetDateTime checkin;
    private OffsetDateTime checkout;
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
    private OffsetDateTime createdAt;
    private Long createdBy;
    private OffsetDateTime updatedAt;
    private Long updatedBy;

    public TodayBooking() {}

    public TodayBooking(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.bookingNo = rs.getString("booking_no");
        this.chargeType = BookingChargeType.valueOf(rs.getString("charge_type"));
        this.checkin = rs.getObject("checkin", OffsetDateTime.class);
        this.checkout = rs.getObject("checkout", OffsetDateTime.class);
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
        this.createdAt = rs.getObject("created_at", OffsetDateTime.class);
        this.createdBy = rs.getLong("created_by");
        this.updatedAt = rs.getObject("updated_at", OffsetDateTime.class);
        this.updatedBy = rs.getLong("updated_by");
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public BookingChargeType getChargeType() {
        return chargeType;
    }

    public OffsetDateTime getCheckin() {
        return checkin;
    }

    public OffsetDateTime getCheckout() {
        return checkout;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public Long getPrimaryGuestId() {
        return primaryGuestId;
    }

    public String getPrimaryGuestName() {
        return primaryGuestName;
    }

    public String getPrimaryGuestPhone() {
        return primaryGuestPhone;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public Double getTotalRoomCharges() {
        return totalRoomCharges;
    }

    public Double getTotalServiceCharges() {
        return totalServiceCharges;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public void setChargeType(BookingChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public void setCheckin(OffsetDateTime checkin) {
        this.checkin = checkin;
    }

    public void setCheckout(OffsetDateTime checkout) {
        this.checkout = checkout;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public void setPrimaryGuestId(Long primaryGuestId) {
        this.primaryGuestId = primaryGuestId;
    }

    public void setPrimaryGuestName(String primaryGuestName) {
        this.primaryGuestName = primaryGuestName;
    }

    public void setPrimaryGuestPhone(String primaryGuestPhone) {
        this.primaryGuestPhone = primaryGuestPhone;
    }

    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }

    public void setTotalRoomCharges(Double totalRoomCharges) {
        this.totalRoomCharges = totalRoomCharges;
    }

    public void setTotalServiceCharges(Double totalServiceCharges) {
        this.totalServiceCharges = totalServiceCharges;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}