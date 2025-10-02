-- ==========================================
-- HOTEL MANAGEMENT SYSTEM DATABASE SCHEMA
-- ==========================================

-- ==========================================
-- 1) USERS TABLE
-- ==========================================
CREATE TABLE users (
    id               BIGSERIAL PRIMARY KEY,
    username         VARCHAR(100) NOT NULL UNIQUE,
    role             VARCHAR(100) NOT NULL CHECK (role IN ('Manager', 'Receptionist', 'Housekeeping', 'Accountant')),
    password_hash    TEXT         NOT NULL,
    status           VARCHAR(100) NOT NULL DEFAULT 'Active' CHECK (status IN ('Active', 'Locked')),
    last_login_at    TIMESTAMP,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMP,
    updated_by       BIGINT,
    CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_users_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL
);

-- Create indexes for users table
CREATE INDEX ix_users_username ON users(username);
CREATE INDEX ix_users_role ON users(role);
CREATE INDEX ix_users_status ON users(status);

-- ==========================================
-- 2) ROOM TYPES TABLE
-- ==========================================
CREATE TABLE room_types (
    id               BIGSERIAL PRIMARY KEY,
    code             VARCHAR(50)   NOT NULL UNIQUE,
    name             VARCHAR(200)  NOT NULL,
    base_occupancy   SMALLINT      NOT NULL CHECK (base_occupancy > 0),
    max_occupancy    SMALLINT      NOT NULL CHECK (max_occupancy >= base_occupancy),
    base_rate        NUMERIC(12,2) NOT NULL CHECK (base_rate >= 0),
    hour_rate        NUMERIC(12,2) NOT NULL CHECK (hour_rate >= 0),
    extra_adult_fee  NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (extra_adult_fee >= 0),
    extra_child_fee  NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (extra_child_fee >= 0),
    description      TEXT,
    created_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMP,
    updated_by       BIGINT,
    CONSTRAINT fk_room_types_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_room_types_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL
);

-- Create indexes for room_types table
CREATE INDEX ix_room_types_code ON room_types(code);
CREATE INDEX ix_room_types_name ON room_types(name);

-- ==========================================
-- 3) SERVICES TABLE
-- ==========================================
CREATE TABLE services (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(200)  NOT NULL,
    unit         VARCHAR(50)   NOT NULL,
    price        NUMERIC(12,2) NOT NULL CHECK (price >= 0),
    description  TEXT,
    status       VARCHAR(100)  NOT NULL DEFAULT 'Active' CHECK (status IN ('Active', 'Inactive')),
    created_at   TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by   BIGINT,
    updated_at   TIMESTAMP,
    updated_by   BIGINT,
    CONSTRAINT fk_services_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_services_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL
);

-- Create indexes for services table
CREATE INDEX ix_services_name ON services(name);
CREATE INDEX ix_services_status ON services(status);

-- ==========================================
-- 4) ROOMS TABLE
-- ==========================================
CREATE TABLE rooms (
    id                    BIGSERIAL PRIMARY KEY,
    name                  VARCHAR(100) NOT NULL UNIQUE,
    room_type_id          BIGINT       NOT NULL,
    description           TEXT,
    housekeeping_status   VARCHAR(100) NOT NULL DEFAULT 'Clean' CHECK (housekeeping_status IN ('Clean', 'Dirty', 'Inspected', 'OutOfOrder')),
    status                VARCHAR(100) NOT NULL DEFAULT 'Available' CHECK (status IN ('Available', 'Occupied', 'OutOfService')),
    created_at            TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by            BIGINT,
    updated_at            TIMESTAMP,
    updated_by            BIGINT,
    CONSTRAINT fk_rooms_room_type   FOREIGN KEY (room_type_id) REFERENCES room_types(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_rooms_created_by  FOREIGN KEY (created_by)   REFERENCES users(id)      ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_rooms_updated_by  FOREIGN KEY (updated_by)   REFERENCES users(id)      ON UPDATE CASCADE ON DELETE SET NULL
);

-- Create indexes for rooms table
CREATE INDEX ix_rooms_name ON rooms(name);
CREATE INDEX ix_rooms_room_type_id ON rooms(room_type_id);
CREATE INDEX ix_rooms_status ON rooms(status);
CREATE INDEX ix_rooms_housekeeping_status ON rooms(housekeeping_status);

-- ==========================================
-- 5) GUESTS TABLE
-- ==========================================
CREATE TABLE guests (
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(200) NOT NULL,
    gender         VARCHAR(100) CHECK (gender IN ('Male', 'Female', 'Other')),
    date_of_birth  DATE,
    nationality    VARCHAR(100),
    phone          VARCHAR(50),
    email          VARCHAR(255),
    address        TEXT,
    description    TEXT,
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by     BIGINT,
    updated_at     TIMESTAMP,
    updated_by     BIGINT,
    CONSTRAINT fk_guests_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_guests_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL
);

-- Create indexes for guests table
CREATE INDEX ix_guests_name ON guests(name);
CREATE INDEX ix_guests_phone ON guests(phone);
CREATE INDEX ix_guests_email ON guests(email);
CREATE INDEX ix_guests_nationality ON guests(nationality);

-- ==========================================
-- 6) BOOKINGS TABLE
-- ==========================================
CREATE TABLE bookings (
    id                BIGSERIAL PRIMARY KEY,
    booking_no        VARCHAR(50)  NOT NULL UNIQUE,
    charge_type       VARCHAR(100) NOT NULL CHECK (charge_type IN ('Hour', 'Night')),
    checkin           TIMESTAMP    NOT NULL,
    checkout          TIMESTAMP,
    room_id           BIGINT       NOT NULL,
    room_type_id      BIGINT       NOT NULL,
    primary_guest_id  BIGINT,
    num_adults        SMALLINT     NOT NULL DEFAULT 1 CHECK (num_adults >= 0),
    num_children      SMALLINT     NOT NULL DEFAULT 0 CHECK (num_children >= 0),
    status            VARCHAR(100) NOT NULL DEFAULT 'CheckedIn' CHECK (status IN ('CheckedIn', 'CheckedOut')),
    payment_status    VARCHAR(100) NOT NULL DEFAULT 'Unpaid' CHECK (payment_status IN ('Unpaid', 'Partial', 'Paid')),
    notes             TEXT,
    created_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by        BIGINT,
    updated_at        TIMESTAMP,
    updated_by        BIGINT,
    CONSTRAINT fk_bookings_room         FOREIGN KEY (room_id)          REFERENCES rooms(id)       ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_bookings_room_type    FOREIGN KEY (room_type_id)     REFERENCES room_types(id)  ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_bookings_guest        FOREIGN KEY (primary_guest_id) REFERENCES guests(id)      ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_bookings_created_by   FOREIGN KEY (created_by)       REFERENCES users(id)       ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_bookings_updated_by   FOREIGN KEY (updated_by)       REFERENCES users(id)       ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_bookings_dates       CHECK (checkout IS NULL OR checkout >= checkin),
    CONSTRAINT chk_bookings_occupancy   CHECK (num_adults + num_children > 0)
);

-- Create indexes for bookings table
CREATE INDEX ix_bookings_booking_no ON bookings(booking_no);
CREATE INDEX ix_bookings_room_id ON bookings(room_id);
CREATE INDEX ix_bookings_guest_id ON bookings(primary_guest_id);
CREATE INDEX ix_bookings_checkin ON bookings(checkin);
CREATE INDEX ix_bookings_checkout ON bookings(checkout);
CREATE INDEX ix_bookings_status ON bookings(status);
CREATE INDEX ix_bookings_payment_status ON bookings(payment_status);

-- ==========================================
-- 7) BOOKING DETAILS TABLE
-- ==========================================
CREATE TABLE booking_details (
    id               BIGSERIAL PRIMARY KEY,
    booking_id       BIGINT       NOT NULL,
    type             VARCHAR(100) NOT NULL CHECK (type IN ('Room', 'Service', 'Fee', 'Adjustment')),
    service_id       BIGINT,
    issued_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
    description      TEXT,
    quantity         NUMERIC(12,2) NOT NULL DEFAULT 1 CHECK (quantity >= 0),
    unit_price       NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (unit_price >= 0),
    discount_amount  NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (discount_amount >= 0),
    amount           NUMERIC(12,2) NOT NULL CHECK (amount >= 0),
    created_at       TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMP,
    updated_by       BIGINT,
    CONSTRAINT fk_booking_details_booking    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_booking_details_service    FOREIGN KEY (service_id) REFERENCES services(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_booking_details_created_by FOREIGN KEY (created_by) REFERENCES users(id)   ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_booking_details_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)   ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_booking_details_service_ref CHECK (
        (type = 'Service' AND service_id IS NOT NULL) OR (type <> 'Service')
    )
);

-- Create indexes for booking_details table
CREATE INDEX ix_booking_details_booking_id ON booking_details(booking_id);
CREATE INDEX ix_booking_details_type ON booking_details(type);
CREATE INDEX ix_booking_details_service_id ON booking_details(service_id);
CREATE INDEX ix_booking_details_issued_at ON booking_details(issued_at);

-- ==========================================
-- 8) PAYMENTS TABLE
-- ==========================================
CREATE TABLE payments (
    id               BIGSERIAL PRIMARY KEY,
    booking_id       BIGINT         NOT NULL,
    paid_at          TIMESTAMP      NOT NULL DEFAULT NOW(),
    payment_method   VARCHAR(100)   NOT NULL CHECK (payment_method IN ('Cash', 'Card', 'Other')),
    reference_no     VARCHAR(100),
    amount           NUMERIC(12,2)  NOT NULL CHECK (amount > 0),
    payer_name       VARCHAR(200),
    notes            TEXT,
    created_at       TIMESTAMP      NOT NULL DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMP,
    updated_by       BIGINT,
    CONSTRAINT fk_payments_booking     FOREIGN KEY (booking_id) REFERENCES bookings(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_payments_created_by  FOREIGN KEY (created_by) REFERENCES users(id)    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_payments_updated_by  FOREIGN KEY (updated_by) REFERENCES users(id)    ON UPDATE CASCADE ON DELETE SET NULL
);

-- Create indexes for payments table
CREATE INDEX ix_payments_booking_id ON payments(booking_id);
CREATE INDEX ix_payments_paid_at ON payments(paid_at);
CREATE INDEX ix_payments_payment_method ON payments(payment_method);
CREATE INDEX ix_payments_reference_no ON payments(reference_no);

-- ==========================================
-- ADDITIONAL COMMENTS AND DOCUMENTATION
-- ==========================================

-- Business Rules:
-- 1. Users can self-reference for created_by/updated_by tracking
-- 2. Rooms must belong to a room type and cannot be deleted if they have bookings
-- 3. Bookings track both room and room_type for historical purposes
-- 4. Booking details support different types: Room charges, Services, Fees, and Adjustments
-- 5. Payment status is automatically calculated based on total booking amount vs payments
-- 6. All monetary values use NUMERIC(12,2) for precision
-- 7. All tables include audit fields (created_at, created_by, updated_at, updated_by)

-- Performance Considerations:
-- 1. Indexes on frequently queried columns (names, dates, status fields)
-- 2. Foreign key indexes for join performance
-- 3. Composite indexes may be added based on query patterns

-- Data Integrity:
-- 1. CHECK constraints ensure valid enum values
-- 2. Foreign key constraints maintain referential integrity
-- 3. Cascading rules prevent orphaned records where appropriate
-- 4. Business logic constraints (dates, amounts, occupancy)