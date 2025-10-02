package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IRoomRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class RoomRepository implements IRoomRepository {
    private final DataSource ds;

    public RoomRepository(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(Room.findAllQuery());
                ResultSet rs = query.executeQuery()) {
            while (rs.next()) {
                rooms.add(new Room(rs));
            }
            return rooms;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> findByRoomTypeId(Long roomTypeId) {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ds.getConnection();
                PreparedStatement query = connection.prepareStatement(Room.findByRoomTypeIdQuery())) {
            query.setLong(1, roomTypeId);
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    rooms.add(new Room(rs));
                }
                return rooms;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Room findById(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Room.findByIdQuery())) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Room(rs);
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(Room room) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Room.insertQuery())) {
            room.setInsertParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Room room) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Room.updateQuery())) {
            room.setUpdateParameters(ps);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Long id) {
        try (Connection connection = ds.getConnection();
                PreparedStatement ps = connection.prepareStatement(Room.deleteQuery())) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
