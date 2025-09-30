package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.*;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IReportRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportRepository implements IReportRepository {
    private final DataSource ds;

    public ReportRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<ReportRoomRevenuePoint> getRoomRevenue(ReportDateRangeParams params) {
        List<ReportRoomRevenuePoint> list = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(ReportRoomRevenuePoint.findQuery())) {
            ps.setDate(1, Date.valueOf(params.getFrom()));
            ps.setDate(2, Date.valueOf(params.getTo()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(new ReportRoomRevenuePoint(rs));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReportRevenueByRoomType> getRevenueByRoomType(ReportDateRangeParams params) {
        List<ReportRevenueByRoomType> list = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(ReportRevenueByRoomType.findQuery())) {
            ps.setDate(1, Date.valueOf(params.getFrom()));
            ps.setDate(2, Date.valueOf(params.getTo()));
            boolean noFilter = params.getRoomTypeIds() == null || params.getRoomTypeIds().isEmpty();
            ps.setBoolean(3, noFilter);
            setArrayOrNull(ps, 4, toPgBigintArray(connection, params.getRoomTypeIds()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(new ReportRevenueByRoomType(rs));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReportServiceRevenueItem> getServiceRevenue(ReportDateRangeParams params) {
        List<ReportServiceRevenueItem> list = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(ReportServiceRevenueItem.findQuery())) {
            ps.setDate(1, Date.valueOf(params.getFrom()));
            ps.setDate(2, Date.valueOf(params.getTo()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(new ReportServiceRevenueItem(rs));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReportGuestMix> getGuestMix(ReportDateRangeParams params) {
        List<ReportGuestMix> list = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(ReportGuestMix.findQuery())) {
            ps.setDate(1, Date.valueOf(params.getFrom()));
            ps.setDate(2, Date.valueOf(params.getTo()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(new ReportGuestMix(rs));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReportOccupancyPoint> getOccupancy(ReportDateRangeParams params) {
        List<ReportOccupancyPoint> list = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(ReportOccupancyPoint.findQuery())) {
            ps.setDate(1, Date.valueOf(params.getFrom()));
            ps.setDate(2, Date.valueOf(params.getTo()));
            boolean noFilter = params.getRoomTypeIds() == null || params.getRoomTypeIds().isEmpty();
            ps.setBoolean(3, noFilter);
            setArrayOrNull(ps, 4, toPgBigintArray(connection, params.getRoomTypeIds()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(new ReportOccupancyPoint(rs));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Array toPgBigintArray(Connection connection, List<Long> ids) throws SQLException {
        if (ids == null || ids.isEmpty())
            return null;
        Long[] arr = ids.toArray(new Long[0]);
        return connection.createArrayOf("bigint", arr);
    }

    private void setArrayOrNull(PreparedStatement ps, int index, Array array) throws SQLException {
        if (array == null)
            ps.setNull(index, Types.ARRAY);
        else
            ps.setArray(index, array);
    }
}
