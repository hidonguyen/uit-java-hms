package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.HousekeepingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.RoomStatus;

public class Room {
    private Long id;
    private String name;
    private Long roomTypeId;
    private String description;
    private HousekeepingStatus housekeepingStatus;
    private RoomStatus status;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public Room() {}

    public Room(Long id, String name, Long roomTypeId, String description, HousekeepingStatus housekeepingStatus, RoomStatus status, LocalDateTime createdAt, Long createdBy, LocalDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.name = name;
        this.roomTypeId = roomTypeId;
        this.description = description;
        this.housekeepingStatus = housekeepingStatus;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Room(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.name = rs.getString("name");
        this.roomTypeId = rs.getLong("room_type_id");
        this.description = rs.getString("description");
        this.housekeepingStatus = HousekeepingStatus.valueOf(rs.getString("housekeeping_status"));
        this.status = RoomStatus.valueOf(rs.getString("status"));
        this.createdAt = rs.getObject("created_at", LocalDateTime.class);
        this.createdBy = rs.getLong("created_by");
        this.updatedAt = rs.getObject("updated_at", LocalDateTime.class);
        this.updatedBy = rs.getLong("updated_by");
    }

    public Long getId() {
        return id;
    }

    public Room setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Room setName(String name) {
        this.name = name;
        return this;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public Room setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Room setDescription(String description) {
        this.description = description;
        return this;
    }

    public HousekeepingStatus getHousekeepingStatus() {
        return housekeepingStatus;
    }

    public Room setHousekeepingStatus(HousekeepingStatus housekeepingStatus) {
        this.housekeepingStatus = housekeepingStatus;
        return this;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public Room setStatus(RoomStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Room setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Room setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Room setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Room setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public static String findAllQuery() {
        return "SELECT id, name, room_type_id, description, housekeeping_status, status, created_at, created_by, updated_at, updated_by FROM rooms";
    }

    public static String findByRoomTypeIdQuery() {
        return "SELECT id, name, room_type_id, description, housekeeping_status, status, created_at, created_by, updated_at, updated_by FROM rooms WHERE room_type_id = ?";
    }

    public static String findByIdQuery() {
        return "SELECT id, name, room_type_id, description, housekeeping_status, status, created_at, created_by, updated_at, updated_by FROM rooms WHERE id = ?";
    }

    public static String insertQuery() {
        return "INSERT INTO rooms (name, room_type_id, description, housekeeping_status, status, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    public void setInsertParameters(PreparedStatement ps) throws SQLException {
        System.out.println(this.housekeepingStatus);
        ps.setString(1, this.name);
        ps.setLong(2, this.roomTypeId);
        ps.setString(3, this.description);
        ps.setString(4, this.housekeepingStatus.name());
        ps.setString(5, this.status.name());
        ps.setObject(6, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(7, this.createdBy);
        } else {
            ps.setNull(7, java.sql.Types.BIGINT);
        }
        ps.setObject(8, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(9, this.updatedBy);
        } else {
            ps.setNull(9, java.sql.Types.BIGINT);
        }
    }

    public static String updateQuery() {
        return "UPDATE rooms SET name = ?, room_type_id = ?, description = ?, housekeeping_status = ?, status = ?, created_at = ?, created_by = ?, updated_at = ?, updated_by = ? WHERE id = ?";
    }

    public void setUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.name);
        ps.setLong(2, this.roomTypeId);
        ps.setString(3, this.description);
        ps.setString(4, this.housekeepingStatus.name());
        ps.setString(5, this.status.name());
        ps.setObject(6, this.createdAt);
        if (this.createdBy != null) {
            ps.setLong(7, this.createdBy);
        } else {
            ps.setNull(7, java.sql.Types.BIGINT);
        }
        ps.setObject(8, this.updatedAt);
        if (this.updatedBy != null) {
            ps.setLong(9, this.updatedBy);
        } else {
            ps.setNull(9, java.sql.Types.BIGINT);
        }
        ps.setLong(10, this.id);
    }

    public static String deleteQuery() {
        return "DELETE FROM rooms WHERE id = ?";
    }
}