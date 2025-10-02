package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory;

import java.time.OffsetDateTime;

public class BookingHistoryFilter {
    private OffsetDateTime fromDate;
    private OffsetDateTime toDate;
    private String chargeType;
    private String status;
    private String paymentStatus;
    private Long roomId;
    private Long roomTypeId;
    private Long primaryGuestId;
    private String keyword;

    public OffsetDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(OffsetDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public OffsetDateTime getToDate() {
        return toDate;
    }

    public void setToDate(OffsetDateTime toDate) {
        this.toDate = toDate;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Long getPrimaryGuestId() {
        return primaryGuestId;
    }

    public void setPrimaryGuestId(Long primaryGuestId) {
        this.primaryGuestId = primaryGuestId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
