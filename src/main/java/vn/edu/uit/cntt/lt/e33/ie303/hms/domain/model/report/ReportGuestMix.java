package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportGuestMix {
    private LocalDate date;
    private Integer newGuests;
    private Integer returningGuests;

    public ReportGuestMix() {
    }

    public ReportGuestMix(LocalDate date, Integer newGuests, Integer returningGuests) {
        this.date = date;
        this.newGuests = newGuests;
        this.returningGuests = returningGuests;
    }

    public ReportGuestMix(ResultSet rs) throws SQLException {
        this.date = rs.getObject("date", LocalDate.class);
        this.newGuests = rs.getInt("new_guests");
        this.returningGuests = rs.getInt("returning_guests");
    }

    public LocalDate getDate() {
        return date;
    }

    public ReportGuestMix setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getNewGuests() {
        return newGuests;
    }

    public ReportGuestMix setNewGuests(Integer newGuests) {
        this.newGuests = newGuests;
        return this;
    }

    public Integer getReturningGuests() {
        return returningGuests;
    }

    public ReportGuestMix setReturningGuests(Integer returningGuests) {
        this.returningGuests = returningGuests;
        return this;
    }

    public static String findQuery() {
        return """
                WITH d AS (
                  SELECT generate_series(?::date, ?::date, interval '1 day')::date AS dte
                ),
                gb AS (
                  SELECT b.id, b.primary_guest_id, (b.checkin AT TIME ZONE 'Asia/Ho_Chi_Minh')::date AS chk
                  FROM bookings b
                  WHERE b.primary_guest_id IS NOT NULL
                ),
                firsts AS (
                  SELECT primary_guest_id, MIN(chk) AS first_chk
                  FROM gb GROUP BY primary_guest_id
                )
                SELECT d.dte AS date,
                       COUNT(DISTINCT CASE WHEN f.first_chk = d.dte THEN gb.primary_guest_id END) AS new_guests,
                       COUNT(DISTINCT CASE WHEN f.first_chk < d.dte THEN gb.primary_guest_id END) AS returning_guests
                FROM d
                LEFT JOIN gb ON gb.chk = d.dte
                LEFT JOIN firsts f ON f.primary_guest_id = gb.primary_guest_id
                GROUP BY d.dte
                ORDER BY d.dte
                """;
    }
}
