package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReportSeriesPoint {
    private LocalDate period;
    private BigDecimal value;
    private String label;

    public ReportSeriesPoint() {
    }

    public ReportSeriesPoint(LocalDate period, BigDecimal value, String label) {
        this.period = period;
        this.value = value;
        this.label = label;
    }

    public ReportSeriesPoint(ResultSet rs) throws SQLException {
        this.period = rs.getObject("period", LocalDate.class);
        this.value = rs.getBigDecimal("value");
        this.label = rs.getString("label");
    }

    public LocalDate getPeriod() {
        return period;
    }

    public ReportSeriesPoint setPeriod(LocalDate period) {
        this.period = period;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ReportSeriesPoint setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ReportSeriesPoint setLabel(String label) {
        this.label = label;
        return this;
    }
}
