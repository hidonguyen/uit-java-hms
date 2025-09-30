package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Booking;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IBookingRepository;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class BookingRepository implements IBookingRepository {
    private final DataSource ds;

    public BookingRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(Booking.findAllQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                bookings.add(new Booking(rs));
            }
            return bookings;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Booking findById(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Booking.findByIdQuery())) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Booking(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Booking booking) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Booking.insertQuery())) {
            booking.setInsertParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Booking booking) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Booking.updateQuery())) {
            booking.setUpdateParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Booking.deleteQuery())) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<TodayBookingDto> findTodayBookings() {
        ArrayList<TodayBookingDto> todayBookings = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement query = connection.prepareStatement(Booking.findTodayBookingsIncludeDetailsQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                todayBookings.add(new TodayBookingDto(rs));
            }
            return todayBookings;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}