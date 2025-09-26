package vn.edu.uit.cntt.lt.e33.ie303.hms.util;

public class Constants {

    public static final String ALGORITHM = "AES";

    public class ErrorCode {

        public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
        public static final String USER_PASSWORD_IS_INCORRECT = "USER_PASSWORD_IS_INCORRECT";
        public static final String USER_ACCOUNT_HAS_BEEN_LOCKED = "USER_ACCOUNT_HAS_BEEN_LOCKED";
        public static final String USERNAME_CANNOT_BE_EMPTY = "USERNAME_CANNOT_BE_EMPTY";
        public static final String PASSWORD_CANNOT_BE_EMPTY = "PASSWORD_CANNOT_BE_EMPTY";
        public static final String USERNAME_IS_ALREADY_TAKEN = "USERNAME_IS_ALREADY_TAKEN";
    }

    public class ErrorMessage {

        public static final String USER_NOT_FOUND = "No matching user account found in the system.";
        public static final String USER_PASSWORD_IS_INCORRECT = "Your password is incorrect.";
        public static final String USER_ACCOUNT_HAS_BEEN_LOCKED = "Your account has been locked. Please contact the administrator.";
    }

    public class ValidateMessage {
        public static final String USERNAME_CANNOT_BE_EMPTY = "Username cannot be empty";
        public static final String PASSWORD_CANNOT_BE_EMPTY = "Password cannot be empty";
        public static final String USERNAME_IS_ALREADY_TAKEN = "Username is already taken";
    }

    public class SuccessMessage {

        public static final String CREATE_USER_SUCCESS = "User created successfully.";
        public static final String UPDATE_USER_SUCCESS = "User updated successfully.";
        public static final String DELETE_USER_SUCCESS = "User deleted successfully.";
    }

    public class ErrorTitle {

        public static final String LOGIN = "LOGIN";
        public static final String USER = "USER";
    }

    public class DateTimeFormat {
        public static final String ddMMyyyyHHmm = "dd/MM/yyyy HH:mm";
        public static final String ddMMyyyy = "dd/MM/yyyy";
    }
}
