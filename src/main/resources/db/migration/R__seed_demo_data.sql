-- ===== BEGIN BULK SEED SAFE RUN =====
BEGIN;

-- 1) Bổ sung nhiều ROOMs cho STD / DLX (nếu chưa có)
INSERT INTO rooms (name, room_type_id, description, housekeeping_status, status, created_by, updated_by)
SELECT '1' || lpad((100 + n)::text, 2, '0'),
       rt.id,
       'STD ' || '1' || lpad((100 + n)::text, 2, '0'),
       (ARRAY['Clean','Dirty','Clean','Clean'])[1 + (random()*3)::int],
       (ARRAY['Available','Occupied','Available'])[1 + (random()*2)::int],
       1, 1
FROM generate_series(3,60) g(n)
JOIN room_types rt ON rt.code = 'STD'
ON CONFLICT (name) DO NOTHING;

INSERT INTO rooms (name, room_type_id, description, housekeeping_status, status, created_by, updated_by)
SELECT '2' || lpad((100 + n)::text, 2, '0'),
       rt.id,
       'DLX ' || '2' || lpad((100 + n)::text, 2, '0'),
       (ARRAY['Clean','Dirty','Clean','Clean'])[1 + (random()*3)::int],
       (ARRAY['Available','Occupied','Available'])[1 + (random()*2)::int],
       1, 1
FROM generate_series(2,60) g(n)
JOIN room_types rt ON rt.code = 'DLX'
ON CONFLICT (name) DO NOTHING;

-- 2) Bổ sung SERVICES (nếu thiếu)
INSERT INTO services (name, unit, price, description, status, created_by, updated_by)
VALUES
  ('Airport Pickup', 'trip', 400000, 'Pickup from airport', 'Active', 1, 1),
  ('Dinner', 'set', 200000, 'Dinner set menu', 'Active', 1, 1),
  ('Mini Bar', 'usage', 100000, 'In-room mini bar', 'Active', 1, 1),
  ('Extra Bed', 'night', 150000, 'Additional bed per night', 'Active', 1, 1),
  ('Parking', 'day', 50000, 'Car parking', 'Active', 1, 1),
  ('Late Checkout', 'hour', 80000, 'Late checkout per hour', 'Active', 1, 1),
  ('Spa', 'session', 300000, 'Spa massage 60 mins', 'Active', 1, 1)
ON CONFLICT DO NOTHING;

-- 3) Thêm 200 GUESTs (nếu thiếu)
INSERT INTO guests (name, gender, date_of_birth, nationality, phone, email, address, description, created_by, updated_by)
SELECT
  'Guest ' || gs::text,
  (ARRAY['Male','Female'])[1 + (random()*1)::int],
  date '1970-01-01' + ((random()*15000)::int) * interval '1 day',
  'VN',
  '09' || to_char(100000000 + (random()*899999999)::int, 'FM000000000'),
  'guest' || gs::text || '@example.com',
  (ARRAY['HCMC','Hanoi','Danang','Hue','Can Tho','Nha Trang'])[1 + (random()*5)::int],
  (ARRAY['Demo','Frequent','Business','Tourist','Family','Backpacker'])[1 + (random()*5)::int],
  1, 1
FROM generate_series(1,200) gs
ON CONFLICT DO NOTHING;

-- 4) Tạo ~300 BOOKINGS gần 120 ngày qua -> Lưu vào TEMP TABLE để dùng tiếp
DROP TABLE IF EXISTS temp_new_bookings;
CREATE TEMP TABLE temp_new_bookings AS
WITH rnd_rooms AS (
  SELECT r.id AS room_id, r.room_type_id FROM rooms r
),
rnd_guests AS (
  SELECT g.id AS guest_id FROM guests g ORDER BY random() LIMIT 300
),
gen AS (
  SELECT
    gs AS seq,
    (now()::date - ((random()*120)::int))::timestamp
      + ((12 + (random()*10)::int) || ':00')::time AS ci,
    1 + (random()*2)::int AS nights
  FROM generate_series(1,300) gs
),
picked AS (
  SELECT
    g.seq, g.ci,
    (g.ci + (g.nights || ' days')::interval) AS co,
    r.room_id, r.room_type_id,
    gg.guest_id,
    1 + (random()*2)::int AS num_adults,
    (random()*1)::int AS num_children,
    (ARRAY['Day','Day','Hour'])[1 + (random()*2)::int] AS charge_type
  FROM gen g
  CROSS JOIN LATERAL (SELECT room_id, room_type_id FROM rnd_rooms ORDER BY random() LIMIT 1) r
  CROSS JOIN LATERAL (SELECT guest_id FROM rnd_guests ORDER BY random() LIMIT 1) gg
),
ins AS (
  INSERT INTO bookings
    (booking_no, charge_type, checkin, checkout, room_id, room_type_id, primary_guest_id,
     num_adults, num_children, status, payment_status, notes, created_by, updated_by)
  SELECT
    'AUTO' || to_char(seq, 'FM000000'),
    charge_type,
    ci, co,
    room_id, room_type_id, guest_id,
    num_adults, num_children,
    (ARRAY['CheckedIn','CheckedOut','CheckedOut'])[1 + (random()*2)::int],
    'Unpaid',
    'Seed auto booking ' || seq,
    1, 1
  FROM picked
  ON CONFLICT (booking_no) DO NOTHING
  RETURNING id, booking_no, checkin, checkout, room_id, room_type_id, primary_guest_id
)
SELECT * FROM ins;

-- 5) BOOKING_DETAILS cho các booking vừa tạo
-- 5.1 Room charge (tính theo số đêm; tối thiểu 1)
INSERT INTO booking_details
  (booking_id, type, service_id, issued_at, description, quantity, unit_price, discount_amount, amount, created_by, updated_by)
SELECT
  t.id,
  'Room',
  NULL,
  t.checkin,
  'Room charge',
  GREATEST(1, (t.checkout::date - t.checkin::date))::int AS nights,
  rt.base_rate,
  0,
  GREATEST(1, (t.checkout::date - t.checkin::date))::int * rt.base_rate,
  1, 1
FROM temp_new_bookings t
JOIN room_types rt ON rt.id = t.room_type_id;

-- 5.2 Service line 1 (75% booking có)
INSERT INTO booking_details
  (booking_id, type, service_id, issued_at, description, quantity, unit_price, discount_amount, amount, created_by, updated_by)
SELECT
  t.id,
  'Service',
  s.id,
  t.checkin + ((random()* (EXTRACT(EPOCH FROM (t.checkout - t.checkin)) ) )::int || ' seconds')::interval,
  'Extra service 1',
  q.qty,
  s.price,
  0,
  q.qty * s.price,
  1, 1
FROM temp_new_bookings t
JOIN LATERAL (SELECT id, price FROM services ORDER BY random() LIMIT 1) s ON true
JOIN LATERAL (SELECT 1 + (random()*2)::int AS qty) q ON true
WHERE random() < 0.75;

-- 5.3 Service line 2 (40% booking có)
INSERT INTO booking_details
  (booking_id, type, service_id, issued_at, description, quantity, unit_price, discount_amount, amount, created_by, updated_by)
SELECT
  t.id,
  'Service',
  s.id,
  t.checkin + ((random()* (EXTRACT(EPOCH FROM (t.checkout - t.checkin)) ) )::int || ' seconds')::interval,
  'Extra service 2',
  1,
  s.price,
  0,
  s.price,
  1, 1
FROM temp_new_bookings t
JOIN LATERAL (SELECT id, price FROM services ORDER BY random() LIMIT 1) s ON true
WHERE random() < 0.40;

-- 6) PAYMENTS: 80% full, 20% 50%
WITH sums AS (
  SELECT t.id AS booking_id, COALESCE(SUM(d.amount),0) AS total_amount
  FROM temp_new_bookings t
  JOIN booking_details d ON d.booking_id = t.id
  GROUP BY t.id
),
to_pay AS (
  SELECT s.booking_id,
         s.total_amount,
         CASE WHEN random() < 0.80 THEN s.total_amount ELSE round(s.total_amount * 0.5) END AS pay_amount,
         (ARRAY['Cash','Card','Transfer','EWallet'])[1 + (random()*3)::int] AS method
  FROM sums s
)
INSERT INTO payments
  (booking_id, paid_at, payment_method, reference_no, amount, payer_name, notes, created_by, updated_by)
SELECT
  p.booking_id,
  t.checkout,
  p.method,
  'REF' || p.booking_id,
  p.pay_amount,
  g.name,
  CASE WHEN p.pay_amount = p.total_amount THEN 'Full payment' ELSE 'Partial payment' END,
  1, 1
FROM to_pay p
JOIN temp_new_bookings t ON t.id = p.booking_id
JOIN guests g ON g.id = t.primary_guest_id;

-- 7) Cập nhật payment_status chỉ cho booking mới
WITH totals AS (
  SELECT t.id AS booking_id,
         COALESCE((SELECT SUM(amount) FROM booking_details d WHERE d.booking_id = t.id),0) AS total_amount,
         COALESCE((SELECT SUM(amount) FROM payments       p WHERE p.booking_id = t.id),0) AS paid_amount
  FROM temp_new_bookings t
)
UPDATE bookings b
SET payment_status = CASE
    WHEN t.paid_amount >= t.total_amount THEN 'Paid'
    WHEN t.paid_amount > 0 THEN 'PartiallyPaid'
    ELSE 'Unpaid'
  END
FROM totals t
WHERE b.id = t.booking_id;

COMMIT;
-- ===== END BULK SEED SAFE RUN =====
