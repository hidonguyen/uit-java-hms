package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IGuestRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class GuestRepository implements IGuestRepository {
    private final DataSource ds;

    public GuestRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<Guest> findAll() {
        List<Guest> guests = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(Guest.findAllQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                guests.add(new Guest(rs));
            }
            return guests;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Guest findById(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Guest.findByIdQuery())) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Guest(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Guest guest) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Guest.insertQuery())) {
            guest.setInsertParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Guest guest) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Guest.updateQuery())) {
            guest.setUpdateParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Guest.deleteQuery())) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
