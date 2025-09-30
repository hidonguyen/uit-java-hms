package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportOccupancyPoint {
    private LocalDate date;
    private Integer availableRooms;
    private Integer occupiedRooms;
    private BigDecimal occupancyPct;

    public ReportOccupancyPoint() {
    }

    public ReportOccupancyPoint(LocalDate date, Integer availableRooms, Integer occupiedRooms,
            BigDecimal occupancyPct) {
        this.date = date;
        this.availableRooms = availableRooms;
        this.occupiedRooms = occupiedRooms;
        this.occupancyPct = occupancyPct;
    }

    public ReportOccupancyPoint(ResultSet rs) throws SQLException {
        this.date = rs.getObject("date", LocalDate.class);
        this.availableRooms = rs.getInt("available_rooms");
        this.occupiedRooms = rs.getInt("occupied_rooms");
        this.occupancyPct = rs.getBigDecimal("occupancy_pct");
    }

    public LocalDate getDate() {
        return date;
    }

    public ReportOccupancyPoint setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Integer getAvailableRooms() {
        return availableRooms;
    }

    public ReportOccupancyPoint setAvailableRooms(Integer availableRooms) {
        this.availableRooms = availableRooms;
        return this;
    }

    public Integer getOccupiedRooms() {
        return occupiedRooms;
    }

    public ReportOccupancyPoint setOccupiedRooms(Integer occupiedRooms) {
        this.occupiedRooms = occupiedRooms;
        return this;
    }

    public BigDecimal getOccupancyPct() {
        return occupancyPct;
    }

    public ReportOccupancyPoint setOccupancyPct(BigDecimal occupancyPct) {
        this.occupancyPct = occupancyPct;
        return this;
    }
}
