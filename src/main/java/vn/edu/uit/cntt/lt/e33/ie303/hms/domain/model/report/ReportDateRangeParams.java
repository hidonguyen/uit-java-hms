package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.time.LocalDate;

public class ReportDateRangeParams {
    private LocalDate from;
    private LocalDate to;
    private String granularity; // "DAY", "WEEK", "MONTH", "YEAR"

    public ReportDateRangeParams() {
    }

    public ReportDateRangeParams(LocalDate from, LocalDate to, String granularity) {
        this.from = from;
        this.to = to;
        this.granularity = granularity;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public String getGranularity() {
        return granularity;
    }

    public void setGranularity(String granularity) {
        this.granularity = granularity;
    }

    public static ReportDateRangeParams today() {
        LocalDate now = LocalDate.now();
        return new ReportDateRangeParams(now, now, "DAY");
    }

    public static ReportDateRangeParams thisMonth() {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        return new ReportDateRangeParams(start, end, "DAY");
    }

    public static ReportDateRangeParams thisYear() {
        LocalDate start = LocalDate.now().withDayOfYear(1);
        LocalDate end = start.plusYears(1).minusDays(1);
        return new ReportDateRangeParams(start, end, "MONTH");
    }
}
