package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingChargeType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.BookingDetail;

public class BookingDto {
    private Long id;
    private String bookingNo;
    private BookingChargeType chargeType;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private Long roomId;
    private String roomName;
    private Long roomTypeId;
    private String roomTypeName;
    private Long primaryGuestId;
    private String primaryGuestName;
    private String primaryGuestPhone;
    private String primaryGuestEmail;
    private int numAdults;
    private int numChildren;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
    private String notes;
    private Double totalRoomCharges;
    private Double totalServiceCharges;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    private ArrayList<BookingDetailDto> bookingDetails;

    public BookingDto() {}

    public Long getId() {
        return id;
    }

    public BookingDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public BookingDto setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
        return this;
    }

    public BookingChargeType getChargeType() {
        return chargeType;
    }

    public BookingDto setChargeType(BookingChargeType chargeType) {
        this.chargeType = chargeType;
        return this;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    public BookingDto setCheckin(LocalDateTime checkin) {
        this.checkin = checkin;
        return this;
    }

    public LocalDateTime getCheckout() {
        return checkout;
    }

    public BookingDto setCheckout(LocalDateTime checkout) {
        this.checkout = checkout;
        return this;
    }

    public Long getRoomId() {
        return roomId;
    }

    public BookingDto setRoomId(Long roomId) {
        this.roomId = roomId;
        return this;
    }

    public String getRoomName() {
        return roomName;
    }

    public BookingDto setRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public BookingDto setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
        return this;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public BookingDto setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
        return this;
    }

    public Long getPrimaryGuestId() {
        return primaryGuestId;
    }

    public BookingDto setPrimaryGuestId(Long primaryGuestId) {
        this.primaryGuestId = primaryGuestId;
        return this;
    }

    public String getPrimaryGuestName() {
        return primaryGuestName;
    }

    public BookingDto setPrimaryGuestName(String primaryGuestName) {
        this.primaryGuestName = primaryGuestName;
        return this;
    }

    public String getPrimaryGuestPhone() {
        return primaryGuestPhone;
    }

    public BookingDto setPrimaryGuestPhone(String primaryGuestPhone) {
        this.primaryGuestPhone = primaryGuestPhone;
        return this;
    }

    public String getPrimaryGuestEmail() {
        return primaryGuestEmail;
    }

    public BookingDto setPrimaryGuestEmail(String primaryGuestEmail) {
        this.primaryGuestEmail = primaryGuestEmail;
        return this;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public BookingDto setNumAdults(int numAdults) {
        this.numAdults = numAdults;
        return this;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public BookingDto setNumChildren(int numChildren) {
        this.numChildren = numChildren;
        return this;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public BookingDto setStatus(BookingStatus status) {
        this.status = status;
        return this;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public BookingDto setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public BookingDto setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public Double getTotalRoomCharges() {
        return totalRoomCharges;
    }

    public BookingDto setTotalRoomCharges(Double totalRoomCharges) {
        this.totalRoomCharges = totalRoomCharges;
        return this;
    }

    public Double getTotalServiceCharges() {
        return totalServiceCharges;
    }

    public BookingDto setTotalServiceCharges(Double totalServiceCharges) {
        this.totalServiceCharges = totalServiceCharges;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BookingDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public BookingDto setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BookingDto setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public BookingDto setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public ArrayList<BookingDetailDto> getBookingDetails() {
        if (bookingDetails == null) {
            bookingDetails = new ArrayList<>();
        }
        return bookingDetails;
    }

    public BookingDto setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = new ArrayList<>();
        if (bookingDetails != null) {
            for (BookingDetail detail : bookingDetails) {
                this.bookingDetails.add(new BookingDetailDto()
                        .setId(detail.getId())
                        .setBookingId(detail.getBookingId())
                        .setType(detail.getType())
                        .setServiceId(detail.getServiceId())
                        .setIssuedAt(detail.getIssuedAt())
                        .setDescription(detail.getDescription())
                        .setQuantity(detail.getQuantity())
                        .setUnitPrice(detail.getUnitPrice())
                        .setDiscountAmount(detail.getDiscountAmount())
                        .setAmount(detail.getAmount())
                        .setCreatedAt(detail.getCreatedAt())
                        .setCreatedBy(detail.getCreatedBy())
                        .setUpdatedAt(detail.getUpdatedAt())
                        .setUpdatedBy(detail.getUpdatedBy()));
            }
        }
        return this;
    }

    public static String findAllQuery() {
        return "SELECT id, booking_no, charge_type, checkin, checkout, room_id, room_type_id, primary_guest_id, num_adults, num_children, status, payment_status, notes, created_at, created_by, updated_at, updated_by FROM bookings";
    }

    public static String findTodayBookingsIncludeDetailsQuery() {
        return """
            SELECT
                b.id, b.booking_no, b.charge_type, b.checkin, b.checkout,
                b.room_id, r.name AS room_name, b.room_type_id, rt.name AS room_type_name,
                b.primary_guest_id, g.name AS primary_guest_name, g.phone AS primary_guest_phone,
                b.num_adults, b.num_children,
                rbd.total_amount AS total_room_charges,
				sbd.total_amount AS total_service_charges,
                b.status, b.payment_status,
                b.notes,
                b.created_at, b.created_by, b.updated_at, b.updated_by
            FROM bookings b
				JOIN (SELECT booking_id, SUM(amount) AS total_amount FROM booking_details rbd WHERE rbd.Type = 'Room' GROUP BY booking_id) rbd ON b.id = rbd.booking_id
				JOIN (SELECT booking_id, SUM(amount) AS total_amount FROM booking_details sbd WHERE sbd.Type != 'Room' GROUP BY booking_id) sbd ON b.id = sbd.booking_id
                JOIN guests g ON b.primary_guest_id = g.id
                JOIN rooms r ON b.room_id = r.id
                JOIN room_types rt ON b.room_type_id = rt.id
            WHERE DATE(b.checkin) <= CURRENT_DATE AND (b.checkout IS NULL OR DATE(b.checkout) >= CURRENT_DATE)
            ORDER BY b.checkin ASC
        """;
    }

    public static String findByIdQuery() {
        return "SELECT id, booking_no, charge_type, checkin, checkout, room_id, room_type_id, primary_guest_id, num_adults, num_children, status, payment_status, notes, created_at, created_by, updated_at, updated_by FROM bookings WHERE id = ?";
    }

    public static String insertQuery() {
        return "INSERT INTO bookings (booking_no, charge_type, checkin, checkout, room_id, room_type_id, primary_guest_id, num_adults, num_children, status, payment_status, notes, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public void setInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.bookingNo);
        ps.setString(2, this.chargeType.name());
        ps.setObject(3, this.checkin);
        ps.setObject(4, this.checkout);
        if (this.roomId != null) {
            ps.setLong(5, this.roomId);
        } else {
            ps.setNull(5, java.sql.Types.BIGINT);
        }
        if (this.roomTypeId != null) {
            ps.setLong(6, this.roomTypeId);
        } else {
            ps.setNull(6, java.sql.Types.BIGINT);
        }
        if (this.primaryGuestId != null) {
            ps.setLong(7, this.primaryGuestId);
        } else {
            ps.setNull(7, java.sql.Types.BIGINT);
        }
        ps.setInt(8, this.numAdults);
        ps.setInt(9, this.numChildren);
        ps.setString(10, this.status.name());
        ps.setString(11, this.paymentStatus.name());
        ps.setString(12, this.notes);
        ps.setObject(13, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(14, this.createdBy);
        } else {
            ps.setNull(14, java.sql.Types.BIGINT);
        }
        ps.setObject(15, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(16, this.updatedBy);
        } else {
            ps.setNull(16, java.sql.Types.BIGINT);
        }
    }

    public static String updateQuery() {
        return "UPDATE bookings SET booking_no = ?, charge_type = ?, checkin = ?, checkout = ?, room_id = ?, room_type_id = ?, primary_guest_id = ?, num_adults = ?, num_children = ?, status = ?, payment_status = ?, notes = ?, created_at = ?, created_by = ?, updated_at = ?, updated_by = ? WHERE id = ?";
    }

    public void setUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.bookingNo);
        ps.setString(2, this.chargeType.name());
        ps.setObject(3, this.checkin);
        ps.setObject(4, this.checkout);
        if (this.roomId != null) {
            ps.setLong(5, this.roomId);
        } else {
            ps.setNull(5, java.sql.Types.BIGINT);
        }
        if (this.roomTypeId != null) {
            ps.setLong(6, this.roomTypeId);
        } else {
            ps.setNull(6, java.sql.Types.BIGINT);
        }
        if (this.primaryGuestId != null) {
            ps.setLong(7, this.primaryGuestId);
        } else {
            ps.setNull(7, java.sql.Types.BIGINT);
        }
        ps.setInt(8, this.numAdults);
        ps.setInt(9, this.numChildren);
        ps.setString(10, this.status.name());
        ps.setString(11, this.paymentStatus.name());
        ps.setString(12, this.notes);
        ps.setObject(13, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(14, this.createdBy);
        } else {
            ps.setNull(14, java.sql.Types.BIGINT);
        }
        ps.setObject(15, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(16, this.updatedBy);
        } else {
            ps.setNull(16, java.sql.Types.BIGINT);
        }
        ps.setLong(17, this.id);
    }

    public static String deleteQuery() {
        return "DELETE FROM bookings WHERE id = ?";
    }
}