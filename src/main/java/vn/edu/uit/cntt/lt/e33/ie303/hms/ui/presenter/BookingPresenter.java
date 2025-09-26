package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import javax.swing.JFrame;

import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking.TodayBookingView;

public class BookingPresenter {
    private final TodayBookingView todayBookingView;

    public BookingPresenter(JFrame parentFrame) {
        this.todayBookingView = new TodayBookingView();
    }

    public TodayBookingView getTodayBookingView() { return todayBookingView; }
}