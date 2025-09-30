package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.List;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.*;

public interface IReportRepository {
    List<ReportRoomRevenuePoint> getRoomRevenue(ReportDateRangeParams params);

    List<ReportRevenueByRoomType> getRevenueByRoomType(ReportDateRangeParams params);

    List<ReportServiceRevenueItem> getServiceRevenue(ReportDateRangeParams params);

    List<ReportGuestMix> getGuestMix(ReportDateRangeParams params);

    List<ReportOccupancyPoint> getOccupancy(ReportDateRangeParams params);
}
