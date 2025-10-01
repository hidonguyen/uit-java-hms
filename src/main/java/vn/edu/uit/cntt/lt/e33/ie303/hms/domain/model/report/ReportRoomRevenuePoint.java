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
                WITH params AS (
                  SELECT
                    ?::date AS dfrom,
                    ?::date AS dto,
                    COALESCE(NULLIF(?::text, ''), 'DAY') AS gran
                ),
                buckets AS (
                  SELECT
                    generate_series(
                      CASE
                        WHEN gran = 'DAY'   THEN dfrom
                        WHEN gran = 'WEEK'  THEN date_trunc('week',  dfrom::timestamp)::date
                        WHEN gran = 'MONTH' THEN date_trunc('month', dfrom::timestamp)::date
                        WHEN gran = 'YEAR'  THEN date_trunc('year',  dfrom::timestamp)::date
                        ELSE dfrom
                      END,
                      CASE
                        WHEN gran = 'DAY'   THEN dto
                        WHEN gran = 'WEEK'  THEN date_trunc('week',  dto::timestamp)::date
                        WHEN gran = 'MONTH' THEN date_trunc('month', dto::timestamp)::date
                        WHEN gran = 'YEAR'  THEN date_trunc('year',  dto::timestamp)::date
                        ELSE dto
                      END,
                      CASE
                        WHEN gran = 'DAY'   THEN interval '1 day'
                        WHEN gran = 'WEEK'  THEN interval '1 week'
                        WHEN gran = 'MONTH' THEN interval '1 month'
                        WHEN gran = 'YEAR'  THEN interval '1 year'
                        ELSE interval '1 day'
                      END
                    )::date AS bucket_start
                  FROM params
                ),
                room_revenue AS (
                  SELECT
                    date_trunc(p.gran, (bd.issued_at AT TIME ZONE 'Asia/Ho_Chi_Minh'))::date AS bucket_start,
                    bd.amount
                  FROM booking_details bd
                  CROSS JOIN params p
                  WHERE bd.type = 'Room'
                    AND (bd.issued_at AT TIME ZONE 'Asia/Ho_Chi_Minh')::date BETWEEN p.dfrom AND p.dto
                )
                SELECT
                  b.bucket_start AS date,
                  COALESCE(SUM(r.amount), 0)::numeric(12,2) AS revenue
                FROM buckets b
                LEFT JOIN room_revenue r
                  ON r.bucket_start = b.bucket_start
                GROUP BY b.bucket_start
                ORDER BY b.bucket_start
                """;
    }
}
