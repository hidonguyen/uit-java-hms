package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportKpiSummary {
    private BigDecimal totalRevenue;
    private BigDecimal occupancyPct;
    private BigDecimal adr;
    private BigDecimal revpar;

    public ReportKpiSummary() {
    }

    public ReportKpiSummary(BigDecimal totalRevenue, BigDecimal occupancyPct, BigDecimal adr, BigDecimal revpar) {
        this.totalRevenue = totalRevenue;
        this.occupancyPct = occupancyPct;
        this.adr = adr;
        this.revpar = revpar;
    }

    public ReportKpiSummary(ResultSet rs) throws SQLException {
        this.totalRevenue = rs.getBigDecimal("total_revenue");
        this.occupancyPct = rs.getBigDecimal("occupancy_pct");
        this.adr = rs.getBigDecimal("adr");
        this.revpar = rs.getBigDecimal("revpar");
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public ReportKpiSummary setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
        return this;
    }

    public BigDecimal getOccupancyPct() {
        return occupancyPct;
    }

    public ReportKpiSummary setOccupancyPct(BigDecimal occupancyPct) {
        this.occupancyPct = occupancyPct;
        return this;
    }

    public BigDecimal getAdr() {
        return adr;
    }

    public ReportKpiSummary setAdr(BigDecimal adr) {
        this.adr = adr;
        return this;
    }

    public BigDecimal getRevpar() {
        return revpar;
    }

    public ReportKpiSummary setRevpar(BigDecimal revpar) {
        this.revpar = revpar;
        return this;
    }
}
