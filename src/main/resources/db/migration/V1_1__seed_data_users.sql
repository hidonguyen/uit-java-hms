-- ==========================================
-- HOTEL MANAGEMENT SYSTEM SEED DATA
-- ==========================================

-- Users - Initial system users
INSERT INTO users (username, role, password_hash, status, created_by)
VALUES
  ('manager',      'Manager',      'i708tyacVZRf5wD8Y5dcxg==', 'Active', NULL)
ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, role, password_hash, status, created_by)
VALUES
  ('receptionist', 'Receptionist', 'sQGf4FLQC11L5l4QSFunKw==', 'Active', 1)
ON CONFLICT (username) DO NOTHING;

UPDATE users
SET created_by = 1
WHERE created_by IS NULL AND username = 'manager';

-- ==========================================
-- SAMPLE DATA SUMMARY
-- ==========================================
-- Users: 2 (manager, receptionist)