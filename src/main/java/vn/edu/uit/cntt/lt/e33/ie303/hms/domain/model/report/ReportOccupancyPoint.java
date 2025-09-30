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

    public static String findQuery() {
        return """
                WITH d AS (
                  SELECT generate_series(?::date, ?::date, interval '1 day')::date AS dte
                ),
                room_base AS (
                  SELECT r.id AS room_id, r.room_type_id FROM rooms r
                  WHERE (COALESCE(?, false) OR r.room_type_id = ANY(?))
                ),
                occ AS (
                  SELECT (b.checkin AT TIME ZONE 'Asia/Ho_Chi_Minh')::date AS start_date,
                         COALESCE((b.checkout AT TIME ZONE 'Asia/Ho_Chi_Minh')::date,
                                  (b.checkin AT TIME ZONE 'Asia/Ho_Chi_Minh')::date) AS end_date,
                         b.room_id
                  FROM bookings b
                )
                SELECT d.dte AS date,
                       COUNT(rb.room_id) AS available_rooms,
                       COUNT(DISTINCT rb.room_id) FILTER (
                         WHERE EXISTS (
                           SELECT 1 FROM occ o
                           WHERE o.room_id = rb.room_id AND d.dte >= o.start_date AND d.dte < o.end_date
                         )
                       ) AS occupied_rooms,
                       CASE WHEN COUNT(rb.room_id)=0 THEN 0
                            ELSE ROUND(100.0 * COUNT(DISTINCT rb.room_id) FILTER (
                                   WHERE EXISTS (
                                     SELECT 1 FROM occ o
                                     WHERE o.room_id = rb.room_id AND d.dte >= o.start_date AND d.dte < o.end_date
                                   )
                                 ) / COUNT(rb.room_id), 2)
                       END AS occupancy_pct
                FROM d
                CROSS JOIN room_base rb
                GROUP BY d.dte
                ORDER BY d.dte
                """;
    }
}
