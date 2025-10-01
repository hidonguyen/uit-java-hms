package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RoomType {
    private Long id;
    private String code;
    private String name;
    private int baseOccupancy;
    private int maxOccupancy;
    private double baseRate;
    private double hourRate;
    private double extraAdultFee;
    private double extraChildFee;
    private String description;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public RoomType() {
    }

    public RoomType(
            Long id, String code, String name, int baseOccupancy, int maxOccupancy,
            double baseRate, double hourRate, double extraAdultFee, double extraChildFee,
            String description, LocalDateTime createdAt, Long createdBy,
            LocalDateTime updatedAt, Long updatedBy) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.baseOccupancy = baseOccupancy;
        this.maxOccupancy = maxOccupancy;
        this.baseRate = baseRate;
        this.hourRate = hourRate;
        this.extraAdultFee = extraAdultFee;
        this.extraChildFee = extraChildFee;
        this.description = description;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public RoomType(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.code = rs.getString("code");
        this.name = rs.getString("name");
        this.baseOccupancy = rs.getInt("base_occupancy");
        this.maxOccupancy = rs.getInt("max_occupancy");
        this.baseRate = rs.getDouble("base_rate");
        this.hourRate = rs.getDouble("hour_rate");
        this.extraAdultFee = rs.getDouble("extra_adult_fee");
        this.extraChildFee = rs.getDouble("extra_child_fee");
        this.description = rs.getString("description");
        this.createdAt = rs.getObject("created_at", LocalDateTime.class);
        this.createdBy = rs.getObject("created_by", Long.class);
        this.updatedAt = rs.getObject("updated_at", LocalDateTime.class);
        this.updatedBy = rs.getObject("updated_by", Long.class);
    }

    public Long getId() {
        return id;
    }

    public RoomType setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public RoomType setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoomType setName(String name) {
        this.name = name;
        return this;
    }

    public int getBaseOccupancy() {
        return baseOccupancy;
    }

    public RoomType setBaseOccupancy(int baseOccupancy) {
        this.baseOccupancy = baseOccupancy;
        return this;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public RoomType setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
        return this;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public RoomType setBaseRate(double baseRate) {
        this.baseRate = baseRate;
        return this;
    }

    public double getHourRate() {
        return hourRate;
    }

    public RoomType setHourRate(double hourRate) {
        this.hourRate = hourRate;
        return this;
    }

    public double getExtraAdultFee() {
        return extraAdultFee;
    }

    public RoomType setExtraAdultFee(double extraAdultFee) {
        this.extraAdultFee = extraAdultFee;
        return this;
    }

    public double getExtraChildFee() {
        return extraChildFee;
    }

    public RoomType setExtraChildFee(double extraChildFee) {
        this.extraChildFee = extraChildFee;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RoomType setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public RoomType setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public RoomType setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public RoomType setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public RoomType setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    // ===== SQL builders =====

    public static String findAllQuery() {
        return """
                SELECT id, code, name, base_occupancy, max_occupancy,
                       base_rate, hour_rate, extra_adult_fee, extra_child_fee,
                       description, created_at, created_by, updated_at, updated_by
                FROM room_types
                """;
    }

    public static String findByIdQuery() {
        return """
                SELECT id, code, name, base_occupancy, max_occupancy,
                       base_rate, hour_rate, extra_adult_fee, extra_child_fee,
                       description, created_at, created_by, updated_at, updated_by
                FROM room_types
                WHERE id = ?
                """;
    }

    // ✅ KHÔNG include created_at/updated_at để DB tự NOW()
    public static String insertQuery() {
        return """
                INSERT INTO room_types
                  (code, name, base_occupancy, max_occupancy, base_rate, hour_rate,
                   extra_adult_fee, extra_child_fee, description, created_by, updated_by)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
    }

    public void setInsertParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.code);
        ps.setString(2, this.name);
        ps.setInt(3, this.baseOccupancy);
        ps.setInt(4, this.maxOccupancy);
        ps.setDouble(5, this.baseRate);
        ps.setDouble(6, this.hourRate);
        ps.setDouble(7, this.extraAdultFee);
        ps.setDouble(8, this.extraChildFee);
        ps.setString(9, this.description);

        if (this.createdBy != null)
            ps.setLong(10, this.createdBy);
        else
            ps.setNull(10, java.sql.Types.BIGINT);

        if (this.updatedBy != null)
            ps.setLong(11, this.updatedBy);
        else
            ps.setNull(11, java.sql.Types.BIGINT);
    }

    // ✅ UPDATE: set updated_at = NOW(), không đụng created_*
    public static String updateQuery() {
        return """
                UPDATE room_types
                   SET code = ?,
                       name = ?,
                       base_occupancy = ?,
                       max_occupancy = ?,
                       base_rate = ?,
                       hour_rate = ?,
                       extra_adult_fee = ?,
                       extra_child_fee = ?,
                       description = ?,
                       updated_at = NOW(),
                       updated_by = ?
                 WHERE id = ?
                """;
    }

    public void setUpdateParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.code);
        ps.setString(2, this.name);
        ps.setInt(3, this.baseOccupancy);
        ps.setInt(4, this.maxOccupancy);
        ps.setDouble(5, this.baseRate);
        ps.setDouble(6, this.hourRate);
        ps.setDouble(7, this.extraAdultFee);
        ps.setDouble(8, this.extraChildFee);
        ps.setString(9, this.description);

        if (this.updatedBy != null)
            ps.setLong(10, this.updatedBy);
        else
            ps.setNull(10, java.sql.Types.BIGINT);

        ps.setLong(11, this.id);
    }

    public static String deleteQuery() {
        return "DELETE FROM room_types WHERE id = ?";
    }
}
