package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportServiceRevenueItem {
    private Long serviceId;
    private String serviceName;
    private BigDecimal revenue;
    private BigDecimal quantity;

    public ReportServiceRevenueItem() {
    }

    public ReportServiceRevenueItem(Long serviceId, String serviceName, BigDecimal revenue, BigDecimal quantity) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.revenue = revenue;
        this.quantity = quantity;
    }

    public ReportServiceRevenueItem(ResultSet rs) throws SQLException {
        this.serviceId = rs.getLong("service_id");
        this.serviceName = rs.getString("service_name");
        this.revenue = rs.getBigDecimal("revenue");
        this.quantity = rs.getBigDecimal("quantity");
    }

    public Long getServiceId() {
        return serviceId;
    }

    public ReportServiceRevenueItem setServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ReportServiceRevenueItem setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public ReportServiceRevenueItem setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
        return this;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public ReportServiceRevenueItem setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public static String findQuery() {
        return """
                SELECT s.id AS service_id,
                       s.name AS service_name,
                       COALESCE(SUM(bd.amount),0) AS revenue,
                       COALESCE(SUM(bd.quantity),0) AS quantity
                FROM booking_details bd
                JOIN services s ON s.id = bd.service_id
                WHERE bd.type = 'Service'
                  AND (bd.issued_at AT TIME ZONE 'Asia/Ho_Chi_Minh')::date BETWEEN ?::date AND ?::date
                GROUP BY s.id, s.name
                ORDER BY revenue DESC
                """;
    }
}
