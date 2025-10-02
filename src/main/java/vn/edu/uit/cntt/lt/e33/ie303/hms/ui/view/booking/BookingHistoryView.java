package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingChargeType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.PaymentStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistoryFilter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.common.BaseTableView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter.BookingHistoryPresenter;

public class BookingHistoryView extends BaseTableView implements BookingHistoryPresenter.View {
    private JComboBox<String> statusBox;
    private JComboBox<String> payStatusBox;
    private JComboBox<String> chargeBox;
    private JComboBox<String> sortByBox;
    private JComboBox<String> sortDirBox;
    private JButton resetBtn;
    private JButton prevBtn;
    private JButton nextBtn;
    private JLabel loadingLabel;
    private JLabel pageInfoLabel;

    private Runnable searchHandler;
    private Runnable resetHandler;
    private Runnable prevHandler;
    private Runnable nextHandler;
    private BiConsumer<String, String> sortHandler;

    private int currentPage = 0;
    private int totalRecords = 0;

    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color BORDER_COLOR = new Color(189, 195, 199);

    public BookingHistoryView() {
        super();
    }

    @Override
    protected String getModuleName() {
        return "Booking History";
    }

    @Override
    protected String getAddButtonText() {
        return "Add";
    }

    @Override
    protected String getSearchPlaceholder() {
        return "Search booking no, guest, room...";
    }

    @Override
    protected String getErrorTitle() {
        return "Booking History";
    }

    @Override
    protected void onInitExtraActions(JPanel actionPanel) {
        addBtn.setVisible(false);
        editBtn.setVisible(false);
        deleteBtn.setVisible(false);

        actionPanel.removeAll();
        actionPanel.setLayout(new BorderLayout(10, 10));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JPanel filterPanel = createFilterPanel();
        JPanel controlPanel = createControlPanel();

        actionPanel.add(filterPanel, BorderLayout.NORTH);
        actionPanel.add(controlPanel, BorderLayout.CENTER);

        wireActionListeners();
    }

    private JPanel createFilterPanel() {
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        mainPanel.setBackground(Color.WHITE);

        String[] statusOptions = new String[BookingStatus.values().length + 1];
        statusOptions[0] = "All Status";
        for (int i = 0; i < BookingStatus.values().length; i++) {
            statusOptions[i + 1] = BookingStatus.values()[i].name();
        }
        statusBox = createStyledComboBox(statusOptions, 120);

        String[] paymentOptions = new String[PaymentStatus.values().length + 1];
        paymentOptions[0] = "All Payment";
        for (int i = 0; i < PaymentStatus.values().length; i++) {
            paymentOptions[i + 1] = PaymentStatus.values()[i].name();
        }
        payStatusBox = createStyledComboBox(paymentOptions, 120);

        String[] chargeOptions = new String[BookingChargeType.values().length + 1];
        chargeOptions[0] = "All Charge";
        for (int i = 0; i < BookingChargeType.values().length; i++) {
            chargeOptions[i + 1] = BookingChargeType.values()[i].name();
        }
        chargeBox = createStyledComboBox(chargeOptions, 120);

        sortByBox = createStyledComboBox(new String[] {
                "Check-in", "Check-out", "Booking No", "Status", "Payment", "Charge"
        }, 120);
        sortDirBox = createStyledComboBox(new String[] { "DESC", "ASC" }, 80);

        mainPanel.add(createCompactLabel("Status:"));
        mainPanel.add(statusBox);
        mainPanel.add(createCompactLabel("Payment:"));
        mainPanel.add(payStatusBox);
        mainPanel.add(createCompactLabel("Charge:"));
        mainPanel.add(chargeBox);
        mainPanel.add(createCompactLabel("Sort By:"));
        mainPanel.add(sortByBox);
        mainPanel.add(createCompactLabel("Order:"));
        mainPanel.add(sortDirBox);

        return mainPanel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setBackground(Color.WHITE);

        resetBtn = createIconButton("Reset", PRIMARY_COLOR);
        prevBtn = createIconButton("Prev", SUCCESS_COLOR);
        nextBtn = createIconButton("Next", SUCCESS_COLOR);

        leftPanel.add(resetBtn);
        leftPanel.add(prevBtn);
        leftPanel.add(nextBtn);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(Color.WHITE);

        loadingLabel = new JLabel("Loading...");
        loadingLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        loadingLabel.setForeground(WARNING_COLOR);
        loadingLabel.setVisible(false);

        pageInfoLabel = new JLabel("Page 1");
        pageInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        pageInfoLabel.setForeground(new Color(127, 140, 141));

        rightPanel.add(pageInfoLabel);
        rightPanel.add(loadingLabel);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JLabel createCompactLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private JComboBox<String> createStyledComboBox(String[] items, int width) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setPreferredSize(new Dimension(width, 28));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(2, 6, 2, 6)));
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return combo;
    }

    private JButton createIconButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 32));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color original = color;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(original);
            }
        });

        return btn;
    }

    private void wireActionListeners() {
        searchBtn.addActionListener(e -> {
            if (searchHandler != null) {
                currentPage = 0;
                searchHandler.run();
            }
        });

        searchField.addActionListener(e -> {
            if (searchHandler != null) {
                currentPage = 0;
                searchHandler.run();
            }
        });

        resetBtn.addActionListener(e -> {
            if (resetHandler != null) {
                statusBox.setSelectedIndex(0);
                payStatusBox.setSelectedIndex(0);
                chargeBox.setSelectedIndex(0);
                sortByBox.setSelectedIndex(0);
                sortDirBox.setSelectedIndex(0);
                searchField.setText("");
                currentPage = 0;
                resetHandler.run();
            }
        });

        prevBtn.addActionListener(e -> {
            if (prevHandler != null) {
                currentPage = Math.max(0, currentPage - 1);
                prevHandler.run();
            }
        });

        nextBtn.addActionListener(e -> {
            if (nextHandler != null) {
                currentPage++;
                nextHandler.run();
            }
        });

        sortByBox.addActionListener(e -> {
            if (sortHandler != null) {
                currentPage = 0;
                sortHandler.accept(getSortBy(), getSortDir());
            }
        });

        sortDirBox.addActionListener(e -> {
            if (sortHandler != null) {
                currentPage = 0;
                sortHandler.accept(getSortBy(), getSortDir());
            }
        });

        statusBox.addActionListener(e -> {
            if (searchHandler != null) {
                currentPage = 0;
                searchHandler.run();
            }
        });

        payStatusBox.addActionListener(e -> {
            if (searchHandler != null) {
                currentPage = 0;
                searchHandler.run();
            }
        });

        chargeBox.addActionListener(e -> {
            if (searchHandler != null) {
                currentPage = 0;
                searchHandler.run();
            }
        });
    }

    @Override
    public BookingHistoryFilter getFilter() {
        BookingHistoryFilter f = new BookingHistoryFilter();

        String q = getSearchQuery();
        if (q != null && !q.isBlank()) {
            f.setKeyword(q);
        }

        String status = (String) statusBox.getSelectedItem();
        if (status != null && !status.startsWith("All")) {
            f.setStatus(status);
        }

        String payStatus = (String) payStatusBox.getSelectedItem();
        if (payStatus != null && !payStatus.startsWith("All")) {
            f.setPaymentStatus(payStatus);
        }

        String charge = (String) chargeBox.getSelectedItem();
        if (charge != null && !charge.startsWith("All")) {
            f.setChargeType(charge);
        }

        return f;
    }

    @Override
    public String getSortBy() {
        int idx = sortByBox.getSelectedIndex();
        return switch (idx) {
            case 0 -> "checkin";
            case 1 -> "checkout";
            case 2 -> "booking_no";
            case 3 -> "status";
            case 4 -> "payment_status";
            case 5 -> "charge_type";
            default -> "checkin";
        };
    }

    @Override
    public String getSortDir() {
        int idx = sortDirBox.getSelectedIndex();
        return idx == 1 ? "ASC" : "DESC";
    }

    @Override
    public int getPageSize() {
        return 20;
    }

    @Override
    public void setLoading(boolean v) {
        loadingLabel.setVisible(v);
        prevBtn.setEnabled(!v);
        nextBtn.setEnabled(!v);
        resetBtn.setEnabled(!v);
    }

    @Override
    public void setTableModel(AbstractTableModel model) {
        super.setTableModel(model);
        updatePageDisplay();
    }

    @Override
    public void onSearch(Runnable r) {
        this.searchHandler = r;
    }

    @Override
    public void onReset(Runnable r) {
        this.resetHandler = r;
    }

    @Override
    public void onPrev(Runnable r) {
        this.prevHandler = r;
    }

    @Override
    public void onNext(Runnable r) {
        this.nextHandler = r;
    }

    @Override
    public void onSort(BiConsumer<String, String> handler) {
        this.sortHandler = handler;
    }

    @Override
    public void setCursor(Cursor cursor) {
        super.setCursor(cursor);
        if (table != null) {
            table.setCursor(cursor);
        }
    }

    public void setCurrentPage(int page) {
        this.currentPage = page;
        updatePageDisplay();
    }

    public void setTotalRecords(int total) {
        this.totalRecords = total;
        updatePageDisplay();
    }

    private void updatePageDisplay() {
        int totalPages = (int) Math.ceil((double) totalRecords / getPageSize());
        if (totalPages == 0)
            totalPages = 1;
        pageInfoLabel.setText(String.format("Page %d of %d", currentPage + 1, totalPages));

        prevBtn.setEnabled(currentPage > 0);
        nextBtn.setEnabled(currentPage < totalPages - 1);
    }
}