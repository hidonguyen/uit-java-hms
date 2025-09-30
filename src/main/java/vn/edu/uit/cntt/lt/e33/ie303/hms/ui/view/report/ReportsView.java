package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.report;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportDateRangeParams;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportGuestMix;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportKpiSummary;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportOccupancyPoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRevenueByRoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRoomRevenuePoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportServiceRevenueItem;

import java.time.format.DateTimeFormatter;

public class ReportsView extends JPanel {
    private static final Color PRIMARY = new Color(79, 70, 229);
    private static final Color SECONDARY = new Color(236, 72, 153);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color WARNING = new Color(251, 146, 60);
    private static final Color INFO = new Color(59, 130, 246);
    private static final Color BG_LIGHT = new Color(249, 250, 251);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color[] CHART_COLORS = {
            new Color(99, 102, 241), new Color(236, 72, 153),
            new Color(34, 197, 94), new Color(251, 146, 60),
            new Color(59, 130, 246), new Color(168, 85, 247)
    };

    private final JComboBox<String> quickRange;
    private final JSpinner fromSpinner;
    private final JSpinner toSpinner;
    private final JComboBox<String> granularity;
    private final JButton applyBtn;

    private final JLabel kpiRevenue;
    private final JLabel kpiRoomRevenue;
    private final JLabel kpiServiceRevenue;
    private final JLabel kpiOcc;
    private final JLabel kpiGuests;

    private final DefaultCategoryDataset revenueDataset;
    private final DefaultCategoryDataset occDataset;
    private final DefaultPieDataset roomTypePie;
    private final DefaultPieDataset servicePie;
    private final DefaultPieDataset guestMixPie;

    private Consumer<ReportDateRangeParams> filterHandler;

    public ReportsView() {
        super(new BorderLayout());
        setBackground(BG_LIGHT);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel toolbar = createToolbar();
        quickRange = (JComboBox<String>) getComponentByName(toolbar, "quickRange");
        fromSpinner = (JSpinner) getComponentByName(toolbar, "fromSpinner");
        toSpinner = (JSpinner) getComponentByName(toolbar, "toSpinner");
        granularity = (JComboBox<String>) getComponentByName(toolbar, "granularity");
        applyBtn = (JButton) getComponentByName(toolbar, "applyBtn");

        JPanel kpiPanel = createKpiPanel();
        kpiRevenue = (JLabel) getComponentByName(kpiPanel, "kpiRevenue");
        kpiRoomRevenue = (JLabel) getComponentByName(kpiPanel, "kpiRoomRevenue");
        kpiServiceRevenue = (JLabel) getComponentByName(kpiPanel, "kpiServiceRevenue");
        kpiOcc = (JLabel) getComponentByName(kpiPanel, "kpiOcc");
        kpiGuests = (JLabel) getComponentByName(kpiPanel, "kpiGuests");

        revenueDataset = new DefaultCategoryDataset();
        occDataset = new DefaultCategoryDataset();
        roomTypePie = new DefaultPieDataset();
        servicePie = new DefaultPieDataset();
        guestMixPie = new DefaultPieDataset();

        JPanel chartsPanel = createChartsPanel();

        JPanel mainPanel = new JPanel(new BorderLayout(0, 16));
        mainPanel.setBackground(BG_LIGHT);
        mainPanel.add(toolbar, BorderLayout.NORTH);
        mainPanel.add(kpiPanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(180);
        splitPane.setResizeWeight(0.0);
        splitPane.setTopComponent(mainPanel);
        splitPane.setBottomComponent(chartsPanel);
        splitPane.setBorder(null);

        add(splitPane);

        quickRange.addActionListener(e -> applyQuickRange());
        applyBtn.addActionListener(e -> applyFilter());
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        toolbar.setBackground(CARD_BG);
        toolbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
                new EmptyBorder(16, 20, 16, 20)));

        JComboBox<String> qr = new JComboBox<>(new String[] {
                "Hôm nay", "7 ngày", "30 ngày", "Tháng này", "Năm nay"
        });
        qr.setName("quickRange");
        styleComboBox(qr);

        JSpinner fs = new JSpinner(new SpinnerDateModel());
        JSpinner ts = new JSpinner(new SpinnerDateModel());
        fs.setName("fromSpinner");
        ts.setName("toSpinner");
        styleSpinner(fs);
        styleSpinner(ts);
        ((JSpinner.DateEditor) fs.getEditor()).getFormat().applyPattern("dd/MM/yyyy");
        ((JSpinner.DateEditor) ts.getEditor()).getFormat().applyPattern("dd/MM/yyyy");

        JComboBox<String> gr = new JComboBox<>(new String[] { "DAY", "WEEK", "MONTH", "YEAR" });
        gr.setName("granularity");
        styleComboBox(gr);

        JButton btn = new JButton("Áp dụng");
        btn.setName("applyBtn");
        styleButton(btn);

        toolbar.add(createLabel("Nhanh:"));
        toolbar.add(qr);
        toolbar.add(Box.createHorizontalStrut(8));
        toolbar.add(createLabel("Từ:"));
        toolbar.add(fs);
        toolbar.add(createLabel("Đến:"));
        toolbar.add(ts);
        toolbar.add(Box.createHorizontalStrut(8));
        toolbar.add(createLabel("Chi tiết:"));
        toolbar.add(gr);
        toolbar.add(Box.createHorizontalStrut(8));
        toolbar.add(btn);

        return toolbar;
    }

    private JPanel createKpiPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 16, 0));
        panel.setBackground(BG_LIGHT);

        JLabel rev = new JLabel("-");
        JLabel room = new JLabel("-");
        JLabel service = new JLabel("-");
        JLabel occ = new JLabel("-");
        JLabel guests = new JLabel("-");

        rev.setName("kpiRevenue");
        room.setName("kpiRoomRevenue");
        service.setName("kpiServiceRevenue");
        occ.setName("kpiOcc");
        guests.setName("kpiGuests");

        panel.add(createKpiCard("Tổng doanh thu", rev, PRIMARY));
        panel.add(createKpiCard("Doanh thu phòng", room, SUCCESS));
        panel.add(createKpiCard("Doanh thu dịch vụ", service, INFO));
        panel.add(createKpiCard("Công suất TB", occ, WARNING));
        panel.add(createKpiCard("Tổng khách", guests, SECONDARY));

        return panel;
    }

    private JPanel createKpiCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
                new EmptyBorder(20, 20, 20, 20)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(107, 114, 128));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(accentColor);
        valueLabel.setHorizontalAlignment(SwingConstants.LEFT);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createChartsPanel() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabs.setBackground(BG_LIGHT);

        JFreeChart revenueChart = createLineChart("Doanh thu theo thời gian", "Thời gian", "VND (triệu)",
                revenueDataset);
        JFreeChart occChart = createLineChart("Công suất theo thời gian", "Thời gian", "Phần trăm (%)", occDataset);
        JFreeChart roomTypeChart = createPieChart("Doanh thu theo loại phòng", roomTypePie);
        JFreeChart serviceChart = createPieChart("Doanh thu dịch vụ", servicePie);
        JFreeChart guestMixChart = createPieChart("Phân bổ khách", guestMixPie);

        JPanel trendPanel = new JPanel(new GridLayout(1, 2, 16, 0));
        trendPanel.setBackground(BG_LIGHT);
        trendPanel.add(createChartCard(revenueChart));
        trendPanel.add(createChartCard(occChart));

        JPanel mixPanel = new JPanel(new GridLayout(1, 3, 16, 0));
        mixPanel.setBackground(BG_LIGHT);
        mixPanel.add(createChartCard(roomTypeChart));
        mixPanel.add(createChartCard(serviceChart));
        mixPanel.add(createChartCard(guestMixChart));

        tabs.addTab("Xu hướng", trendPanel);
        tabs.addTab("Cơ cấu", mixPanel);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_LIGHT);
        wrapper.setBorder(new EmptyBorder(16, 0, 0, 0));
        wrapper.add(tabs);

        return wrapper;
    }

    private JFreeChart createLineChart(String title, String xLabel, String yLabel, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(title, xLabel, yLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(CARD_BG);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getTitle().setPaint(new Color(31, 41, 55));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(229, 231, 235));
        plot.setOutlineVisible(false);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, PRIMARY);
        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesShapesVisible(0, true);
        plot.setRenderer(renderer);

        return chart;
    }

    private JFreeChart createPieChart(String title, DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);

        chart.setBackgroundPaint(CARD_BG);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getTitle().setPaint(new Color(31, 41, 55));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 11));

        for (int i = 0; i < CHART_COLORS.length; i++) {
            plot.setSectionPaint(i, CHART_COLORS[i % CHART_COLORS.length]);
        }

        return chart;
    }

    private JPanel createChartCard(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(CARD_BG);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
                new EmptyBorder(12, 12, 12, 12)));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_LIGHT);
        wrapper.add(chartPanel);

        return wrapper;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(75, 85, 99));
        return label;
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setPreferredSize(new Dimension(120, 36));
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        spinner.setPreferredSize(new Dimension(120, 36));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 36));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private Component getComponentByName(Container container, String name) {
        for (Component c : container.getComponents()) {
            if (name.equals(c.getName()))
                return c;
            if (c instanceof Container) {
                Component found = getComponentByName((Container) c, name);
                if (found != null)
                    return found;
            }
        }
        return null;
    }

    private void applyQuickRange() {
        String q = (String) quickRange.getSelectedItem();
        ReportDateRangeParams p = switch (q) {
            case "Hôm nay" -> ReportDateRangeParams.today();
            case "30 ngày" -> ReportDateRangeParams.last30Days();
            case "Tháng này" -> ReportDateRangeParams.thisMonth();
            case "Năm nay" -> ReportDateRangeParams.thisYear();
            default -> ReportDateRangeParams.last7Days();
        };
        setFilterRange(p);
        applyFilter();
    }

    private void applyFilter() {
        if (filterHandler == null)
            return;
        java.util.Date f = (java.util.Date) fromSpinner.getValue();
        java.util.Date t = (java.util.Date) toSpinner.getValue();
        String g = (String) granularity.getSelectedItem();
        ReportDateRangeParams p = new ReportDateRangeParams(
                new java.sql.Date(f.getTime()).toLocalDate(),
                new java.sql.Date(t.getTime()).toLocalDate(),
                g, java.util.List.of());
        filterHandler.accept(p);
    }

    public void setFilterRange(ReportDateRangeParams p) {
        fromSpinner.setValue(java.sql.Date.valueOf(p.getFrom()));
        toSpinner.setValue(java.sql.Date.valueOf(p.getTo()));
        if (p.getGranularity() != null)
            granularity.setSelectedItem(p.getGranularity());
    }

    public void onFilterDateRange(Consumer<ReportDateRangeParams> handler) {
        this.filterHandler = handler;
    }

    public void setKpiSummary(ReportKpiSummary kpi) {
        kpiRevenue.setText(formatCurrency(kpi == null ? null : kpi.getTotalRevenue()));
        kpiRoomRevenue.setText(formatCurrency(kpi == null ? null : kpi.getRoomRevenue()));
        kpiServiceRevenue.setText(formatCurrency(kpi == null ? null : kpi.getServiceRevenue()));
        kpiOcc.setText(
                kpi == null || kpi.getOccupancyRate() == null ? "-" : String.format("%.1f%%", kpi.getOccupancyRate()));
        kpiGuests.setText(kpi == null ? "-" : String.format("%,d", kpi.getTotalGuests()));
    }

    public void setRoomRevenueData(List<ReportRoomRevenuePoint> data) {
        revenueDataset.clear();
        if (data != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM");
            data.forEach(p -> revenueDataset.addValue(
                    p.getRevenue().doubleValue() / 1_000_000,
                    "Doanh thu",
                    p.getDate().format(df)));
        }
    }

    public void setRevenueByRoomTypeData(List<ReportRevenueByRoomType> data) {
        roomTypePie.clear();
        if (data != null) {
            data.forEach(r -> roomTypePie.setValue(r.getRoomTypeName(), r.getRevenue()));
        }
    }

    public void setServiceRevenueData(List<ReportServiceRevenueItem> data) {
        servicePie.clear();
        if (data != null) {
            data.forEach(s -> servicePie.setValue(s.getServiceName(), s.getRevenue()));
        }
    }

    public void setGuestMixData(List<ReportGuestMix> data) {
        guestMixPie.clear();
        if (data != null) {
            long newGuests = data.stream().mapToLong(ReportGuestMix::getNewGuests).sum();
            long returningGuests = data.stream().mapToLong(ReportGuestMix::getReturningGuests).sum();
            guestMixPie.setValue("Khách mới", newGuests);
            guestMixPie.setValue("Khách quay lại", returningGuests);
        }
    }

    public void setOccupancyData(List<ReportOccupancyPoint> data) {
        occDataset.clear();
        if (data != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM");
            data.forEach(p -> occDataset.addValue(
                    p.getOccupancyPct(),
                    "Công suất",
                    p.getDate().format(df)));
        }
    }

    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private String formatCurrency(Number value) {
        if (value == null)
            return "-";
        double val = value.doubleValue();
        if (val >= 1_000_000_000) {
            return String.format("%.1f tỷ", val / 1_000_000_000);
        } else if (val >= 1_000_000) {
            return String.format("%.1f tr", val / 1_000_000);
        }
        return String.format("%,d", value.longValue());
    }
}
