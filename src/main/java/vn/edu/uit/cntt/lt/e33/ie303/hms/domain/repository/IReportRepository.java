package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportBookingCountPoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportDateRangeParams;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportGuestMix;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRevenueByRoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRoomRevenuePoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportServiceRevenueItem;

public interface IReportRepository {
    List<ReportRoomRevenuePoint> getRoomRevenue(ReportDateRangeParams params);

    List<ReportRevenueByRoomType> getRevenueByRoomType(ReportDateRangeParams params);

    List<ReportServiceRevenueItem> getServiceRevenue(ReportDateRangeParams params);

    List<ReportGuestMix> getGuestMix(ReportDateRangeParams params);

    List<ReportBookingCountPoint> getBookingCounts(ReportDateRangeParams params);
}
