package vn.edu.uit.cntt.lt.e33.ie303.hms.util;

public class Constants {

    public static final String ALGORITHM = "AES";

    public class ErrorCode {

        public static final String USER_NOT_FOUND = "USR_01";
        public static final String PASSWORD_NOT_CORRECT = "USR_02";
        public static final String USER_HAS_BEEN_LOCKED = "USR_03";
        public static final String USER_IS_NOT_ADMIN = "USR_04";
        public static final String AUTHOR_CAN_NOT_CREATE = "AUT_04";
        public static final String CREATE_USER_FAILD = "USR_05";
        public static final String UPDATE_USER_FAILD = "USR_06";
        public static final String NOT_FOUND_USER = "USR_07";
        public static final String DELETE_USER = "USR_08";
        public static final String CREATE_TRANS_FAILED = "TRN_01";
        public static final String REVOKE_TRANS_FAILED = "TRN_02";
    }

    public class ErrorMessage {

        public static final String USER_NOT_FOUND = "No matching user account found in the system.";
        public static final String PASSWORD_NOT_CORRECT = "Invalid username or password.";
        public static final String USER_HAS_BEEN_LOCKED = "Your account has been locked. Please contact the administrator.";
        public static final String USER_IS_NOT_ADMIN = "Your account login is not admin, Please verify it again.";
        public static final String CREATE_AUTHOR_FAILD = "Create author failed";
        public static final String CREATE_USER_FAILD = "Create user failed";
        public static final String UPDATE_USER_FAILD = "Update user failed";
        public static final String DELETE_USER_FAILD = "Delete user failed";
        public static final String CREATE_TRANS_FAILED = "Failed to create transaction loans";
        public static final String REVOKE_TRANS_FAILED = "Failed to revoke transaction loans";
    }

    public class ValidateMessage {

        public static final String NAME_CAN_NOT_EMPTY = "Name can not empty";
        public static final String USER_INFORMATION_CAN_NOT_EMPTY = "First Name, Last Name, User Name, and Email cannot be empty";
        public static final String PASSWORD_CAN_NOT_EMPTY = "Password cannot be empty";
        public static final String CHOOSE_USER = "Please select a user!";
        public static final String CORRECT_FORMAT_DATE = "Please enter a valid loan return date (MM/dd/yyyy)!";
        public static final String SELECT_AT_LEAST_A_BOOK = "Please select at least one book!";
        public static final String BOOK_OUT_OF_STOCK = "This book is out of stock and cannot be selected!";
        public static final String SELECT_MOVE_BOOK_TO_ADD = "Please select a book to add!";
        public static final String SELECT_BOOK_REMOVE = "Please select a book to remove";
         public static final String EMAIL_IS_DUPLICATE = "Email is already exist";
          public static final String USERNAME_IS_DUPLICATE = "UserName is already exist";
    }

    public class SuccessMessage {

        public static final String CREATE_AUTHOR_SUCCESS = "Create author successful.";
        public static final String CREATE_USER_SUCCESS = "Create user successful.";
        public static final String UPDATE_USER_SUCCESS = "Update user successful.";
        public static final String REVOKE_TRANS_SUCCESS = "Transaction revoked successful.";
        public static final String DELETE_USER_SUCCESS = "User deleted successfully";
        public static final String CREATE_TRANS_SUCCESS = "Transaction create successfully!";
    }

    public class ErrorTitle {

        public static final String LOGIN = "LOGIN";
        public static final String AUTHOR = "AUTHOR";
        public static final String USER = "USER";
        public static final String TRANS = "TRANSACTION LOAN";
    }

    public class GeneralStatus {

        public static final int OPEN = 0;
        public static final int DELETE = 1;
    }

    public class DateFormat {

        public static final String MMddyyyy = "MM/dd/yyyy";
    }

    public class ConfirmConsts {

        public static final String CONFIRM_TITLE = "Confirm";
        public static final String CONFIRM_CONTENT = "Are you want to save?";
        public static final String CONFIRM_EXIT_CONTENT = "Are you want to exit?";
        public static final String CONFIRM_DELETE_CONTENT = "Are you want to delete?";
        public static final String CONFIRM_REVOKE_CONTENT = "Are you want to revoke this trans?";
        public static final String CONFIRM_UPDATE_CONTENT = "Are you want to update?";
    }

    public class TransLoanStatusConsts {

        public static final int BORROW = 0;
        public static final int PAID = 1;
    }

    public class EmailConstants {
        public static final String EMAIL_SUBJECT_ACCOUNT_CREATED = "UIT-LMS | Your Account Has Been Successfully Created";
        public static final String LOAN_BOOK_SUBJECT = "Your Book Loan Confirmation - Ticket #%s";
    }

}
