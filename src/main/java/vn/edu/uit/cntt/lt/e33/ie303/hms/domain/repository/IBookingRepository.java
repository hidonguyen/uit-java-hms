package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Booking;

public interface IBookingRepository {
    List<Booking> findAll();
    Booking findById(Long id);
    int insert(Booking booking);
    int update(Booking booking);
    int delete(Long id);
    ArrayList<TodayBookingDto> findTodayBookings();
}
