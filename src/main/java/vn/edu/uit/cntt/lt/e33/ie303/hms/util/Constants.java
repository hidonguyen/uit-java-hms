package vn.edu.uit.cntt.lt.e33.ie303.hms.util;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings;

public class Constants {

    public static final String ALGORITHM = "AES";

    // ===================== ERROR CODE =====================
    public class ErrorCode {
        // User
        public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
        public static final String USER_PASSWORD_IS_INCORRECT = "USER_PASSWORD_IS_INCORRECT";
        public static final String USER_ACCOUNT_HAS_BEEN_LOCKED = "USER_ACCOUNT_HAS_BEEN_LOCKED";
        public static final String USERNAME_CANNOT_BE_EMPTY = "USERNAME_CANNOT_BE_EMPTY";
        public static final String PASSWORD_CANNOT_BE_EMPTY = "PASSWORD_CANNOT_BE_EMPTY";
        public static final String USERNAME_IS_ALREADY_TAKEN = "USERNAME_IS_ALREADY_TAKEN";

        // Service
        public static final String SERVICE_NOT_FOUND = "SERVICE_NOT_FOUND";
        public static final String SERVICE_NAME_ALREADY_EXISTS = "SERVICE_NAME_ALREADY_EXISTS";
        public static final String SERVICE_NAME_CANNOT_BE_EMPTY = "SERVICE_NAME_CANNOT_BE_EMPTY";

        // Room
        public static final String ROOM_NOT_FOUND = "ROOM_NOT_FOUND";
        public static final String ROOM_NAME_ALREADY_EXISTS = "ROOM_NAME_ALREADY_EXISTS";
        public static final String ROOM_NAME_CANNOT_BE_EMPTY = "ROOM_NAME_CANNOT_BE_EMPTY";

        // RoomType
        public static final String ROOM_TYPE_NOT_FOUND = "ROOM_TYPE_NOT_FOUND";
        public static final String ROOM_TYPE_CODE_ALREADY_EXISTS = "ROOM_TYPE_CODE_ALREADY_EXISTS";
        public static final String ROOM_TYPE_CODE_CANNOT_BE_EMPTY = "ROOM_TYPE_CODE_CANNOT_BE_EMPTY";

        // Guest
        public static final String GUEST_NOT_FOUND = "GUEST_NOT_FOUND";
        public static final String GUEST_NAME_CANNOT_BE_EMPTY = "GUEST_NAME_CANNOT_BE_EMPTY";
    }

    // ===================== ERROR MESSAGE =====================
    public class ErrorMessage {
        // User
        public static final String USER_NOT_FOUND = "No matching user account found in the system.";
        public static final String USER_PASSWORD_IS_INCORRECT = "Your password is incorrect.";
        public static final String USER_ACCOUNT_HAS_BEEN_LOCKED = "Your account has been locked. Please contact the administrator.";

        // Service
        public static final String SERVICE_NOT_FOUND = "Service not found in the system.";
        public static final String SERVICE_NAME_ALREADY_EXISTS = "Service name is already taken.";
        public static final String SERVICE_NAME_CANNOT_BE_EMPTY = "Service name cannot be empty.";

        // Room
        public static final String ROOM_NOT_FOUND = "Room not found in the system.";
        public static final String ROOM_NAME_ALREADY_EXISTS = "Room name already exists.";
        public static final String ROOM_NAME_CANNOT_BE_EMPTY = "Room name cannot be empty.";

        // RoomType
        public static final String ROOM_TYPE_NOT_FOUND = "Room type not found in the system.";
        public static final String ROOM_TYPE_CODE_ALREADY_EXISTS = "Room type code already exists.";
        public static final String ROOM_TYPE_CODE_CANNOT_BE_EMPTY = "Room type code cannot be empty.";

        // Guest
        public static final String GUEST_NOT_FOUND = "Guest not found in the system.";
        public static final String GUEST_NAME_CANNOT_BE_EMPTY = "Guest name cannot be empty.";
    }

    // ===================== VALIDATE MESSAGE =====================
    public class ValidateMessage {
        // User
        public static final String USERNAME_CANNOT_BE_EMPTY = "Username cannot be empty";
        public static final String PASSWORD_CANNOT_BE_EMPTY = "Password cannot be empty";
        public static final String USERNAME_IS_ALREADY_TAKEN = "Username is already taken";

        // Service
        public static final String SERVICE_NAME_CANNOT_BE_EMPTY = "Service name cannot be empty";
        public static final String SERVICE_UNIT_CANNOT_BE_EMPTY = "Service unit cannot be empty";
        public static final String SERVICE_PRICE_MUST_BE_POSITIVE = "Service price must be greater than 0";

        // Room
        public static final String ROOM_NAME_CANNOT_BE_EMPTY = "Room name cannot be empty";
        public static final String ROOM_TYPE_MUST_BE_SELECTED = "Room type must be selected";

        // RoomType
        public static final String ROOM_TYPE_CODE_CANNOT_BE_EMPTY = "Room type code cannot be empty";
        public static final String ROOM_TYPE_NAME_CANNOT_BE_EMPTY = "Room type name cannot be empty";

        // Guest
        public static final String GUEST_NAME_CANNOT_BE_EMPTY = "Guest name cannot be empty";
        public static final String GUEST_PHONE_INVALID = "Guest phone number is invalid";
        public static final String GUEST_EMAIL_INVALID = "Guest email is invalid";

        // Booking
        public static final String CHECKIN_TIME_CANNOT_BE_EMPTY = "Check-in time cannot be empty";
        public static final String CHECKOUT_TIME_MUST_BE_AFTER_CHECKIN_TIME = "Check-out time must be after check-in time";
        public static final String ROOM_MUST_BE_SELECTED = "Room must be selected";
        public static final String GUEST_MUST_BE_SELECTED = "Guest must be selected";
    }

    // ===================== SUCCESS MESSAGE =====================
    public class SuccessMessage {
        // User
        public static final String CREATE_USER_SUCCESS = "User created successfully.";
        public static final String UPDATE_USER_SUCCESS = "User updated successfully.";
        public static final String DELETE_USER_SUCCESS = "User deleted successfully.";

        // Service
        public static final String CREATE_SERVICE_SUCCESS = "Service created successfully.";
        public static final String UPDATE_SERVICE_SUCCESS = "Service updated successfully.";
        public static final String DELETE_SERVICE_SUCCESS = "Service deleted successfully.";

        // Room
        public static final String CREATE_ROOM_SUCCESS = "Room created successfully.";
        public static final String UPDATE_ROOM_SUCCESS = "Room updated successfully.";
        public static final String DELETE_ROOM_SUCCESS = "Room deleted successfully.";

        // RoomType
        public static final String CREATE_ROOM_TYPE_SUCCESS = "Room type created successfully.";
        public static final String UPDATE_ROOM_TYPE_SUCCESS = "Room type updated successfully.";
        public static final String DELETE_ROOM_TYPE_SUCCESS = "Room type deleted successfully.";

        // Guest
        public static final String CREATE_GUEST_SUCCESS = "Guest created successfully.";
        public static final String UPDATE_GUEST_SUCCESS = "Guest updated successfully.";
        public static final String DELETE_GUEST_SUCCESS = "Guest deleted successfully.";
    }

    // ===================== ERROR TITLE =====================
    public class ErrorTitle {
        public static final String LOGIN = "LOGIN";
        public static final String USER = "USER";
        public static final String SERVICE = "SERVICE";
        public static final String ROOM = "ROOM";
        public static final String ROOM_TYPE = "ROOM_TYPE";
        public static final String GUEST = "GUEST";
        public static final String BOOKING = "BOOKING";
    }

    // ===================== DATE FORMAT =====================
    public class DateTimeFormat {
        public static final String ddMMyyyyHHmm = "dd/MM/yyyy HH:mm";
        public static final String ddMMyyyy = "dd/MM/yyyy";
        public static final String HHmm = "HH:mm";
    }
}
