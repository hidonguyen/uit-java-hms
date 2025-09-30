package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.time.LocalDate;
import java.util.List;

public class ReportDateRangeParams {
    private LocalDate from;
    private LocalDate to;
    private String granularity;
    private List<Long> roomTypeIds;

    public ReportDateRangeParams() {
    }

    public ReportDateRangeParams(LocalDate from, LocalDate to, String granularity, List<Long> roomTypeIds) {
        this.from = from;
        this.to = to;
        this.granularity = granularity;
        this.roomTypeIds = roomTypeIds;
    }

    public LocalDate getFrom() {
        return from;
    }

    public ReportDateRangeParams setFrom(LocalDate from) {
        this.from = from;
        return this;
    }

    public LocalDate getTo() {
        return to;
    }

    public ReportDateRangeParams setTo(LocalDate to) {
        this.to = to;
        return this;
    }

    public String getGranularity() {
        return granularity;
    }

    public ReportDateRangeParams setGranularity(String granularity) {
        this.granularity = granularity;
        return this;
    }

    public List<Long> getRoomTypeIds() {
        return roomTypeIds;
    }

    public ReportDateRangeParams setRoomTypeIds(List<Long> roomTypeIds) {
        this.roomTypeIds = roomTypeIds;
        return this;
    }

    public static ReportDateRangeParams today() {
        LocalDate d = LocalDate.now();
        return new ReportDateRangeParams(d, d, "DAY", List.of());
    }

    public static ReportDateRangeParams last7Days() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);
        return new ReportDateRangeParams(start, end, "DAY", List.of());
    }

    public static ReportDateRangeParams last30Days() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(29);
        return new ReportDateRangeParams(start, end, "DAY", List.of());
    }

    public static ReportDateRangeParams thisMonth() {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        return new ReportDateRangeParams(start, end, "DAY", List.of());
    }

    public static ReportDateRangeParams thisYear() {
        LocalDate start = LocalDate.now().withDayOfYear(1);
        LocalDate end = start.plusYears(1).minusDays(1);
        return new ReportDateRangeParams(start, end, "MONTH", List.of());
    }
}
