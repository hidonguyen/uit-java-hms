package vn.edu.uit.cntt.lt.e33.ie303.hms.bootstrap;

import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.repository.*;
import vn.edu.uit.cntt.lt.e33.ie303.hms.domain.service.*;
import vn.edu.uit.cntt.lt.e33.ie303.hms.infra.repository.*;
import vn.edu.uit.cntt.lt.e33.ie303.hms.infra.service.*;

import javax.sql.DataSource;

public final class DIContainer {
    private static DIContainer instance;

    private final IBookingRepository bookingRepository;
    private final IBookingDetailRepository bookingDetailRepository;
    private final IGuestRepository guestRepository;
    private final IPaymentRepository paymentRepository;
    private final IRoomRepository roomRepository;
    private final IRoomTypeRepository roomTypeRepository;
    private final IServiceRepository serviceRepository;
    private final IUserRepository userRepository;
    private final IReportRepository reportRepository;
    private final IBookingHistoryRepository bookingHistoryRepository;

    private final IUserService userService;
    private final IServiceService serviceService;
    private final IRoomService roomService;
    private final IRoomTypeService roomTypeService;
    private final IGuestService guestService;
    private final IReportService reportService;
    private final IBookingHistoryService bookingHistoryService;

    private DIContainer() {
        AppConfig config = AppConfig.load();
        DataSource dataSource = AppConfig.dataSource(config);

        this.bookingRepository = new BookingRepository(dataSource);
        this.bookingDetailRepository = new BookingDetailRepository(dataSource);
        this.guestRepository = new GuestRepository(dataSource);
        this.paymentRepository = new PaymentRepository(dataSource);
        this.roomRepository = new RoomRepository(dataSource);
        this.roomTypeRepository = new RoomTypeRepository(dataSource);
        this.serviceRepository = new ServiceRepository(dataSource);
        this.userRepository = new UserRepository(dataSource);
        this.reportRepository = new ReportRepository(dataSource);
        this.bookingHistoryRepository = new BookingHistoryRepository(dataSource);

        this.userService = new UserService(userRepository);
        this.serviceService = new ServiceService(serviceRepository);
        this.roomService = new RoomService(roomRepository);
        this.roomTypeService = new RoomTypeService(roomTypeRepository);
        this.guestService = new GuestService(guestRepository);
        this.reportService = new ReportService(reportRepository);
        this.bookingHistoryService = new BookingHistoryService(bookingHistoryRepository);

    }

    public static DIContainer getInstance() {
        if (instance == null) {
            instance = new DIContainer();
        }
        return instance;
    }

    public IBookingRepository getBookingRepository() {
        return bookingRepository;
    }

    public IBookingDetailRepository getBookingDetailRepository() {
        return bookingDetailRepository;
    }

    public IGuestRepository getGuestRepository() {
        return guestRepository;
    }

    public IPaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public IRoomRepository getRoomRepository() {
        return roomRepository;
    }

    public IRoomTypeRepository getRoomTypeRepository() {
        return roomTypeRepository;
    }

    public IServiceRepository getServiceRepository() {
        return serviceRepository;
    }

    public IUserRepository getUserRepository() {
        return userRepository;
    }

    public IUserService getUserService() {
        return userService;
    }

    public IServiceService getServiceService() {
        return serviceService;
    }

    public IRoomService getRoomService() {
        return roomService;
    }

    public IRoomTypeService getRoomTypeService() {
        return roomTypeService;
    }

    public IGuestService getGuestService() {
        return guestService;
    }

    public IReportService getReportService() {
        return reportService;
    }

    public IBookingHistoryService getBookingHistoryService() {
        return bookingHistoryService;
    }
}
