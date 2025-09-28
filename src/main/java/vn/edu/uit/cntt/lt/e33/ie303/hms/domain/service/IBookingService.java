package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service;

import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBooking;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Booking;

public interface IBookingService {
    List<Booking> findAll();

    Booking findById(Long id);

    Integer create(Booking booking);

    Integer update(Booking booking);

    Integer delete(Long id);

    ArrayList<TodayBooking> findTodayBookings();
}
