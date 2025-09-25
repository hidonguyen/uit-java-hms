package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IRoomTypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class RoomTypeRepository implements IRoomTypeRepository {
    private final DataSource ds;

    public RoomTypeRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<RoomType> findAll() {
        List<RoomType> services = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(RoomType.findAllQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                services.add(new RoomType(rs));
            }
            return services;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RoomType findById(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(RoomType.findByIdQuery())) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new RoomType(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(RoomType roomType) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(RoomType.insertQuery())) {
            roomType.setInsertParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(RoomType roomType) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(RoomType.updateQuery())) {
            roomType.setUpdateParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(RoomType.deleteQuery())) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
