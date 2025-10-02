package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class BookingHistory {
    private Long id;
    private String bookingNo;
    private String chargeType;
    private OffsetDateTime checkin;
    private OffsetDateTime checkout;
    private String roomName;
    private String roomTypeName;
    private String primaryGuestName;
    private int numAdults;
    private int numChildren;
    private String status;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal balance;

    public BookingHistory() {
    }

    public BookingHistory(java.sql.ResultSet rs) throws java.sql.SQLException {
        this.id = rs.getLong("id");
        this.bookingNo = rs.getString("booking_no");
        this.chargeType = rs.getString("charge_type");
        this.checkin = rs.getObject("checkin", OffsetDateTime.class);
        this.checkout = rs.getObject("checkout", OffsetDateTime.class);
        this.roomName = rs.getString("room_name");
        this.roomTypeName = rs.getString("room_type_name");
        this.primaryGuestName = rs.getString("primary_guest_name");
        this.numAdults = rs.getInt("num_adults");
        this.numChildren = rs.getInt("num_children");
        this.status = rs.getString("status");
        this.paymentStatus = rs.getString("payment_status");
        this.totalAmount = rs.getBigDecimal("total_amount");
        this.paidAmount = rs.getBigDecimal("paid_amount");
        this.balance = rs.getBigDecimal("balance");
    }

    public static String baseQuery() {
        return """
                SELECT b.id, b.booking_no, b.charge_type, b.checkin, b.checkout,
                       r.name AS room_name, rt.name AS room_type_name, g.name AS primary_guest_name,
                       b.num_adults, b.num_children, b.status, b.payment_status,
                       COALESCE(bd.total_amount,0) AS total_amount,
                       COALESCE(pm.paid_amount,0)  AS paid_amount,
                       COALESCE(bd.total_amount,0) - COALESCE(pm.paid_amount,0) AS balance
                FROM bookings b
                JOIN rooms r ON r.id = b.room_id
                JOIN room_types rt ON rt.id = b.room_type_id
                LEFT JOIN guests g ON g.id = b.primary_guest_id
                LEFT JOIN (
                  SELECT booking_id, SUM(amount) AS total_amount
                  FROM booking_details
                  GROUP BY booking_id
                ) bd ON bd.booking_id = b.id
                LEFT JOIN (
                  SELECT booking_id, SUM(amount) AS paid_amount
                  FROM payments
                  GROUP BY booking_id
                ) pm ON pm.booking_id = b.id
                WHERE 1=1
                """;
    }

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

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public OffsetDateTime getCheckin() {
        return checkin;
    }

    public void setCheckin(OffsetDateTime checkin) {
        this.checkin = checkin;
    }

    public OffsetDateTime getCheckout() {
        return checkout;
    }

    public void setCheckout(OffsetDateTime checkout) {
        this.checkout = checkout;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getPrimaryGuestName() {
        return primaryGuestName;
    }

    public void setPrimaryGuestName(String primaryGuestName) {
        this.primaryGuestName = primaryGuestName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BookingHistory{" +
                "id=" + id +
                ", bookingNo='" + bookingNo + '\'' +
                ", chargeType='" + chargeType + '\'' +
                ", checkin=" + checkin +
                ", checkout=" + checkout +
                ", roomName='" + roomName + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", primaryGuestName='" + primaryGuestName + '\'' +
                ", numAdults=" + numAdults +
                ", numChildren=" + numChildren +
                ", status='" + status + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", paidAmount=" + paidAmount +
                ", balance=" + balance +
                '}';
    }
}
