package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingChargeType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentStatus;

public class Booking {
private Long id;
    private String bookingNo;
    private BookingChargeType chargeType;
    private OffsetDateTime checkin;
    private OffsetDateTime checkout;
    private Long roomId;
    private Long roomTypeId;
    private Long primaryGuestId;
    private int numAdults;
    private int numChildren;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
    private String notes;
    private OffsetDateTime createdAt;
    private Long createdBy;
    private OffsetDateTime updatedAt;
    private Long updatedBy;

    private ArrayList<BookingDetail> bookingDetails;

    public Booking() {}

    public Booking(Long id, String bookingNo, BookingChargeType chargeType, OffsetDateTime checkin, OffsetDateTime checkout, Long roomId, Long roomTypeId, Long primaryGuestId, int numAdults, int numChildren, BookingStatus status, PaymentStatus paymentStatus, String notes, OffsetDateTime createdAt, Long createdBy, OffsetDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.bookingNo = bookingNo;
        this.chargeType = chargeType;
        this.checkin = checkin;
        this.checkout = checkout;
        this.roomId = roomId;
        this.roomTypeId = roomTypeId;
        this.primaryGuestId = primaryGuestId;
        this.numAdults = numAdults;
        this.numChildren = numChildren;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.notes = notes;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;

        this.bookingDetails = new ArrayList<>();
    }

    public Booking(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.bookingNo = rs.getString("booking_no");
        this.chargeType = BookingChargeType.valueOf(rs.getString("charge_type"));
        this.checkin = rs.getObject("checkin", OffsetDateTime.class);
        this.checkout = rs.getObject("checkout", OffsetDateTime.class);
        this.roomId = rs.getObject("room_id") != null ? rs.getLong("room_id") : null;
        this.roomTypeId = rs.getObject("room_type_id") != null ? rs.getLong("room_type_id") : null;
        this.primaryGuestId = rs.getObject("primary_guest_id") != null ? rs.getLong("primary_guest_id") : null;
        this.numAdults = rs.getInt("num_adults");
        this.numChildren = rs.getInt("num_children");
        this.status = BookingStatus.valueOf(rs.getString("status"));
        this.paymentStatus = PaymentStatus.valueOf(rs.getString("payment_status"));
        this.notes = rs.getString("notes");
        this.createdAt = rs.getObject("created_at", OffsetDateTime.class);
        this.createdBy = rs.getObject("created_by") != null ? rs.getLong("created_by") : null;
        this.updatedAt = rs.getObject("updated_at", OffsetDateTime.class);
        this.updatedBy = rs.getObject("updated_by") != null ? rs.getLong("updated_by") : null;

        this.bookingDetails = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Booking setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public Booking setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
        return this;
    }

    public BookingChargeType getChargeType() {
        return chargeType;
    }

    public Booking setChargeType(BookingChargeType chargeType) {
        this.chargeType = chargeType;
        return this;
    }

    public OffsetDateTime getCheckin() {
        return checkin;
    }

    public Booking setCheckin(OffsetDateTime checkin) {
        this.checkin = checkin;
        return this;
    }

    public OffsetDateTime getCheckout() {
        return checkout;
    }

    public Booking setCheckout(OffsetDateTime checkout) {
        this.checkout = checkout;
        return this;
    }

    public Long getRoomId() {
        return roomId;
    }

    public Booking setRoomId(Long roomId) {
        this.roomId = roomId;
        return this;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public Booking setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
        return this;
    }

    public Long getPrimaryGuestId() {
        return primaryGuestId;
    }

    public Booking setPrimaryGuestId(Long primaryGuestId) {
        this.primaryGuestId = primaryGuestId;
        return this;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public Booking setNumAdults(int numAdults) {
        this.numAdults = numAdults;
        return this;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public Booking setNumChildren(int numChildren) {
        this.numChildren = numChildren;
        return this;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Booking setStatus(BookingStatus status) {
        this.status = status;
        return this;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Booking setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Booking setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Booking setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Booking setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Booking setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Booking setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public ArrayList<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public Booking setBookingDetails(ResultSet rs) {
        this.bookingDetails = new ArrayList<>();
        try {
            while (rs.next()) {
                this.bookingDetails.add(new BookingDetail(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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