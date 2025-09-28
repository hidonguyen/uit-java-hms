package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBooking;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Booking;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IBookingDetailRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IBookingRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IBookingService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.LoggedInUser;

public class BookingService implements IBookingService {
    private final IBookingRepository bookingRepository;
    private final IBookingDetailRepository bookingDetailRepository;

    public BookingService(IBookingRepository bookingRepository, IBookingDetailRepository bookingDetailRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingDetailRepository = bookingDetailRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Integer create(Booking booking) {
        booking.setId(null);
        booking.setCreatedAt(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        booking.setCreatedBy(LoggedInUser.ID);
        booking.setUpdatedAt(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        booking.setUpdatedBy(LoggedInUser.ID);
        return bookingRepository.insert(booking);
    }

    @Override
    public Integer update(Booking booking) {
        booking.setUpdatedAt(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC));
        booking.setUpdatedBy(LoggedInUser.ID);
        return bookingRepository.update(booking);
    }

    @Override
    public Integer delete(Long id) {
        return bookingRepository.delete(id);
    }

    @Override
    public ArrayList<TodayBooking> findTodayBookings() {
        return bookingRepository.findTodayBookings();
    }
}
