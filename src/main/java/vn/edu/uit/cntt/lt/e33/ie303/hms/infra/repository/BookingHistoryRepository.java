package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistory;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistoryFilter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IBookingHistoryRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookingHistoryRepository implements IBookingHistoryRepository {
    private final DataSource ds;

    public BookingHistoryRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<BookingHistory> search(BookingHistoryFilter filter, int limit, int offset, String sortBy,
            String sortDir) {
        List<BookingHistory> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder(BookingHistory.baseQuery());
        List<Object> params = new ArrayList<>();

        if (filter.getFromDate() != null) {
            sql.append(" AND b.checkin >= ? ");
            params.add(Timestamp.from(filter.getFromDate().toInstant()));
        }
        if (filter.getToDate() != null) {
            sql.append(" AND b.checkin <= ? ");
            params.add(Timestamp.from(filter.getToDate().toInstant()));
        }
        if (filter.getChargeType() != null && !filter.getChargeType().isBlank()) {
            sql.append(" AND b.charge_type = ? ");
            params.add(filter.getChargeType());
        }
        if (filter.getStatus() != null && !filter.getStatus().isBlank()) {
            sql.append(" AND b.status = ? ");
            params.add(filter.getStatus());
        }
        if (filter.getPaymentStatus() != null && !filter.getPaymentStatus().isBlank()) {
            sql.append(" AND b.payment_status = ? ");
            params.add(filter.getPaymentStatus());
        }
        if (filter.getRoomId() != null) {
            sql.append(" AND b.room_id = ? ");
            params.add(filter.getRoomId());
        }
        if (filter.getRoomTypeId() != null) {
            sql.append(" AND b.room_type_id = ? ");
            params.add(filter.getRoomTypeId());
        }
        if (filter.getPrimaryGuestId() != null) {
            sql.append(" AND b.primary_guest_id = ? ");
            params.add(filter.getPrimaryGuestId());
        }
        if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
            sql.append(" AND (b.booking_no ILIKE ? OR g.name ILIKE ? OR r.name ILIKE ?) ");
            String like = "%" + filter.getKeyword() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }

        Set<String> whitelist = new HashSet<>(
                List.of("checkin", "checkout", "booking_no", "status", "payment_status", "charge_type"));
        String sortColumn = whitelist.contains(sortBy) ? sortBy : "checkin";
        String sortDirection = "ASC".equalsIgnoreCase(sortDir) ? "ASC" : "DESC";
        sql.append(" ORDER BY b.").append(sortColumn).append(" ").append(sortDirection);
        sql.append(" LIMIT ? OFFSET ? ");
        params.add(limit);
        params.add(offset);

        try (Connection c = ds.getConnection();
                PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++)
                ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new BookingHistory(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public long count(BookingHistoryFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM bookings b WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (filter.getFromDate() != null) {
            sql.append(" AND b.checkin >= ? ");
            params.add(Timestamp.from(filter.getFromDate().toInstant()));
        }
        if (filter.getToDate() != null) {
            sql.append(" AND b.checkin <= ? ");
            params.add(Timestamp.from(filter.getToDate().toInstant()));
        }
        if (filter.getChargeType() != null && !filter.getChargeType().isBlank()) {
            sql.append(" AND b.charge_type = ? ");
            params.add(filter.getChargeType());
        }
        if (filter.getStatus() != null && !filter.getStatus().isBlank()) {
            sql.append(" AND b.status = ? ");
            params.add(filter.getStatus());
        }
        if (filter.getPaymentStatus() != null && !filter.getPaymentStatus().isBlank()) {
            sql.append(" AND b.payment_status = ? ");
            params.add(filter.getPaymentStatus());
        }
        if (filter.getRoomId() != null) {
            sql.append(" AND b.room_id = ? ");
            params.add(filter.getRoomId());
        }
        if (filter.getRoomTypeId() != null) {
            sql.append(" AND b.room_type_id = ? ");
            params.add(filter.getRoomTypeId());
        }
        if (filter.getPrimaryGuestId() != null) {
            sql.append(" AND b.primary_guest_id = ? ");
            params.add(filter.getPrimaryGuestId());
        }
        if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
            sql.append(
                    " AND (b.booking_no ILIKE ? OR EXISTS (SELECT 1 FROM guests g WHERE g.id=b.primary_guest_id AND g.name ILIKE ?) OR EXISTS (SELECT 1 FROM rooms r WHERE r.id=b.room_id AND r.name ILIKE ?)) ");
            String like = "%" + filter.getKeyword() + "%";
            params.add(like);
            params.add(like);
            params.add(like);
        }

        try (Connection c = ds.getConnection();
                PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++)
                ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getLong(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
