-- Insert sample users with BCrypt encrypted passwords
-- Password for all users: "password"
INSERT INTO users (username, password, name, role) VALUES 
('admin', '$2a$12$DRk37o4RmlG.YXwoDv6eieSS76tlMPHipg4H8IrRIfvSAbTHCNRiy', 'Gale Santos', 'ADMIN'),
('manager', '$2a$12$DRk37o4RmlG.YXwoDv6eieSS76tlMPHipg4H8IrRIfvSAbTHCNRiy', 'Carmen Shelton', 'MANAGER'),
('user1', '$2a$12$DRk37o4RmlG.YXwoDv6eieSS76tlMPHipg4H8IrRIfvSAbTHCNRiy', 'Ross Fox', 'USER'),
('user2', '$2a$12$DRk37o4RmlG.YXwoDv6eieSS76tlMPHipg4H8IrRIfvSAbTHCNRiy', 'Nancy Kennedy', 'USER');

-- Insert sample tasks
INSERT INTO tasks (title, description, status, assigned_user_id, scheduled_date_time, created_date) VALUES
('Setup Development Environment', 'Install and configure all necessary development tools', '2026-02-15 10:00:00', 'HIGH', 'PENDING', 3, CURRENT_TIMESTAMP),
('Review Code Changes', 'Review pull requests from last week', '2026-02-12 14:00:00', 'MEDIUM', 'PENDING', 2, CURRENT_TIMESTAMP),
('Update Documentation', 'Update API documentation with new endpoints', '2026-02-20 09:00:00', 'LOW', 'APPROVED', 3, CURRENT_TIMESTAMP),
('Database Migration', 'Migrate database to new schema version', '2026-02-18 16:00:00', 'HIGH', 'REJECTED', 2, CURRENT_TIMESTAMP),
('Client Meeting', 'Quarterly review meeting with client', '2026-02-14 11:00:00', 'MEDIUM', 'APPROVED', 2, CURRENT_TIMESTAMP);