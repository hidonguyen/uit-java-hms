package vn.edu.uit.cntt.lt.e33.ie303.hms.ui.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

import vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap.DIContainer;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.BookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.GuestItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.RoomItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.RoomTypeItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.ServiceItem;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.dto.TodayBookingDto;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingDetailType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.enums.BookingStatus;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Booking;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.BookingDetail;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Guest;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Room;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.RoomType;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model.Service;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IGuestRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IRoomRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IRoomTypeRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.IServiceRepository;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.IBookingService;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking.CreateOrEditBookingModal;
import vn.edu.uit.cntt.lt.e33.ie303.hms.ui.view.booking.TodayBookingView;
import vn.edu.uit.cntt.lt.e33.ie303.hms.util.Constants;

public class BookingPresenter {
    private final TodayBookingView todayBookingView;

    private final CreateOrEditBookingModal createOrEditBookingModal;

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

        this.createOrEditBookingModal = new CreateOrEditBookingModal(parentFrame, this);


        this.createOrEditBookingModal.onSave(_ -> {
            if (!createOrEditBookingModal.isValidInput())
                return;
            BookingDto bookingDto = createOrEditBookingModal.getModel();
            new SwingWorker<Integer, Void>() {
                @Override
                protected Integer doInBackground() {
                    try {
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
                        booking.setPaymentStatus(bookingDto.getPaymentStatus());

                        bookingDto.getBookingDetails().forEach(detail -> {
                            
                            BookingDetail bookingDetail = new BookingDetail();
                            bookingDetail.setId(detail.getId());
                            bookingDetail.setBookingId(detail.getBookingId());
                            bookingDetail.setIssuedAt(detail.getIssuedAt());
                            bookingDetail.setType(detail.getType());
                            bookingDetail.setServiceId(detail.getServiceId());
                            bookingDetail.setDescription(detail.getDescription());
                            bookingDetail.setQuantity(detail.getQuantity());
                            bookingDetail.setUnitPrice(detail.getUnitPrice());
                            bookingDetail.setDiscountAmount(detail.getDiscountAmount());
                            bookingDetail.setAmount(detail.getAmount());

                            booking.addBookingDetail(bookingDetail);
                        });

                        return booking.getId() == null ? bookingService.create(booking) : bookingService.update(booking);
                    } catch (Exception ex) {
                        todayBookingView.showErrorMessage(ex.getMessage());
                        return -1;
                    }
                }

                @Override
                protected void done() {
                    try {
                        if (get() > 0) {
                            todayBookingView.showSuccessMessage(bookingDto.getId() == null
                                    ? Constants.SuccessMessage.CREATE_GUEST_SUCCESS
                                    : Constants.SuccessMessage.UPDATE_GUEST_SUCCESS);
                        }
                    } catch (Exception ex) {
                        todayBookingView.showErrorMessage(ex.getMessage());
                    }
                    createOrEditBookingModal.setVisible(false);
                }
            }.execute();
        });
    }

    public TodayBookingView loadTodayBookingView() {
        List<TodayBookingDto> bookings = bookingService.findTodayBookings();
        todayBookingView.setBookings(bookings);

        return todayBookingView;
    }

    public TodayBookingView getTodayBookingView() {
        return todayBookingView;
    }

    public void onNewBookingClicked() {
        createOrEditBookingModal.setModel(null);
        createOrEditBookingModal.setVisible(true);
    }

    public void onBookingCardClicked(Long bookingId) {
        Booking booking = bookingService.findById(bookingId);
        if (booking != null) {
            BookingDto bookingDto = new BookingDto();
            // map Booking to BookingDto
            bookingDto.setId(booking.getId());
            bookingDto.setBookingNo(booking.getBookingNo());
            bookingDto.setChargeType(booking.getChargeType());
            bookingDto.setCheckin(booking.getCheckin());
            bookingDto.setCheckout(booking.getCheckout());
            bookingDto.setRoomId(booking.getRoomId());
            bookingDto.setRoomTypeId(booking.getRoomTypeId());
            bookingDto.setPrimaryGuestId(booking.getPrimaryGuestId());
            bookingDto.setNumAdults(booking.getNumAdults());
            bookingDto.setNumChildren(booking.getNumChildren());
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
            bookingDto.setPaymentStatus(booking.getPaymentStatus());
            bookingDto.setNotes(booking.getNotes());

            bookingDto.setCreatedAt(booking.getCreatedAt());
            bookingDto.setCreatedBy(booking.getCreatedBy());
            bookingDto.setUpdatedAt(booking.getUpdatedAt());
            bookingDto.setUpdatedBy(booking.getUpdatedBy());

            bookingDto.setBookingDetails(booking.getBookingDetails());

            createOrEditBookingModal.setModel(bookingDto);
            createOrEditBookingModal.setVisible(true);
        }
    }

    public int saveBooking() {
        return 0;
        // BookingDto bookingDto = todayBookingView.getCurrentBookingDto();

        // Booking booking = new Booking();
        // booking.setId(bookingDto.getId());
        // booking.setBookingNo(bookingDto.getBookingNo());
        // booking.setChargeType(bookingDto.getChargeType());
        // booking.setCheckin(bookingDto.getCheckin());
        // booking.setCheckout(bookingDto.getCheckout());
        // booking.setRoomId(bookingDto.getRoomId());
        // booking.setRoomTypeId(bookingDto.getRoomTypeId());
        // booking.setPrimaryGuestId(bookingDto.getPrimaryGuestId());
        // booking.setNumAdults(bookingDto.getNumAdults());
        // booking.setNumChildren(bookingDto.getNumChildren());
        // booking.setStatus(bookingDto.getStatus());

        // if (bookingDto.getId() == null) {
        // // New booking, set default status
        // booking.setStatus(BookingStatus.CheckedIn);
        // return bookingService.create(booking);
        // }
        // return bookingService.update(booking);
    }

    public int checkOutBooking() {
        return 0;
        // BookingDto bookingDto = todayBookingView.getCurrentBookingDto();

        // Booking booking = new Booking();
        // booking.setId(bookingDto.getId());
        // booking.setBookingNo(bookingDto.getBookingNo());
        // booking.setChargeType(bookingDto.getChargeType());
        // booking.setCheckin(bookingDto.getCheckin());
        // booking.setCheckout(bookingDto.getCheckout());
        // booking.setRoomId(bookingDto.getRoomId());
        // booking.setRoomTypeId(bookingDto.getRoomTypeId());
        // booking.setPrimaryGuestId(bookingDto.getPrimaryGuestId());
        // booking.setNumAdults(bookingDto.getNumAdults());
        // booking.setNumChildren(bookingDto.getNumChildren());
        // booking.setStatus(BookingStatus.CheckedOut);

        // return bookingService.update(booking);
    }

    public int addServiceToBooking(Date value, String selectedItem, String trim, ServiceItem selectedItem2,
            String trim2) {
        // Long bookingId = todayBookingView.getCurrentBookingDto().getId();

        return 0;
    }

    public List<RoomTypeItem> getRoomTypeItems() {
        List<RoomType> roomTypes = roomTypeRepository.findAll();
        List<RoomTypeItem> roomTypeItems = new ArrayList<>();
        roomTypeItems.add(new RoomTypeItem(-1L, "Select Room Type"));
        for (RoomType roomType : roomTypes) {
            roomTypeItems.add(new RoomTypeItem(roomType.getId(), roomType.getName()));
        }
        return roomTypeItems;
    }

    public List<RoomItem> getRoomItems(Long roomTypeId) {
        List<Room> rooms;

        if (roomTypeId == -1L) {
            rooms = roomRepository.findAll();
        } else {
            rooms = roomRepository.findByRoomTypeId(roomTypeId);
        }

        List<RoomItem> roomItems = new ArrayList<>();
        roomItems.add(new RoomItem(-1L, "Select Room"));
        for (Room room : rooms) {
            roomItems.add(new RoomItem(room.getId(), room.getName()));
        }
        return roomItems;
    }

    public List<GuestItem> getGuestItems() {
        List<Guest> guests = guestRepository.findAll();
        List<GuestItem> guestItems = new ArrayList<>();
        guestItems.add(new GuestItem(-1L, "Select Guest", ""));
        for (Guest guest : guests) {
            guestItems.add(new GuestItem(guest.getId(), guest.getName(), guest.getPhone()));
        }
        return guestItems;
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

    public RoomTypeItem getRoomTypeItemById(Long roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId);
        if (roomType != null) {
            return new RoomTypeItem(roomType.getId(), roomType.getName());
        }
        return null;
    }

    public RoomItem getRoomItemById(Long roomId) {
        Room room = roomRepository.findById(roomId);
        if (room != null) {
            return new RoomItem(room.getId(), room.getName());
        }
        return null;
    }

    public GuestItem getGuestItemById(Long primaryGuestId) {
        Guest guest = guestRepository.findById(primaryGuestId);
        if (guest != null) {
            return new GuestItem(guest.getId(), guest.getName(), guest.getPhone());
        }
        return null;
    }
}