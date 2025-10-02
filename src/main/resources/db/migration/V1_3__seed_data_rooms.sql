-- ==========================================
-- HOTEL MANAGEMENT SYSTEM SEED DATA
-- ==========================================

-- Rooms - Physical rooms in the hotel
INSERT INTO rooms (name, room_type_id, description, housekeeping_status, status, created_by)
VALUES
  ('101', (SELECT id FROM room_types WHERE code='STD'), 'Standard room on 1st floor', 'Clean', 'Available', 1),
  ('102', (SELECT id FROM room_types WHERE code='STD'), 'Standard room on 1st floor', 'Clean', 'Available', 1),
  ('103', (SELECT id FROM room_types WHERE code='STD'), 'Standard room on 1st floor', 'Clean', 'Available', 1),
  ('201', (SELECT id FROM room_types WHERE code='DLX'), 'Deluxe room on 2nd floor', 'Clean', 'Available', 1),
  ('202', (SELECT id FROM room_types WHERE code='DLX'), 'Deluxe room on 2nd floor', 'Clean', 'Available', 1),
  ('301', (SELECT id FROM room_types WHERE code='SUI'), 'Suite on 3rd floor', 'Clean', 'Available', 1)
ON CONFLICT (name) DO NOTHING;

-- ==========================================
-- SAMPLE DATA SUMMARY
-- ==========================================
-- Rooms: 6 (101-103: Standard, 201-202: Deluxe, 301: Suite)