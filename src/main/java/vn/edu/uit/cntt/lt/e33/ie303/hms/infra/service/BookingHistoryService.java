package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistory;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.bookingHistory.BookingHistoryFilter;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IBookingHistoryRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IBookingHistoryService;

public class BookingHistoryService implements IBookingHistoryService {
    private final IBookingHistoryRepository repo;

    public BookingHistoryService(IBookingHistoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<BookingHistory> search(BookingHistoryFilter filter, int limit, int offset, String sortBy,
            String sortDir) {
        return repo.search(filter, limit, offset, sortBy, sortDir);
    }

    @Override
    public long count(BookingHistoryFilter filter) {
        return repo.count(filter);
    }
}
