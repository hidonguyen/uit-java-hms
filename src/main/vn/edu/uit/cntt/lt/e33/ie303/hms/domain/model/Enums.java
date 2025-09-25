package vn.edu.uit.cntt.lt.e33.ie303.hms.domain.model;

public enum UserStatus {
    Active,
    Locked
}

public enum HousekeepingStatus {
    Clean,
    Dirty,
    Inspected,
    OutOfOrder
}

public enum RoomStatus {
    Available,
    Occupied,
    OutOfService
}

public enum ServiceStatus {
    Active,
    Inactive
}

public enum BookingChargeType {
    Hour,
    Night
}

public enum BookingStatus {
    CheckedIn,
    CheckedOut
}

public enum PaymentStatus {
    Unpaid,
    Partial,
    Paid
}

public enum BookingDetailType {
    Room,
    Service,
    Fee,
    Adjustment
}

public enum PaymentMethod {
    Cash,
    Card,
    Other
}

public enum GuestGender {
    Male,
    Female,
    Other
}