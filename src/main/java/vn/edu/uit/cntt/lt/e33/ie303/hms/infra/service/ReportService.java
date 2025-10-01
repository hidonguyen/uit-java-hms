package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.*;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IReportRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IReportService;

import java.math.BigDecimal;
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
        long totalGuests = 0;
        long newGuests = 0;
        long returningGuests = 0;

        List<ReportRoomRevenuePoint> roomRevList = repo.getRoomRevenue(params);
        for (ReportRoomRevenuePoint r : roomRevList) {
            roomRevenue = roomRevenue.add(r.getRevenue());
        }

        List<ReportServiceRevenueItem> serviceRevList = repo.getServiceRevenue(params);
        for (ReportServiceRevenueItem s : serviceRevList) {
            serviceRevenue = serviceRevenue.add(s.getRevenue());
        }

        totalRevenue = roomRevenue.add(serviceRevenue);

        List<ReportGuestMix> guestMixList = repo.getGuestMix(params);
        for (ReportGuestMix g : guestMixList) {
            newGuests += g.getNewGuests();
            returningGuests += g.getReturningGuests();
        }
        totalGuests = newGuests + returningGuests;

        return new ReportKpiSummary()
                .setTotalRevenue(totalRevenue)
                .setRoomRevenue(roomRevenue)
                .setServiceRevenue(serviceRevenue)
                .setTotalGuests(totalGuests)
                .setNewGuests(newGuests)
                .setReturningGuests(returningGuests);
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
    public List<ReportBookingCountPoint> getOccupancy(ReportDateRangeParams params) {
        return repo.getBookingCounts(params);
    }
}
