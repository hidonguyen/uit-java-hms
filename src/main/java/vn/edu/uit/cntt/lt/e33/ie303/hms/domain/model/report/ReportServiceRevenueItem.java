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
}
