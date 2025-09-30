package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import java.util.List;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.*;

public interface IReportService {
    ReportKpiSummary getKpi(ReportDateRangeParams params);

    List<ReportRoomRevenuePoint> getRoomRevenue(ReportDateRangeParams params);

    List<ReportRevenueByRoomType> getRevenueByRoomType(ReportDateRangeParams params);

    List<ReportServiceRevenueItem> getServiceRevenue(ReportDateRangeParams params);

    List<ReportGuestMix> getGuestMix(ReportDateRangeParams params);

    List<ReportOccupancyPoint> getOccupancy(ReportDateRangeParams params);
}
