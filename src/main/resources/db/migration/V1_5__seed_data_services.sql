-- ==========================================
-- HOTEL MANAGEMENT SYSTEM SEED DATA
-- ==========================================

-- Services - Hotel services available to guests
INSERT INTO services (name, unit, price, description, status, created_by)
VALUES
  ('Breakfast', 'set', 80000, 'Buffet breakfast (6:00 AM - 10:00 AM)', 'Active', 1),
  ('Laundry', 'kg', 50000, 'Same day laundry service', 'Active', 1),
  ('Airport Transfer', 'trip', 200000, 'Round trip airport transportation', 'Active', 1),
  ('Spa Massage', 'hour', 300000, 'Full body relaxation massage', 'Active', 1),
  ('Room Service', 'order', 25000, 'In-room dining service fee', 'Active', 1),
  ('Extra Bed', 'night', 150000, 'Additional bed in room', 'Active', 1),
  ('Late Checkout', 'hour', 50000, 'Extended checkout time', 'Active', 1)

-- ==========================================
-- SAMPLE DATA SUMMARY
-- ==========================================
-- Services: 7 (Breakfast, Laundry, Airport, Spa, Room Service, Extra Bed, Late Checkout)