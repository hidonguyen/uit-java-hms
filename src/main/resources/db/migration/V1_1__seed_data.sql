-- Users
INSERT INTO users (username, role, password_hash, status, created_by, updated_by)
VALUES
  ('manager',      'Manager',      'hash-demo', 'Active', NULL, NULL),
  ('receptionist',  'Receptionist', 'hash-demo', 'Active', 1,    1)
ON CONFLICT (username) DO NOTHING;

-- Room Types
INSERT INTO room_types (code, name, base_occupancy, max_occupancy, base_rate, hour_rate, extra_adult_fee, extra_child_fee, description, created_by, updated_by)
VALUES
  ('STD', 'Standard', 2, 3, 400000, 80000, 100000, 50000, 'Standard room', 1, 1),
  ('DLX', 'Deluxe',   2, 4, 700000, 120000,120000, 60000, 'Deluxe room',   1, 1)
ON CONFLICT (code) DO NOTHING;

-- Rooms
INSERT INTO rooms (name, room_type_id, description, housekeeping_status, status, created_by, updated_by)
VALUES
  ('101', (SELECT id FROM room_types WHERE code='STD'), 'STD 101', 'Clean', 'Available', 1, 1),
  ('102', (SELECT id FROM room_types WHERE code='STD'), 'STD 102', 'Clean', 'Available', 1, 1),
  ('201', (SELECT id FROM room_types WHERE code='DLX'), 'DLX 201', 'Clean', 'Available', 1, 1)
ON CONFLICT (name) DO NOTHING;

-- Guest
INSERT INTO guests (name, gender, date_of_birth, nationality, phone, email, address, description, created_by, updated_by)
VALUES
  ('Nguyen Van A', 'Male', '1990-05-10', 'VN', '0900000001', 'a@example.com', 'HCMC', 'Demo guest', 2, 2)
ON CONFLICT DO NOTHING;

-- Services
INSERT INTO services (name, unit, price, description, status, created_by, updated_by)
VALUES
  ('Breakfast', 'set', 80000, 'Buffet breakfast', 'Active', 1, 1),
  ('Laundry',   'kg',  50000, 'Laundry service',  'Active', 1, 1)
ON CONFLICT DO NOTHING;

-- Booking (không trùng giờ): bây giờ -> +2 giờ
WITH params AS (
  SELECT
    NOW()                                 AS ci,
    NOW() + INTERVAL '2 hours'            AS co,
    (SELECT id FROM rooms LIMIT 1)        AS room_id,
    (SELECT room_type_id FROM rooms LIMIT 1) AS room_type_id,
    (SELECT id FROM guests LIMIT 1)       AS guest_id
)
INSERT INTO bookings (booking_no, charge_type, checkin, checkout, room_id, room_type_id, primary_guest_id, num_adults, num_children, status, payment_status, notes, created_by, updated_by)
SELECT
  'BK0001', 'Hour', ci, co, room_id, room_type_id, guest_id, 2, 0, 'CheckedIn', 'Unpaid', 'Demo booking', 2, 2
FROM params
ON CONFLICT (booking_no) DO NOTHING;

-- Booking details (tiền phòng + dịch vụ)
INSERT INTO booking_details (booking_id, type, service_id, issued_at, description, quantity, unit_price, discount_amount, amount, created_by, updated_by)
VALUES
  (
    (SELECT id FROM bookings WHERE booking_no='BK0001'), 
    'Room', NULL, NOW(), 'Room charge 2 hours', 2, 
    (SELECT hour_rate FROM room_types rt JOIN bookings b ON b.room_type_id = rt.id WHERE b.booking_no='BK0001'),
    0, 
    2 * (SELECT hour_rate FROM room_types rt JOIN bookings b ON b.room_type_id = rt.id WHERE b.booking_no='BK0001'),
    2, 2
  ),
  (
    (SELECT id FROM bookings WHERE booking_no='BK0001'),
    'Service', (SELECT id FROM services WHERE name='Breakfast'), NOW(), 'Breakfast x2', 2,
    (SELECT price FROM services WHERE name='Breakfast'),
    0,
    2 * (SELECT price FROM services WHERE name='Breakfast'),
    2, 2
  );

-- Thanh toán một phần
INSERT INTO payments (booking_id, paid_at, payment_method, reference_no, amount, payer_name, notes, created_by, updated_by)
VALUES
  (
    (SELECT id FROM bookings WHERE booking_no='BK0001'),
    NOW(), 'Card', 'POS-001', 200000, 'Nguyen Van A', 'Partial payment', 2, 2
  );