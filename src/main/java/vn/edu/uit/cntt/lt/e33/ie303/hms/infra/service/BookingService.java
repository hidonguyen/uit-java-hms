package vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Booking;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.BookingDetail;
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
        Booking booking = bookingRepository.findById(id);
        booking.setBookingDetails(bookingDetailRepository.findAllByBookingId(id));
        return booking;
    }

    @Override
    public Integer create(Booking booking) {
        booking.setId(null);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setCreatedBy(LoggedInUser.ID);
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setUpdatedBy(LoggedInUser.ID);
        return bookingRepository.insert(booking);
    }

    @Override
    public Integer update(Booking booking) {
        List<BookingDetail> existingDetails = bookingDetailRepository.findAllByBookingId(booking.getId());
        List<Long> deletingIds = new ArrayList<>();
        for (BookingDetail existingDetail : existingDetails) {
            boolean exists = false;
            for (BookingDetail detail : booking.getBookingDetails()) {
                if (existingDetail.getId().equals(detail.getId())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                deletingIds.add(existingDetail.getId());
            }
        }
        deletingIds.forEach(id -> bookingDetailRepository.delete(id));

        booking.getBookingDetails().forEach(detail -> {
            if (detail.getServiceId() == null || detail.getServiceId() <= 0) {
                detail.setServiceId(null);
            }

            if (detail.getId() == null) {
                detail.setCreatedAt(LocalDateTime.now());
                detail.setCreatedBy(LoggedInUser.ID);
                bookingDetailRepository.insert(detail);
            } else {
                detail.setCreatedAt(LocalDateTime.now());
                detail.setCreatedBy(LoggedInUser.ID);
                detail.setUpdatedAt(LocalDateTime.now());
                detail.setUpdatedBy(LoggedInUser.ID);
                bookingDetailRepository.update(detail);
            }
        });

        booking.setUpdatedAt(LocalDateTime.now());
        booking.setUpdatedBy(LoggedInUser.ID);
        return bookingRepository.update(booking);
    }

    @Override
    public Integer delete(Long id) {
        return bookingRepository.delete(id);
    }

    @Override
    public List<TodayBookingDto> findTodayBookings() {
        return bookingRepository.findTodayBookings();
    }

    @Override
    public String generateBookingNo() {
        String prefix = "BKG";
        String datePart = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyMMdd"));
        String lastBookingNo = bookingRepository.findLastBookingNo(prefix + datePart + "%");
        int nextSequence = 1;
        if (lastBookingNo != null && lastBookingNo.length() == 12) {
            String lastSequenceStr = lastBookingNo.substring(9);
            try {
                int lastSequence = Integer.parseInt(lastSequenceStr);
                nextSequence = lastSequence + 1;
            } catch (NumberFormatException e) {
                // Ignore and use nextSequence = 1 as default
                nextSequence = 1;
            }
        }

        return prefix + datePart + String.format("%03d", nextSequence);
    }
}