package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.math.BigDecimal;

public class ReportKpiSummary {
    private BigDecimal totalRevenue;
    private BigDecimal roomRevenue;
    private BigDecimal serviceRevenue;
    private long totalGuests;
    private long newGuests;
    private long returningGuests;
    private BigDecimal occupancyRate;
    private BigDecimal adr;
    private BigDecimal revpar;

    public ReportKpiSummary() {
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public ReportKpiSummary setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
        return this;
    }

    public BigDecimal getRoomRevenue() {
        return roomRevenue;
    }

    public ReportKpiSummary setRoomRevenue(BigDecimal roomRevenue) {
        this.roomRevenue = roomRevenue;
        return this;
    }

    public BigDecimal getServiceRevenue() {
        return serviceRevenue;
    }

    public ReportKpiSummary setServiceRevenue(BigDecimal serviceRevenue) {
        this.serviceRevenue = serviceRevenue;
        return this;
    }

    public long getTotalGuests() {
        return totalGuests;
    }

    public ReportKpiSummary setTotalGuests(long totalGuests) {
        this.totalGuests = totalGuests;
        return this;
    }

    public long getNewGuests() {
        return newGuests;
    }

    public ReportKpiSummary setNewGuests(long newGuests) {
        this.newGuests = newGuests;
        return this;
    }

    public long getReturningGuests() {
        return returningGuests;
    }

    public ReportKpiSummary setReturningGuests(long returningGuests) {
        this.returningGuests = returningGuests;
        return this;
    }

    public BigDecimal getOccupancyRate() {
        return occupancyRate;
    }

    public ReportKpiSummary setOccupancyRate(BigDecimal occupancyRate) {
        this.occupancyRate = occupancyRate;
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

    public BigDecimal getOccupancyPct() {
        return occupancyRate;
    }

    public ReportKpiSummary setOccupancyPct(BigDecimal occupancyPct) {
        this.occupancyRate = occupancyPct;
        return this;
    }
}
