package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.GuestItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.RoomItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.ServiceItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingDetailType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Booking;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IGuestRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IRoomRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IRoomTypeRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IServiceRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IBookingService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking.TodayBookingView;

public class BookingPresenter {
    private final TodayBookingView todayBookingView;
    private final IBookingService bookingService;
    private final IGuestRepository guestRepository;
    private final IRoomRepository roomRepository;
    private final IRoomTypeRepository roomTypeRepository;
    private final IServiceRepository serviceRepository;

    public BookingPresenter(JFrame parentFrame) {
        this.todayBookingView = new TodayBookingView();
        this.bookingService = DIContainer.getInstance().getBookingService();
        this.guestRepository = DIContainer.getInstance().getGuestRepository();
        this.roomRepository = DIContainer.getInstance().getRoomRepository();
        this.roomTypeRepository = DIContainer.getInstance().getRoomTypeRepository();
        this.serviceRepository = DIContainer.getInstance().getServiceRepository();
        this.todayBookingView.setPresenter(this);
    }

    public TodayBookingView loadTodayBookingView() {
        ArrayList<TodayBookingDto> bookings = bookingService.findTodayBookings();
        todayBookingView.setBookings(bookings);

        return todayBookingView;
    }

    public TodayBookingView getTodayBookingView() { return todayBookingView; }

    public void onBookingCardClicked(Long bookingId) {
        Booking booking = bookingService.findById(bookingId);
        if (booking != null) {
            BookingDto bookingDto = new BookingDto();
            // map Booking to BookingDto
            bookingDto.setId(booking.getId());
            bookingDto.setBookingNo(booking.getBookingNo());
            bookingDto.setChargeType(booking.getChargeType());
            bookingDto.setRoomId(booking.getRoomId());
            Room room = roomRepository.findById(booking.getRoomId());
            if (room != null) {
                bookingDto.setRoomName(room.getName());
            }
            bookingDto.setRoomTypeId(booking.getRoomTypeId());
            RoomType roomType = roomTypeRepository.findById(booking.getRoomTypeId());
            if (roomType != null) {
                bookingDto.setRoomTypeName(roomType.getName());
            }
            bookingDto.setPrimaryGuestId(booking.getPrimaryGuestId());
            Guest primaryGuest = guestRepository.findById(booking.getPrimaryGuestId());
            if (primaryGuest != null) {
                bookingDto.setPrimaryGuestName(primaryGuest.getName());
                bookingDto.setPrimaryGuestPhone(primaryGuest.getPhone());
                bookingDto.setPrimaryGuestEmail(primaryGuest.getEmail());
            }
            bookingDto.setNumAdults(booking.getNumAdults());
            bookingDto.setNumChildren(booking.getNumChildren());
            bookingDto.setCheckin(booking.getCheckin());
            bookingDto.setCheckout(booking.getCheckout());
            Double totalRoomCharges = booking.getBookingDetails().stream()
                    .filter(detail -> detail.getType() == BookingDetailType.Room)
                    .mapToDouble(detail -> detail.getAmount())
                    .sum();
            bookingDto.setTotalRoomCharges(totalRoomCharges);
            Double totalServiceCharges = booking.getBookingDetails().stream()
                    .filter(detail -> detail.getType() != BookingDetailType.Room)
                    .mapToDouble(detail -> detail.getAmount())
                    .sum();
            bookingDto.setTotalServiceCharges(totalServiceCharges);
            bookingDto.setStatus(booking.getStatus());

            bookingDto.setBookingDetails(booking.getBookingDetails());

            todayBookingView.showBookingDetailDialog(bookingDto);
        }
    }

    public int saveBooking() {
        BookingDto bookingDto = todayBookingView.getCurrentBookingDto();
        
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setBookingNo(bookingDto.getBookingNo());
        booking.setChargeType(bookingDto.getChargeType());
        booking.setCheckin(bookingDto.getCheckin());
        booking.setCheckout(bookingDto.getCheckout());
        booking.setRoomId(bookingDto.getRoomId());
        booking.setRoomTypeId(bookingDto.getRoomTypeId());
        booking.setPrimaryGuestId(bookingDto.getPrimaryGuestId());
        booking.setNumAdults(bookingDto.getNumAdults());
        booking.setNumChildren(bookingDto.getNumChildren());
        booking.setStatus(bookingDto.getStatus());

        if (bookingDto.getId() == null) {
            // New booking, set default status
            booking.setStatus(BookingStatus.CheckedIn);
            return bookingService.create(booking);
        }
        return bookingService.update(booking);
    }

    public int checkOutBooking() {
        BookingDto bookingDto = todayBookingView.getCurrentBookingDto();

        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setBookingNo(bookingDto.getBookingNo());
        booking.setChargeType(bookingDto.getChargeType());
        booking.setCheckin(bookingDto.getCheckin());
        booking.setCheckout(bookingDto.getCheckout());
        booking.setRoomId(bookingDto.getRoomId());
        booking.setRoomTypeId(bookingDto.getRoomTypeId());
        booking.setPrimaryGuestId(bookingDto.getPrimaryGuestId());
        booking.setNumAdults(bookingDto.getNumAdults());
        booking.setNumChildren(bookingDto.getNumChildren());
        booking.setStatus(BookingStatus.CheckedOut);

        return bookingService.update(booking);
    }

    public int addServiceToBooking(Date value, String selectedItem, String trim, ServiceItem selectedItem2,
            String trim2) {
        Long bookingId = todayBookingView.getCurrentBookingDto().getId();


        return 0;
    }

    public JComboBox<RoomItem> getRoomSelectionComboBox() {
        List<Room> rooms = roomRepository.findAll();
        JComboBox<RoomItem> roomComboBox = new JComboBox<>();
        roomComboBox.addItem(new RoomItem(-1L, "Select Room", ""));
        for (Room room : rooms) {
            RoomType roomType = roomTypeRepository.findById(room.getRoomTypeId());
            roomComboBox.addItem(new RoomItem(room.getId(), room.getName(), roomType.getName()));
        }
        return roomComboBox;
    }

    public JComboBox<GuestItem> getGuestSelectionComboBox() {
        List<Guest> guests = guestRepository.findAll();
        JComboBox<GuestItem> guestComboBox = new JComboBox<>();
        guestComboBox.addItem(new GuestItem(-1L, "Select Guest", ""));
        for (Guest guest : guests) {
            guestComboBox.addItem(new GuestItem(guest.getId(), guest.getName(), guest.getPhone()));
        }
        return guestComboBox;
    }

    public JComboBox<ServiceItem> getServiceSelectionComboBox() {
        List<Service> services = serviceRepository.findAll();
        JComboBox<ServiceItem> serviceComboBox = new JComboBox<>();
        serviceComboBox.addItem(new ServiceItem(-1L, "Select Service", 0.0));
        for (Service service : services) {
            serviceComboBox.addItem(new ServiceItem(service.getId(), service.getName(), service.getPrice()));
        }
        return serviceComboBox;
    }
}