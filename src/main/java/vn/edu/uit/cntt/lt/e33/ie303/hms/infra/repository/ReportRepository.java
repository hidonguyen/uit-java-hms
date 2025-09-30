package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.*;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IReportRepository;

import javax.sql.DataSource;
import java.util.List;

public class ReportRepository implements IReportRepository {
    private final DataSource ds;

    public ReportRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<ReportRoomRevenuePoint> getRoomRevenue(ReportDateRangeParams params) {
        return List.of();
    }

    @Override
    public List<ReportRevenueByRoomType> getRevenueByRoomType(ReportDateRangeParams params) {
        return List.of();
    }

    @Override
    public List<ReportServiceRevenueItem> getServiceRevenue(ReportDateRangeParams params) {
        return List.of();
    }

    @Override
    public List<ReportGuestMix> getGuestMix(ReportDateRangeParams params) {
        return List.of();
    }

    @Override
    public List<ReportOccupancyPoint> getOccupancy(ReportDateRangeParams params) {
        return List.of();
    }
}
