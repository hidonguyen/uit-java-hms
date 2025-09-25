-- 5) USERS
CREATE TABLE IF NOT EXISTS users (
    id               BIGSERIAL PRIMARY KEY,
    username         VARCHAR(100) NOT NULL UNIQUE,
    role             VARCHAR(100) NOT NULL,
    password_hash    TEXT         NOT NULL,
    status           VARCHAR(100) NOT NULL DEFAULT 'Active',
    last_login_at    TIMESTAMPTZ,
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_by       BIGINT,
    CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_users_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL
);

-- 6) ROOM TYPES
CREATE TABLE IF NOT EXISTS room_types (
    id               BIGSERIAL PRIMARY KEY,
    code             VARCHAR(50)   NOT NULL UNIQUE,
    name             VARCHAR(200)  NOT NULL,
    base_occupancy   SMALLINT      NOT NULL CHECK (base_occupancy >= 0),
    max_occupancy    SMALLINT      NOT NULL CHECK (max_occupancy >= base_occupancy),
    base_rate        NUMERIC(12,2) NOT NULL CHECK (base_rate >= 0),
    hour_rate        NUMERIC(12,2) NOT NULL CHECK (hour_rate >= 0),
    extra_adult_fee  NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (extra_adult_fee >= 0),
    extra_child_fee  NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (extra_child_fee >= 0),
    description      TEXT,
    created_at       TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_by       BIGINT,
    CONSTRAINT fk_rt_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_rt_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL
);

-- 7) ROOMS
CREATE TABLE IF NOT EXISTS rooms (
    id                    BIGSERIAL PRIMARY KEY,
    name                  VARCHAR(100) NOT NULL UNIQUE,
    room_type_id          BIGINT       NOT NULL,
    description           TEXT,
    housekeeping_status   VARCHAR(100) NOT NULL DEFAULT 'Clean',
    status                VARCHAR(100) NOT NULL DEFAULT 'Available',
    created_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by            BIGINT,
    updated_at            TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_by            BIGINT,
    CONSTRAINT fk_rooms_type       FOREIGN KEY (room_type_id) REFERENCES room_types(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_rooms_created_by FOREIGN KEY (created_by)   REFERENCES users(id)      ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_rooms_updated_by FOREIGN KEY (updated_by)   REFERENCES users(id)      ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE INDEX IF NOT EXISTS ix_rooms_type ON rooms(room_type_id);

-- 8) GUESTS
CREATE TABLE IF NOT EXISTS guests (
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(200) NOT NULL,
    gender         VARCHAR(100),
    date_of_birth  DATE,
    nationality    VARCHAR(100),
    phone          VARCHAR(50),
    email          VARCHAR(255),
    address        TEXT,
    description    TEXT,
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by     BIGINT,
    updated_at     TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_by     BIGINT,
    CONSTRAINT fk_guests_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_guests_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE INDEX IF NOT EXISTS ix_guests_name  ON guests(name);
CREATE INDEX IF NOT EXISTS ix_guests_phone ON guests(phone);
CREATE INDEX IF NOT EXISTS ix_guests_email ON guests(email);

-- 9) SERVICES (đã bỏ cột category)
CREATE TABLE IF NOT EXISTS services (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(200)  NOT NULL,
    unit         VARCHAR(50)   NOT NULL,          -- ví dụ: giờ, lần, chai...
    price        NUMERIC(12,2) NOT NULL CHECK (price >= 0),
    description  TEXT,
    status       VARCHAR(100) NOT NULL DEFAULT 'Active',
    created_at   TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    created_by   BIGINT,
    updated_at   TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_by   BIGINT,
    CONSTRAINT fk_services_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_services_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE INDEX IF NOT EXISTS ix_services_name ON services(name);

-- 10) BOOKINGS
CREATE TABLE IF NOT EXISTS bookings (
    id                BIGSERIAL PRIMARY KEY,
    booking_no        VARCHAR(50)  NOT NULL UNIQUE,
    charge_type       VARCHAR(100) NOT NULL,
    checkin           TIMESTAMPTZ  NOT NULL,
    checkout          TIMESTAMPTZ,
    room_id           BIGINT       NOT NULL,
    room_type_id      BIGINT       NOT NULL,
    primary_guest_id  BIGINT,
    num_adults        SMALLINT     NOT NULL DEFAULT 1 CHECK (num_adults >= 0),
    num_children      SMALLINT     NOT NULL DEFAULT 0 CHECK (num_children >= 0),
    status            VARCHAR(100) NOT NULL DEFAULT 'CheckedIn',
    payment_status    VARCHAR(100) NOT NULL DEFAULT 'Unpaid',
    notes             TEXT,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    created_by        BIGINT,
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_by        BIGINT,
    CONSTRAINT fk_book_room        FOREIGN KEY (room_id)          REFERENCES rooms(id)       ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_book_room_type   FOREIGN KEY (room_type_id)     REFERENCES room_types(id)  ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_book_guest       FOREIGN KEY (primary_guest_id) REFERENCES guests(id)      ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_book_created_by  FOREIGN KEY (created_by)       REFERENCES users(id)       ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_book_updated_by  FOREIGN KEY (updated_by)       REFERENCES users(id)       ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_booking_dates CHECK (checkout IS NULL OR checkout >= checkin)
);
CREATE INDEX IF NOT EXISTS ix_bookings_room     ON bookings(room_id);
CREATE INDEX IF NOT EXISTS ix_bookings_guest    ON bookings(primary_guest_id);
CREATE INDEX IF NOT EXISTS ix_bookings_checkin  ON bookings(checkin);

-- 11) BOOKING DETAILS
CREATE TABLE IF NOT EXISTS booking_details (
    id               BIGSERIAL PRIMARY KEY,
    booking_id       BIGINT       NOT NULL,
    type             VARCHAR(100)  NOT NULL,              -- room/service/fee/adjustment
    service_id       BIGINT,                             -- NOT NULL khi type='service'
    issued_at        TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    description      TEXT,
    quantity         NUMERIC(12,2) NOT NULL DEFAULT 1 CHECK (quantity >= 0),
    unit_price       NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (unit_price >= 0),
    discount_amount  NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (discount_amount >= 0),
    amount           NUMERIC(12,2) NOT NULL CHECK (amount >= 0),
    created_at       TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_by       BIGINT,
    CONSTRAINT fk_bdet_booking    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_bdet_service    FOREIGN KEY (service_id) REFERENCES services(id) ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_bdet_created_by FOREIGN KEY (created_by) REFERENCES users(id)   ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_bdet_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)   ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT chk_bdet_service_ref CHECK (
        (type = 'Service' AND service_id IS NOT NULL)
        OR (type <> 'Service')
    )
);
CREATE INDEX IF NOT EXISTS ix_bdet_booking ON booking_details(booking_id);
CREATE INDEX IF NOT EXISTS ix_bdet_type    ON booking_details(type);

-- 12) PAYMENTS
CREATE TABLE IF NOT EXISTS payments (
    id               BIGSERIAL PRIMARY KEY,
    booking_id       BIGINT         NOT NULL,
    paid_at          TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    payment_method   VARCHAR(100)   NOT NULL,
    reference_no     VARCHAR(100),
    amount           NUMERIC(12,2)  NOT NULL CHECK (amount > 0),
    payer_name       VARCHAR(200),
    notes            TEXT,
    created_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    created_by       BIGINT,
    updated_at       TIMESTAMPTZ    NOT NULL DEFAULT NOW(),
    updated_by       BIGINT,
    CONSTRAINT fk_pay_booking     FOREIGN KEY (booking_id) REFERENCES bookings(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_pay_created_by  FOREIGN KEY (created_by)  REFERENCES users(id)   ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_pay_updated_by  FOREIGN KEY (updated_by)  REFERENCES users(id)   ON UPDATE CASCADE ON DELETE SET NULL
);
CREATE INDEX IF NOT EXISTS ix_payments_booking ON payments(booking_id);
CREATE INDEX IF NOT EXISTS ix_payments_paid_at ON payments(paid_at);