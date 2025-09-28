package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.util.ArrayList;

import javax.swing.JFrame;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBooking;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IBookingService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking.TodayBookingView;

public class BookingPresenter {
    private final TodayBookingView todayBookingView;
    private final IBookingService bookingService;

    public BookingPresenter(JFrame parentFrame) {
        this.todayBookingView = new TodayBookingView();
        this.bookingService = DIContainer.getInstance().getBookingService();
    }

    public TodayBookingView loadTodayBookingView() {
        ArrayList<TodayBooking> bookings = bookingService.findTodayBookings();
        todayBookingView.setBookings(bookings);

        return todayBookingView;
    }

    public TodayBookingView getTodayBookingView() { return todayBookingView; }
}