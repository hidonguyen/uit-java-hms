package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.report;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportDateRangeParams;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportGuestMix;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportKpiSummary;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportOccupancyPoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRevenueByRoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRoomRevenuePoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportServiceRevenueItem;

public class ReportsView extends JPanel {
    private final JPanel kpiPanel;
    private final JPanel chartPanel;
    private final JButton filterBtn;
    private Consumer<ReportDateRangeParams> filterHandler;

    public ReportsView() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== KPI Summary =====
        kpiPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        add(kpiPanel, BorderLayout.NORTH);

        // ===== Chart / Table zone =====
        chartPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        add(chartPanel, BorderLayout.CENTER);

        // ===== Filter control =====
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterBtn = new JButton("Chọn khoảng thời gian");
        footer.add(filterBtn);
        add(footer, BorderLayout.SOUTH);

        bindEvents();
    }

    private void bindEvents() {
        filterBtn.addActionListener(_ -> {
            // Fake: cho hiển thị date range dialog
            JOptionPane.showMessageDialog(this, "Hiển thị form chọn ngày (TODO)");
            if (filterHandler != null) {
                filterHandler.accept(ReportDateRangeParams.today());
            }
        });
    }

    // ========== Event wiring ==========
    public void onFilterDateRange(Consumer<ReportDateRangeParams> handler) {
        this.filterHandler = handler;
    }

    // ========== Data binding ==========
    public void setKpiSummary(ReportKpiSummary kpi) {
        kpiPanel.removeAll();
        kpiPanel.add(makeKpiCard("Doanh thu", kpi.getTotalRevenue() + " VND"));
        kpiPanel.add(makeKpiCard("ADR", kpi.getAdr() + " VND"));
        kpiPanel.add(makeKpiCard("RevPAR", kpi.getRevpar() + " VND"));
        kpiPanel.add(makeKpiCard("Công suất", kpi.getOccupancyPct() + "%"));
        kpiPanel.revalidate();
        kpiPanel.repaint();
    }

    public void setRoomRevenueData(List<ReportRoomRevenuePoint> data) {
        chartPanel.add(makeChartPlaceholder("Doanh thu phòng theo thời gian (" + data.size() + " điểm)"));
        refreshCharts();
    }

    public void setRevenueByRoomTypeData(List<ReportRevenueByRoomType> data) {
        chartPanel.add(makeChartPlaceholder("Doanh thu theo loại phòng (" + data.size() + " loại)"));
        refreshCharts();
    }

    public void setServiceRevenueData(List<ReportServiceRevenueItem> data) {
        chartPanel.add(makeChartPlaceholder("Doanh thu dịch vụ (" + data.size() + " dịch vụ)"));
        refreshCharts();
    }

    public void setGuestMixData(List<ReportGuestMix> data) {
        chartPanel.add(makeChartPlaceholder("Khách mới vs quay lại (" + data.size() + ")"));
        refreshCharts();
    }

    public void setOccupancyData(List<ReportOccupancyPoint> data) {
        chartPanel.add(makeChartPlaceholder("Công suất phòng (" + data.size() + " điểm)"));
        refreshCharts();
    }

    // ========== Helper UI ==========
    private JPanel makeKpiCard(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        JLabel lbl = new JLabel(value, SwingConstants.CENTER);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(lbl, BorderLayout.CENTER);
        return panel;
    }

    private JPanel makeChartPlaceholder(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder(title));
        JLabel lbl = new JLabel("Chart placeholder", SwingConstants.CENTER);
        p.add(lbl, BorderLayout.CENTER);
        return p;
    }

    private void refreshCharts() {
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
