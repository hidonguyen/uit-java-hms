package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.*;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IReportRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IReportService;

import java.util.List;

public class ReportService implements IReportService {
    private final IReportRepository repo;

    public ReportService(IReportRepository repo) {
        this.repo = repo;
    }

    @Override
    public ReportKpiSummary getKpi(ReportDateRangeParams params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<ReportRoomRevenuePoint> getRoomRevenue(ReportDateRangeParams params) {
        return repo.getRoomRevenue(params);
    }

    @Override
    public List<ReportRevenueByRoomType> getRevenueByRoomType(ReportDateRangeParams params) {
        return repo.getRevenueByRoomType(params);
    }

    @Override
    public List<ReportServiceRevenueItem> getServiceRevenue(ReportDateRangeParams params) {
        return repo.getServiceRevenue(params);
    }

    @Override
    public List<ReportGuestMix> getGuestMix(ReportDateRangeParams params) {
        return repo.getGuestMix(params);
    }

    @Override
    public List<ReportOccupancyPoint> getOccupancy(ReportDateRangeParams params) {
        return repo.getOccupancy(params);
    }
}
