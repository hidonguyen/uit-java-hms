package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.util.function.BiConsumer;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

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

    private Runnable searchHandler;
    private Runnable resetHandler;
    private Runnable prevHandler;
    private Runnable nextHandler;
    private BiConsumer<String, String> sortHandler;

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
        return "Search booking no / guest / room";
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

        statusBox = new JComboBox<>(new String[] { "", "CheckedIn", "CheckedOut", "Cancelled" });
        payStatusBox = new JComboBox<>(new String[] { "", "Paid", "Unpaid" });
        chargeBox = new JComboBox<>(new String[] { "", "Hourly", "Daily" });
        sortByBox = new JComboBox<>(
                new String[] { "checkin", "checkout", "booking_no", "status", "payment_status", "charge_type" });
        sortDirBox = new JComboBox<>(new String[] { "DESC", "ASC" });

        resetBtn = createModernButton("Reset", PRIMARY_COLOR, Color.WHITE);
        prevBtn = createModernButton("Prev", PRIMARY_COLOR, Color.WHITE);
        nextBtn = createModernButton("Next", PRIMARY_COLOR, Color.WHITE);

        loadingLabel = new JLabel("Loading...");
        loadingLabel.setVisible(false);

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filters.setBackground(Color.WHITE);
        filters.add(new JLabel("Status:"));
        filters.add(statusBox);
        filters.add(new JLabel("Payment:"));
        filters.add(payStatusBox);
        filters.add(new JLabel("Charge:"));
        filters.add(chargeBox);
        filters.add(new JLabel("Sort:"));
        filters.add(sortByBox);
        filters.add(sortDirBox);

        actionPanel.add(Box.createHorizontalStrut(8));
        actionPanel.add(filters);
        actionPanel.add(Box.createHorizontalStrut(8));
        actionPanel.add(prevBtn);
        actionPanel.add(Box.createHorizontalStrut(8));
        actionPanel.add(nextBtn);
        actionPanel.add(Box.createHorizontalStrut(8));
        actionPanel.add(resetBtn);
        actionPanel.add(Box.createHorizontalStrut(12));
        actionPanel.add(loadingLabel);

        searchBtn.addActionListener(e -> {
            if (searchHandler != null)
                searchHandler.run();
        });
        searchField.addActionListener(e -> {
            if (searchHandler != null)
                searchHandler.run();
        });
        resetBtn.addActionListener(e -> {
            if (resetHandler != null)
                resetHandler.run();
        });
        prevBtn.addActionListener(e -> {
            if (prevHandler != null)
                prevHandler.run();
        });
        nextBtn.addActionListener(e -> {
            if (nextHandler != null)
                nextHandler.run();
        });
        sortByBox.addActionListener(e -> {
            if (sortHandler != null)
                sortHandler.accept(getSortBy(), getSortDir());
        });
        sortDirBox.addActionListener(e -> {
            if (sortHandler != null)
                sortHandler.accept(getSortBy(), getSortDir());
        });
    }

    @Override
    public BookingHistoryFilter getFilter() {
        BookingHistoryFilter f = new BookingHistoryFilter();
        String q = getSearchQuery();
        if (q != null && !q.isBlank())
            f.setKeyword(q);
        Object s;
        s = statusBox.getSelectedItem();
        if (s != null)
            f.setStatus(s.toString());
        s = payStatusBox.getSelectedItem();
        if (s != null)
            f.setPaymentStatus(s.toString());
        s = chargeBox.getSelectedItem();
        if (s != null)
            f.setChargeType(s.toString());
        return f;
    }

    @Override
    public String getSortBy() {
        Object v = sortByBox.getSelectedItem();
        return v == null ? "checkin" : v.toString();
    }

    @Override
    public String getSortDir() {
        Object v = sortDirBox.getSelectedItem();
        return v == null ? "DESC" : v.toString();
    }

    @Override
    public int getPageSize() {
        return 20;
    }

    @Override
    public void setLoading(boolean v) {
        loadingLabel.setVisible(v);
    }

    @Override
    public void setTableModel(AbstractTableModel model) {
        super.setTableModel(model);
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
    }
}
