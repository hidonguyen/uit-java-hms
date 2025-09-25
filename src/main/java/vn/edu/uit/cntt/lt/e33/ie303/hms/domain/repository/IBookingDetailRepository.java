package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.BookingDetail;

public interface IBookingDetailRepository {
    List<BookingDetail> findAll();
    BookingDetail findById(Long id);
    int insert(BookingDetail bookingDetail);
    int update(BookingDetail bookingDetail);
    int delete(Long id);
}
