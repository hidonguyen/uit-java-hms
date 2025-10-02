package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.awt.Cursor;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;

import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistory;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistoryFilter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IBookingHistoryService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking.BookingHistoryView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class BookingHistoryPresenter {
    public interface View {
        void onSearch(Runnable r);

        void onReset(Runnable r);

        void onPrev(Runnable r);

        void onNext(Runnable r);

        void onSort(BiConsumer<String, String> handler);

        void setTableModel(AbstractTableModel model);

        void showErrorMessage(String msg);

        void showSuccessMessage(String msg);

        void setLoading(boolean v);

        BookingHistoryFilter getFilter();

        String getSortBy();

        String getSortDir();

        int getPageSize();

        void setCursor(java.awt.Cursor cursor);
    }

    private final IBookingHistoryService service;
    private final BookingHistoryView view;
    private List<BookingHistory> rows = java.util.List.of();
    private int page = 0;
    private String sortBy = "checkin";
    private String sortDir = "DESC";
    private long totalRecords = 0;

    public BookingHistoryPresenter() {
        this.service = Objects.requireNonNull(DIContainer.getInstance().getBookingHistoryService());
        this.view = new BookingHistoryView();
        wireEvents();
        loadData();
    }

    public BookingHistoryView getView() {
        return view;
    }

    private void wireEvents() {
        view.onSearch(this::onSearch);
        view.onReset(this::onReset);
        view.onPrev(this::onPrev);
        view.onNext(this::onNext);
        view.onSort((sb, sd) -> {
            sortBy = sb != null && !sb.isBlank() ? sb : "checkin";
            sortDir = "ASC".equalsIgnoreCase(sd) ? "ASC" : "DESC";
            page = 0;
            loadData();
        });
    }

    private void onSearch() {
        page = 0;
        loadData();
    }

    private void onReset() {
        page = 0;
        sortBy = "checkin";
        sortDir = "DESC";
        view.setTableModel(new BookingHistoryTableModel(java.util.List.of()));
        loadData();
    }

    private void onPrev() {
        if (page > 0) {
            page--;
            loadData();
        }
    }

    private void onNext() {
        page++;
        loadData();
    }

    private void loadData() {
        BookingHistoryFilter filter = view.getFilter();
        String vSortBy = view.getSortBy() != null && !view.getSortBy().isBlank() ? view.getSortBy() : sortBy;
        String vSortDir = view.getSortDir() != null && !view.getSortDir().isBlank() ? view.getSortDir() : sortDir;
        int size = Math.max(1, view.getPageSize());
        int offset = page * size;

        view.setLoading(true);
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new SwingWorker<java.util.List<BookingHistory>, Void>() {
            long total;

            @Override
            protected java.util.List<BookingHistory> doInBackground() {
                total = service.count(filter);
                if (offset >= total && total > 0) {
                    page = Math.max(0, (int) ((total - 1) / size));
                }
                int realOffset = page * size;
                return service.search(filter, size, realOffset, vSortBy, vSortDir);
            }

            @Override
            protected void done() {
                try {
                    rows = get();
                    totalRecords = total;
                    view.setTableModel(new BookingHistoryTableModel(rows));
                    view.setCurrentPage(page);
                    view.setTotalRecords((int) totalRecords);
                } catch (Exception e) {
                    view.showErrorMessage(e.getMessage());
                } finally {
                    view.setLoading(false);
                    view.setCursor(Cursor.getDefaultCursor());
                }
            }
        }.execute();
    }

    static class BookingHistoryTableModel extends AbstractTableModel {
        private final String[] cols = {
                "Id", "Booking No", "Guest", "Room", "Room Type",
                "Check-in", "Check-out", "Charge", "Status", "Payment",
                "Adults", "Children", "Total", "Paid", "Balance"
        };
        private final List<BookingHistory> data;
        private final DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern(Constants.DateTimeFormat.ddMMyyyyHHmm);
        private final NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        BookingHistoryTableModel(List<BookingHistory> data) {
            this.data = data != null ? data : java.util.List.of();
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return cols.length;
        }

        @Override
        public String getColumnName(int c) {
            return cols[c];
        }

        @Override
        public Object getValueAt(int r, int c) {
            BookingHistory o = data.get(r);
            return switch (c) {
                case 0 -> o.getId();
                case 1 -> o.getBookingNo();
                case 2 -> o.getPrimaryGuestName();
                case 3 -> o.getRoomName();
                case 4 -> o.getRoomTypeName();
                case 5 -> o.getCheckin() == null ? null : o.getCheckin().format(dtFmt);
                case 6 -> o.getCheckout() == null ? null : o.getCheckout().format(dtFmt);
                case 7 -> o.getChargeType();
                case 8 -> o.getStatus();
                case 9 -> o.getPaymentStatus();
                case 10 -> o.getNumAdults();
                case 11 -> o.getNumChildren();
                case 12 -> o.getTotalAmount() == null ? null : money.format(o.getTotalAmount());
                case 13 -> o.getPaidAmount() == null ? null : money.format(o.getPaidAmount());
                case 14 -> o.getBalance() == null ? null : money.format(o.getBalance());
                default -> "";
            };
        }
    }
}