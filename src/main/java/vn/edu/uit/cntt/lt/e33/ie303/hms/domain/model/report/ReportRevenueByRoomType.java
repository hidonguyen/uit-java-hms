package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportRevenueByRoomType {
    private Long roomTypeId;
    private String roomTypeCode;
    private String roomTypeName;
    private BigDecimal revenue;

    public ReportRevenueByRoomType() {
    }

    public ReportRevenueByRoomType(Long roomTypeId, String roomTypeCode, String roomTypeName, BigDecimal revenue) {
        this.roomTypeId = roomTypeId;
        this.roomTypeCode = roomTypeCode;
        this.roomTypeName = roomTypeName;
        this.revenue = revenue;
    }

    public ReportRevenueByRoomType(ResultSet rs) throws SQLException {
        this.roomTypeId = rs.getLong("room_type_id");
        this.roomTypeCode = rs.getString("room_type_code");
        this.roomTypeName = rs.getString("room_type_name");
        this.revenue = rs.getBigDecimal("revenue");
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public ReportRevenueByRoomType setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
        return this;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public ReportRevenueByRoomType setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
        return this;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public ReportRevenueByRoomType setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
        return this;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public ReportRevenueByRoomType setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
        return this;
    }

    public static String findQuery() {
        return """
                SELECT b.room_type_id AS room_type_id,
                       rt.code AS room_type_code,
                       rt.name AS room_type_name,
                       COALESCE(SUM(bd.amount),0) AS revenue
                FROM booking_details bd
                JOIN bookings b ON b.id = bd.booking_id
                JOIN room_types rt ON rt.id = b.room_type_id
                WHERE bd.type = 'Room'
                  AND (bd.issued_at AT TIME ZONE 'Asia/Ho_Chi_Minh')::date BETWEEN ?::date AND ?::date
                  AND (COALESCE(?, false) OR b.room_type_id = ANY(?))
                GROUP BY b.room_type_id, rt.code, rt.name
                ORDER BY revenue DESC
                """;
    }
}
