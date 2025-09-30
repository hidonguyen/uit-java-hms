package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.time.OffsetDateTime;
import java.util.List;

public class ReportDateRangeParams {
    private OffsetDateTime from;
    private OffsetDateTime to;
    private String granularity;
    private List<Long> roomTypeIds;

    public ReportDateRangeParams() {
    }

    public ReportDateRangeParams(OffsetDateTime from, OffsetDateTime to, String granularity, List<Long> roomTypeIds) {
        this.from = from;
        this.to = to;
        this.granularity = granularity;
        this.roomTypeIds = roomTypeIds;
    }

    public OffsetDateTime getFrom() {
        return from;
    }

    public ReportDateRangeParams setFrom(OffsetDateTime from) {
        this.from = from;
        return this;
    }

    public OffsetDateTime getTo() {
        return to;
    }

    public ReportDateRangeParams setTo(OffsetDateTime to) {
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
}
