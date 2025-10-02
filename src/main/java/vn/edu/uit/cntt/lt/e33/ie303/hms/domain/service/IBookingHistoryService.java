package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import java.util.List;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistory;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistoryFilter;

public interface IBookingHistoryService {
    List<BookingHistory> search(BookingHistoryFilter filter, int limit, int offset, String sortBy, String sortDir);

    long count(BookingHistoryFilter filter);
}
