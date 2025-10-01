-- ==========================================
-- HOTEL MANAGEMENT SYSTEM SEED DATA
-- ==========================================

-- Guests - Sample guest data
INSERT INTO guests (name, gender, date_of_birth, nationality, phone, email, address, description, created_by)
VALUES
  ('Nguyen Van A', 'Male', '1990-05-10', 'Vietnamese', '0900000001', 'nguyenvana@example.com', 'Ho Chi Minh City', 'VIP guest', 1),
  ('Tran Thi B', 'Female', '1985-12-25', 'Vietnamese', '0900000002', 'tranthib@example.com', 'Hanoi', 'Regular guest', 1),
  ('John Smith', 'Male', '1988-03-15', 'American', '0900000003', 'john.smith@example.com', 'New York, USA', 'Business traveler', 1),
  ('Mary Johnson', 'Female', '1992-07-20', 'Canadian', '0900000004', 'mary.johnson@example.com', 'Toronto, Canada', 'Tourist', 1)

-- ==========================================
-- SAMPLE DATA SUMMARY
-- ==========================================
-- Guests: 4 (2 Vietnamese, 1 American, 1 Canadian)