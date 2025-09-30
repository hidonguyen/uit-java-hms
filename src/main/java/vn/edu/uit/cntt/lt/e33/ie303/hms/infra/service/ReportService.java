package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.*;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IReportRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IReportService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ReportService implements IReportService {
    private final IReportRepository repo;

    public ReportService(IReportRepository repo) {
        this.repo = repo;
    }

    @Override
    public ReportKpiSummary getKpi(ReportDateRangeParams params) {
        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal roomRevenue = BigDecimal.ZERO;
        BigDecimal serviceRevenue = BigDecimal.ZERO;
        BigDecimal occupancyAvg = BigDecimal.ZERO;
        long totalGuests = 0;
        long newGuests = 0;
        long returningGuests = 0;

        // Doanh thu phòng
        List<ReportRoomRevenuePoint> roomRevList = repo.getRoomRevenue(params);
        for (ReportRoomRevenuePoint r : roomRevList) {
            roomRevenue = roomRevenue.add(r.getRevenue());
        }

        // Doanh thu dịch vụ
        List<ReportServiceRevenueItem> serviceRevList = repo.getServiceRevenue(params);
        for (ReportServiceRevenueItem s : serviceRevList) {
            serviceRevenue = serviceRevenue.add(s.getRevenue());
        }

        totalRevenue = roomRevenue.add(serviceRevenue);

        // Guest mix
        List<ReportGuestMix> guestMixList = repo.getGuestMix(params);
        for (ReportGuestMix g : guestMixList) {
            newGuests += g.getNewGuests();
            returningGuests += g.getReturningGuests();
        }
        totalGuests = newGuests + returningGuests;

        // Occupancy
        List<ReportOccupancyPoint> occList = repo.getOccupancy(params);
        if (!occList.isEmpty()) {
            BigDecimal sum = BigDecimal.ZERO;
            for (ReportOccupancyPoint o : occList) {
                sum = sum.add(o.getOccupancyPct());
            }
            occupancyAvg = sum.divide(BigDecimal.valueOf(occList.size()), 2, RoundingMode.HALF_UP);
        }

        return new ReportKpiSummary()
                .setTotalRevenue(totalRevenue)
                .setRoomRevenue(roomRevenue)
                .setServiceRevenue(serviceRevenue)
                .setTotalGuests(totalGuests)
                .setNewGuests(newGuests)
                .setReturningGuests(returningGuests)
                .setOccupancyRate(occupancyAvg);
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
