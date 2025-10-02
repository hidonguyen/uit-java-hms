-- ==========================================
-- HOTEL MANAGEMENT SYSTEM SEED DATA
-- ==========================================

-- Sample Bookings - Demonstrate different booking scenarios
-- Active booking (currently checked in)
WITH current_booking AS (
  SELECT
    NOW() - INTERVAL '1 hour'             AS ci,
    NOW() + INTERVAL '3 hours'            AS co,
    (SELECT id FROM rooms WHERE name='101' LIMIT 1) AS room_id,
    (SELECT room_type_id FROM rooms WHERE name='101' LIMIT 1) AS room_type_id,
    (SELECT id FROM guests WHERE name='Nguyen Van A' LIMIT 1) AS guest_id
)
INSERT INTO bookings (booking_no, charge_type, checkin, checkout, room_id, room_type_id, primary_guest_id, num_adults, num_children, status, payment_status, notes, created_by)
SELECT
  'BK0001', 'Hour', ci, co, room_id, room_type_id, guest_id, 2, 0, 'CheckedIn', 'Unpaid', 'Hourly booking - currently active', 1
FROM current_booking
ON CONFLICT (booking_no) DO NOTHING;

-- Completed booking (checked out)
INSERT INTO bookings (booking_no, charge_type, checkin, checkout, room_id, room_type_id, primary_guest_id, num_adults, num_children, status, payment_status, notes, created_by)
VALUES
  ('BK0002', 'Night', 
   NOW() - INTERVAL '2 days', 
   NOW() - INTERVAL '1 day',
   (SELECT id FROM rooms WHERE name='201' LIMIT 1),
   (SELECT room_type_id FROM rooms WHERE name='201' LIMIT 1),
   (SELECT id FROM guests WHERE name='John Smith' LIMIT 1),
   2, 1, 'CheckedOut', 'Paid', 'Business trip - completed', 1)
ON CONFLICT (booking_no) DO NOTHING;

-- Booking Details - Itemized charges for bookings
-- Details for active booking BK0001
INSERT INTO booking_details (booking_id, type, service_id, issued_at, description, unit, quantity, unit_price, discount_amount, amount, created_by)
VALUES
  -- Room charge for BK0001 (4 hours)
  ((SELECT id FROM bookings WHERE booking_no='BK0001'), 
   'Room', NULL, NOW() - INTERVAL '1 hour', 'Room charge - 4 hours', 'hours', 4.0, 
   (SELECT hour_rate FROM room_types rt JOIN bookings b ON b.room_type_id = rt.id WHERE b.booking_no='BK0001'),
   0, 
   4.0 * (SELECT hour_rate FROM room_types rt JOIN bookings b ON b.room_type_id = rt.id WHERE b.booking_no='BK0001'),
   1),
  -- Breakfast service for BK0001
  ((SELECT id FROM bookings WHERE booking_no='BK0001'),
   'Service', (SELECT id FROM services WHERE name='Breakfast'), NOW() - INTERVAL '30 minutes', 'Breakfast for 2 guests', 'servings', 2.0,
   (SELECT price FROM services WHERE name='Breakfast'),
   0,
   2.0 * (SELECT price FROM services WHERE name='Breakfast'),
   1),
  -- Laundry service for BK0001
  ((SELECT id FROM bookings WHERE booking_no='BK0001'),
   'Service', (SELECT id FROM services WHERE name='Laundry'), NOW() - INTERVAL '15 minutes', 'Laundry service', 'kg', 1.5,
   (SELECT price FROM services WHERE name='Laundry'),
   10000,
   1.5 * (SELECT price FROM services WHERE name='Laundry') - 10000,
   1);

-- Details for completed booking BK0002
INSERT INTO booking_details (booking_id, type, service_id, issued_at, description, unit, quantity, unit_price, discount_amount, amount, created_by)
VALUES
  -- Room charge for BK0002 (1 night)
  ((SELECT id FROM bookings WHERE booking_no='BK0002'),
   'Room', NULL, NOW() - INTERVAL '2 days', 'Room charge - 1 night', 'nights', 1.0,
   (SELECT base_rate FROM room_types rt JOIN bookings b ON b.room_type_id = rt.id WHERE b.booking_no='BK0002'),
   0,
   1.0 * (SELECT base_rate FROM room_types rt JOIN bookings b ON b.room_type_id = rt.id WHERE b.booking_no='BK0002'),
   1),
  -- Extra child charge
  ((SELECT id FROM bookings WHERE booking_no='BK0002'),
   'Fee', NULL, NOW() - INTERVAL '2 days', 'Extra child fee', 'nights', 1.0,
   (SELECT extra_child_fee FROM room_types rt JOIN bookings b ON b.room_type_id = rt.id WHERE b.booking_no='BK0002'),
   0,
   1.0 * (SELECT extra_child_fee FROM room_types rt JOIN bookings b ON b.room_type_id = rt.id WHERE b.booking_no='BK0002'),
   1),
  -- Airport transfer
  ((SELECT id FROM bookings WHERE booking_no='BK0002'),
   'Service', (SELECT id FROM services WHERE name='Airport Transfer'), NOW() - INTERVAL '1 day', 'Airport pickup and drop-off', 'transfers', 1.0,
   (SELECT price FROM services WHERE name='Airport Transfer'),
   0,
   1.0 * (SELECT price FROM services WHERE name='Airport Transfer'),
   1);

-- Payments - Sample payment records
-- Partial payment for active booking BK0001
INSERT INTO payments (booking_id, paid_at, payment_method, reference_no, amount, payer_name, notes, created_by)
VALUES
  -- Advance payment for BK0001
  ((SELECT id FROM bookings WHERE booking_no='BK0001'),
   NOW() - INTERVAL '30 minutes', 'Card', 'POS-001', 200000, 'Nguyen Van A', 'Advance payment via credit card', 1);

-- Full payment for completed booking BK0002
INSERT INTO payments (booking_id, paid_at, payment_method, reference_no, amount, payer_name, notes, created_by)
VALUES
  -- Full settlement for BK0002
  ((SELECT id FROM bookings WHERE booking_no='BK0002'),
   NOW() - INTERVAL '1 day', 'Cash', 'CASH-002', 
   (SELECT COALESCE(SUM(amount), 0) FROM booking_details WHERE booking_id = (SELECT id FROM bookings WHERE booking_no='BK0002')),
   'John Smith', 'Full payment in cash at checkout', 1);

-- ==========================================
-- SAMPLE DATA SUMMARY
-- ==========================================
-- Bookings: 2 (1 active hourly, 1 completed nightly)
-- Booking Details: 6 total (room charges, services, fees)
-- Payments: 2 (1 partial, 1 full payment)