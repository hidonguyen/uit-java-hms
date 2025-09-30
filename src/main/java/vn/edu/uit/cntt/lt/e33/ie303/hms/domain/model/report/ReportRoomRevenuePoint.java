package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportRoomRevenuePoint {
    private LocalDate date;
    private BigDecimal revenue;

    public ReportRoomRevenuePoint() {
    }

    public ReportRoomRevenuePoint(LocalDate date, BigDecimal revenue) {
        this.date = date;
        this.revenue = revenue;
    }

    public ReportRoomRevenuePoint(ResultSet rs) throws SQLException {
        this.date = rs.getObject("date", LocalDate.class);
        this.revenue = rs.getBigDecimal("revenue");
    }

    public LocalDate getDate() {
        return date;
    }

    public ReportRoomRevenuePoint setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public ReportRoomRevenuePoint setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
        return this;
    }

    public static String findQuery() {
        return """
                WITH d AS (
                    SELECT generate_series(?::date, ?::date, interval '1 day')::date AS dte
                )
                SELECT d.dte AS date, COALESCE(SUM(bd.amount),0) AS revenue
                FROM d
                LEFT JOIN booking_details bd
                  ON bd.type = 'Room'
                 AND (bd.issued_at AT TIME ZONE 'Asia/Ho_Chi_Minh')::date = d.dte
                GROUP BY d.dte
                ORDER BY d.dte
                """;
    }
}
