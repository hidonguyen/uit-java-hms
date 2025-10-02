-- ==========================================
-- HOTEL MANAGEMENT SYSTEM SEED DATA
-- ==========================================

-- Room Types - Basic room categories
INSERT INTO room_types (code, name, base_occupancy, max_occupancy, base_rate, hour_rate, extra_adult_fee, extra_child_fee, description, created_by)
VALUES
  ('STD', 'Standard', 2, 3, 400000, 80000, 100000, 50000, 'Standard room with basic amenities', 1),
  ('DLX', 'Deluxe',   2, 4, 700000, 120000, 120000, 60000, 'Deluxe room with premium amenities', 1),
  ('SUI', 'Suite',    2, 6, 1200000, 200000, 150000, 75000, 'Luxury suite with separate living area', 1)
ON CONFLICT (code) DO NOTHING;

-- ==========================================
-- SAMPLE DATA SUMMARY
-- ==========================================
-- Room Types: 3 (Standard, Deluxe, Suite)