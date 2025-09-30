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

    public static String queryByDateRange() {
        return "SELECT dte AS date, " +
                "SUM(CASE WHEN is_new THEN 1 ELSE 0 END) AS new_guests, " +
                "SUM(CASE WHEN is_new = false THEN 1 ELSE 0 END) AS returning_guests " +
                "FROM report_guest_mix(?, ?) GROUP BY dte ORDER BY dte ASC";
    }
}
