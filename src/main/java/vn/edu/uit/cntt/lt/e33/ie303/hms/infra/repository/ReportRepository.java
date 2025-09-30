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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<ReportRevenueByRoomType> getRevenueByRoomType(ReportDateRangeParams params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<ReportServiceRevenueItem> getServiceRevenue(ReportDateRangeParams params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<ReportGuestMix> getGuestMix(ReportDateRangeParams params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<ReportOccupancyPoint> getOccupancy(ReportDateRangeParams params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
