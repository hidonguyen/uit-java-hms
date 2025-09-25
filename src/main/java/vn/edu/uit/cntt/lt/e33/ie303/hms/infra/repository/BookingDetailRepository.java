package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.BookingDetail;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IBookingDetailRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class BookingDetailRepository implements IBookingDetailRepository {
    private final DataSource ds;

    public BookingDetailRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<BookingDetail> findAll() {
        List<BookingDetail> bookingDetails = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(BookingDetail.findAllQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                bookingDetails.add(new BookingDetail(rs));
            }
            return bookingDetails;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BookingDetail findById(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(BookingDetail.findByIdQuery())) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BookingDetail(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(BookingDetail bookingDetail) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(BookingDetail.insertQuery())) {
            bookingDetail.setInsertParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(BookingDetail bookingDetail) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(BookingDetail.updateQuery())) {
            bookingDetail.setUpdateParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(BookingDetail.deleteQuery())) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
