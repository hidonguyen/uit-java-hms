package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportBookingCountPoint {
  private LocalDate date;
  private Integer bookingCount;

  public ReportBookingCountPoint() {
  }

  public ReportBookingCountPoint(LocalDate date, Integer bookingCount) {
    this.date = date;
    this.bookingCount = bookingCount;
  }

  public ReportBookingCountPoint(ResultSet rs) throws SQLException {
    this.date = rs.getObject("date", LocalDate.class);
    this.bookingCount = rs.getInt("booking_count");
  }

  public LocalDate getDate() {
    return date;
  }

  public ReportBookingCountPoint setDate(LocalDate date) {
    this.date = date;
    return this;
  }

  public Integer getBookingCount() {
    return bookingCount;
  }

  public ReportBookingCountPoint setBookingCount(Integer bookingCount) {
    this.bookingCount = bookingCount;
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
                WHEN gran = 'WEEK'  THEN date_trunc('week', dfrom::timestamp)::date
                WHEN gran = 'MONTH' THEN date_trunc('month', dfrom::timestamp)::date
                WHEN gran = 'YEAR'  THEN date_trunc('year',  dfrom::timestamp)::date
                ELSE dfrom
              END,
              CASE
                WHEN gran = 'DAY'   THEN dto
                WHEN gran = 'WEEK'  THEN date_trunc('week', dto::timestamp)::date
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
        bookings_bucketed AS (
          SELECT
            date_trunc(p.gran,
              (b.checkin AT TIME ZONE 'Asia/Ho_Chi_Minh')
            )::date AS bucket_start
          FROM bookings b
          CROSS JOIN params p
          WHERE (b.checkin AT TIME ZONE 'Asia/Ho_Chi_Minh')::date BETWEEN p.dfrom AND p.dto
        )
        SELECT
          b.bucket_start AS date,
          COUNT(bb.bucket_start) AS booking_count
        FROM buckets b
        LEFT JOIN bookings_bucketed bb
          ON bb.bucket_start = b.bucket_start
        GROUP BY b.bucket_start
        ORDER BY b.bucket_start
        """;
  }

}
