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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportBookingCountPoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportDateRangeParams;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportGuestMix;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportKpiSummary;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRevenueByRoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportRoomRevenuePoint;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.report.ReportServiceRevenueItem;

public class ReportsView extends JPanel {
    private static final Color PRIMARY = new Color(79, 70, 229);
    private static final Color SECONDARY = new Color(236, 72, 153);
    private static final Color SUCCESS = new Color(34, 197, 94);
    private static final Color INFO = new Color(59, 130, 246);
    private static final Color BG_LIGHT = new Color(249, 250, 251);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color[] CHART_COLORS = {
            new Color(99, 102, 241), new Color(236, 72, 153),
            new Color(34, 197, 94), new Color(251, 146, 60),
            new Color(59, 130, 246), new Color(168, 85, 247)
    };

    private final JComboBox<String> quickRange;
    private final DatePicker fromPicker;
    private final DatePicker toPicker;

    private final JComboBox<String> granularity;
    private final JButton applyBtn;

    private final JLabel kpiRevenue;
    private final JLabel kpiRoomRevenue;
    private final JLabel kpiServiceRevenue;
    private final JLabel kpiGuests;

    private final DefaultCategoryDataset revenueDataset;
    private final DefaultCategoryDataset bookingCountDataset;
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
        fromPicker = (DatePicker) getComponentByName(toolbar, "fromPicker");
        toPicker = (DatePicker) getComponentByName(toolbar, "toPicker");
        granularity = (JComboBox<String>) getComponentByName(toolbar, "granularity");
        applyBtn = (JButton) getComponentByName(toolbar, "applyBtn");

        JPanel kpiPanel = createKpiPanel();
        kpiRevenue = (JLabel) getComponentByName(kpiPanel, "kpiRevenue");
        kpiRoomRevenue = (JLabel) getComponentByName(kpiPanel, "kpiRoomRevenue");
        kpiServiceRevenue = (JLabel) getComponentByName(kpiPanel, "kpiServiceRevenue");
        kpiGuests = (JLabel) getComponentByName(kpiPanel, "kpiGuests");

        revenueDataset = new DefaultCategoryDataset();
        bookingCountDataset = new DefaultCategoryDataset();
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
                "Today", "7 days", "30 days", "This month", "This year"
        });
        qr.setName("quickRange");
        styleComboBox(qr);

        DatePicker fromDatePicker = createStyledDatePicker();
        DatePicker toDatePicker = createStyledDatePicker();
        fromDatePicker.setName("fromPicker");
        toDatePicker.setName("toPicker");

        fromDatePicker.setDate(LocalDate.now().minusDays(7));
        toDatePicker.setDate(LocalDate.now());

        JComboBox<String> gr = new JComboBox<>(new String[] { "DAY", "WEEK", "MONTH", "YEAR" });
        gr.setName("granularity");
        styleComboBox(gr);

        JButton btn = new JButton("Apply");
        btn.setName("applyBtn");
        styleButton(btn);

        toolbar.add(createLabel("Quick:"));
        toolbar.add(qr);
        toolbar.add(Box.createHorizontalStrut(8));
        toolbar.add(createLabel("From:"));
        toolbar.add(fromDatePicker);
        toolbar.add(createLabel("To:"));
        toolbar.add(toDatePicker);
        toolbar.add(Box.createHorizontalStrut(8));
        toolbar.add(createLabel("Granularity:"));
        toolbar.add(gr);
        toolbar.add(Box.createHorizontalStrut(8));
        toolbar.add(btn);

        return toolbar;
    }

    private DatePicker createStyledDatePicker() {
        DatePickerSettings settings = new DatePickerSettings();

        settings.setFormatForDatesCommonEra("dd/MM/yyyy");
        settings.setTranslationToday("Today");
        settings.setTranslationClear("Clear");

        Font regularFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 12);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 14);

        settings.setFontValidDate(regularFont);
        settings.setFontInvalidDate(regularFont);
        settings.setFontVetoedDate(regularFont);
        settings.setFontCalendarDateLabels(regularFont);
        settings.setFontCalendarWeekdayLabels(boldFont);
        settings.setFontCalendarWeekNumberLabels(regularFont);
        settings.setFontMonthAndYearMenuLabels(titleFont);
        settings.setFontMonthAndYearNavigationButtons(new Font("Segoe UI", Font.BOLD, 16));

        settings.setColor(DatePickerSettings.DateArea.BackgroundOverallCalendarPanel, Color.WHITE);
        settings.setColor(DatePickerSettings.DateArea.BackgroundMonthAndYearMenuLabels, Color.WHITE);
        settings.setColor(DatePickerSettings.DateArea.BackgroundTopLeftLabelAboveWeekNumbers, Color.WHITE);
        settings.setColor(DatePickerSettings.DateArea.BackgroundCalendarPanelLabelsOnHover, new Color(243, 244, 246));
        settings.setColor(DatePickerSettings.DateArea.CalendarBackgroundSelectedDate, PRIMARY);
        settings.setColor(DatePickerSettings.DateArea.CalendarBorderSelectedDate, PRIMARY);
        settings.setColor(DatePickerSettings.DateArea.BackgroundTodayLabel, new Color(243, 244, 246));
        settings.setColor(DatePickerSettings.DateArea.BackgroundClearLabel, new Color(243, 244, 246));
        settings.setColor(DatePickerSettings.DateArea.CalendarDefaultBackgroundHighlightedDates,
                new Color(224, 231, 255));
        settings.setColor(DatePickerSettings.DateArea.CalendarDefaultTextHighlightedDates, PRIMARY);

        DatePicker datePicker = new DatePicker(settings);

        datePicker.getComponentDateTextField().setFont(regularFont);
        datePicker.getComponentDateTextField().setPreferredSize(new Dimension(140, 36));
        datePicker.getComponentDateTextField().setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        datePicker.getComponentDateTextField().setBackground(Color.WHITE);
        datePicker.getComponentDateTextField().setForeground(new Color(31, 41, 55));

        datePicker.getComponentToggleCalendarButton().setBackground(PRIMARY);
        datePicker.getComponentToggleCalendarButton().setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY, 1, true),
                        BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        datePicker.getComponentToggleCalendarButton().setFocusPainted(false);
        datePicker.getComponentToggleCalendarButton().setText("▼");
        datePicker.getComponentToggleCalendarButton().setForeground(Color.WHITE);
        datePicker.getComponentToggleCalendarButton().setCursor(new Cursor(Cursor.HAND_CURSOR));

        datePicker.getComponentToggleCalendarButton().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datePicker.getComponentToggleCalendarButton().setBackground(
                        new Color(67, 56, 202));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                datePicker.getComponentToggleCalendarButton().setBackground(PRIMARY);
            }
        });

        return datePicker;
    }

    private void applyFilter() {
        if (filterHandler == null)
            return;

        LocalDate from = fromPicker.getDate();
        LocalDate to = toPicker.getDate();

        if (from == null || to == null) {
            showErrorMessage("Please select a start and end date");
            return;
        }

        if (from.isAfter(to)) {
            showErrorMessage("Start date must be before end date");
            return;
        }

        String g = (String) granularity.getSelectedItem();
        ReportDateRangeParams p = new ReportDateRangeParams(from, to, g, java.util.List.of());
        filterHandler.accept(p);
    }

    public void setFilterRange(ReportDateRangeParams p) {
        fromPicker.setDate(p.getFrom());
        toPicker.setDate(p.getTo());
        if (p.getGranularity() != null)
            granularity.setSelectedItem(p.getGranularity());
    }

    private JPanel createKpiPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 16, 0));
        panel.setBackground(BG_LIGHT);

        JLabel rev = new JLabel("-");
        JLabel room = new JLabel("-");
        JLabel service = new JLabel("-");
        JLabel guests = new JLabel("-");

        rev.setName("kpiRevenue");
        room.setName("kpiRoomRevenue");
        service.setName("kpiServiceRevenue");
        guests.setName("kpiGuests");

        panel.add(createKpiCard("Total Revenue", rev, PRIMARY));
        panel.add(createKpiCard("Room Revenue", room, SUCCESS));
        panel.add(createKpiCard("Service Revenue", service, INFO));
        panel.add(createKpiCard("Total Guests", guests, SECONDARY));

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
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabs.setBackground(BG_LIGHT);

        tabs.setBorder(BorderFactory.createEmptyBorder());
        tabs.setForeground(new Color(55, 65, 81));

        JFreeChart revenueChart = createLineChart("Revenue Over Time", "Time", "VND (Million)",
                revenueDataset);
        JFreeChart bookingChart = createBarChart("Bookings Per Day", "Time", "Number of Bookings",
                bookingCountDataset);
        JFreeChart roomTypeChart = createPieChart("Revenue By Room Type", roomTypePie);
        JFreeChart serviceChart = createPieChart("Service Revenue", servicePie);
        JFreeChart guestMixChart = createPieChart("Guest Statistics", guestMixPie);

        JPanel trendPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        trendPanel.setBackground(BG_LIGHT);
        trendPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        trendPanel.add(createChartCard(revenueChart));
        trendPanel.add(createChartCard(bookingChart));

        JPanel mixPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        mixPanel.setBackground(BG_LIGHT);
        mixPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        mixPanel.add(createChartCard(roomTypeChart));
        mixPanel.add(createChartCard(serviceChart));
        mixPanel.add(createChartCard(guestMixChart));

        tabs.addTab("Trends", trendPanel);
        tabs.addTab("Composition", mixPanel);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_LIGHT);
        wrapper.setBorder(new EmptyBorder(20, 16, 16, 16));
        wrapper.add(tabs, BorderLayout.CENTER);

        return wrapper;
    }

    private JFreeChart createLineChart(String title, String xLabel, String yLabel, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart(title, xLabel, yLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(CARD_BG);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getTitle().setPaint(new Color(31, 41, 55));

        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
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

    private JFreeChart createBarChart(String title, String xLabel, String yLabel, DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        chart.setBackgroundPaint(CARD_BG);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getTitle().setPaint(new Color(31, 41, 55));

        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setAutoRangeMinimumSize(2.0);
        double upperMargin = 0.15;
        rangeAxis.setUpperMargin(upperMargin);

        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(new Color(229, 231, 235));
        plot.setOutlineVisible(false);

        org.jfree.chart.renderer.category.BarRenderer renderer = (org.jfree.chart.renderer.category.BarRenderer) plot
                .getRenderer();
        renderer.setSeriesPaint(0, INFO);
        renderer.setDrawBarOutline(false);
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());

        return chart;
    }

    private JFreeChart createPieChart(String title, DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);

        chart.setBackgroundPaint(CARD_BG);
        chart.setBorderVisible(false);

        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));
        chart.getTitle().setPaint(new Color(17, 24, 39));
        chart.getTitle().setPadding(new RectangleInsets(4, 0, 4, 0));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setCircular(true);
        plot.setInsets(new RectangleInsets(5, 5, 5, 5));
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 240));
        plot.setLabelOutlinePaint(new Color(229, 231, 235));
        plot.setLabelOutlineStroke(new BasicStroke(1.0f));
        plot.setLabelShadowPaint(new Color(0, 0, 0, 15));
        plot.setLabelPaint(new Color(31, 41, 55));

        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{0}: {2}",
                NumberFormat.getNumberInstance(),
                new DecimalFormat("0.#%"));
        plot.setLabelGenerator(labelGenerator);

        plot.setLabelGap(0.05);
        plot.setInteriorGap(0.02);
        plot.setMaximumLabelWidth(0.22);
        plot.setLabelLinkMargin(0.015);
        plot.setLabelLinkPaint(new Color(156, 163, 175));
        plot.setLabelLinkStroke(new BasicStroke(1.2f));

        int itemCount = dataset.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            Comparable<?> key = dataset.getKey(i);
            plot.setSectionPaint(key, CHART_COLORS[i % CHART_COLORS.length]);

            plot.setSectionOutlinePaint(key, new Color(255, 255, 255, 180));
            plot.setSectionOutlineStroke(key, new BasicStroke(2.0f));
        }

        if (itemCount > 0) {
            Comparable<?> largestKey = dataset.getKey(0);
            double maxValue = dataset.getValue(0).doubleValue();

            for (int i = 1; i < itemCount; i++) {
                double value = dataset.getValue(i).doubleValue();
                if (value > maxValue) {
                    maxValue = value;
                    largestKey = dataset.getKey(i);
                }
            }
            plot.setExplodePercent(largestKey, 0.08);
        }
        LegendTitle legend = chart.getLegend();
        if (legend != null) {
            legend.setItemFont(new Font("Segoe UI", Font.PLAIN, 11));
            legend.setBackgroundPaint(new Color(249, 250, 251));
            legend.setFrame(new BlockBorder(new Color(229, 231, 235)));
            legend.setPadding(new RectangleInsets(8, 8, 8, 8));
            legend.setItemLabelPadding(new RectangleInsets(2, 4, 2, 4));
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
            case "Today" -> ReportDateRangeParams.today();
            case "30 days" -> ReportDateRangeParams.last30Days();
            case "This month" -> ReportDateRangeParams.thisMonth();
            case "This year" -> ReportDateRangeParams.thisYear();
            default -> ReportDateRangeParams.last7Days();
        };
        setFilterRange(p);
        applyFilter();
    }

    public void onFilterDateRange(Consumer<ReportDateRangeParams> handler) {
        this.filterHandler = handler;
    }

    public void setKpiSummary(ReportKpiSummary kpi) {
        kpiRevenue.setText(formatCurrency(kpi == null ? null : kpi.getTotalRevenue()));
        kpiRoomRevenue.setText(formatCurrency(kpi == null ? null : kpi.getRoomRevenue()));
        kpiServiceRevenue.setText(formatCurrency(kpi == null ? null : kpi.getServiceRevenue()));
        kpiGuests.setText(kpi == null ? "-" : String.format("%,d", kpi.getTotalGuests()));
    }

    public void setRoomRevenueData(List<ReportRoomRevenuePoint> data) {
        revenueDataset.clear();
        if (data != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM");
            data.forEach(p -> revenueDataset.addValue(
                    p.getRevenue().doubleValue() / 1_000_000,
                    "Revenue",
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
            guestMixPie.setValue("New Guests", newGuests);
            guestMixPie.setValue("Returning Guests", returningGuests);
        }
    }

    public void setBookingCountData(List<ReportBookingCountPoint> data) {
        bookingCountDataset.clear();
        if (data != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM");
            data.forEach(p -> bookingCountDataset.addValue(
                    p.getBookingCount().intValue(),
                    "Booking Count",
                    p.getDate().format(df)));
        }
    }

    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String formatCurrency(Number value) {
        if (value == null)
            return "-";
        double val = value.doubleValue();
        if (val >= 1_000_000_000) {
            return String.format("%.1f trillion", val / 1_000_000_000);
        } else if (val >= 1_000_000) {
            return String.format("%.1f million", val / 1_000_000);
        }
        return String.format("%,d", value.longValue());
    }
}
