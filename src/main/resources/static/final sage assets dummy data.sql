DROP DATABASE IF EXISTS Sage_asset_management_system;
CREATE DATABASE Sage_asset_management_system;
USE Sage_asset_management_system;

CREATE TABLE `user` (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    department VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    role ENUM('ADMIN','MANAGER','BORROWER'),
    password_hash VARCHAR(255),
    created_at DATETIME,
    status ENUM('ACTIVE','INACTIVE','DELETED')
);

CREATE TABLE asset (
    asset_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    category VARCHAR(255),
    serial_number VARCHAR(255) NOT NULL UNIQUE,
    acquisition_date DATE,
    cost DECIMAL(10,2),
    location VARCHAR(255),
    asset_condition VARCHAR(255),
    photo_path VARCHAR(255),
    created_at DATETIME,
    status ENUM('AVAILABLE','LOANED','RETIRED')
);

CREATE TABLE loan (
    loan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    request_date DATETIME,
    status ENUM('PENDING','APPROVED','REJECTED','RETURNED'),
    checkout_date DATETIME,
    due_date DATETIME,
    return_date DATETIME,
    approved_by BIGINT,

    CONSTRAINT fk_loan_asset FOREIGN KEY (asset_id) REFERENCES asset(asset_id),
    CONSTRAINT fk_loan_user FOREIGN KEY (user_id) REFERENCES `user`(user_id),
    CONSTRAINT fk_loan_approved_by FOREIGN KEY (approved_by) REFERENCES `user`(user_id)
);

INSERT INTO `user`
(name, department, email, role, password_hash, created_at, status)
VALUES
('Murembiwa', 'IT', 'murembiwa@sageassets.co.za', 'ADMIN', 'password', NOW(), 'ACTIVE'),
('Chanty', 'IT', 'chanty@sageassets.co.za', 'MANAGER', 'password', NOW(), 'ACTIVE'),
('Samantha Reed', 'FINANCE', 'samantha.reed@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Brian Foster', 'HR', 'brian.foster@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Michelle Ross', 'MARKETING', 'michelle.ross@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Andrew Lewis', 'OPERATIONS', 'andrew.lewis@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Rachel Morgan', 'IT', 'rachel.morgan@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Steven Price', 'FINANCE', 'steven.price@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Lauren Bennett', 'HR', 'lauren.bennett@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Nicholas Carter', 'MARKETING', 'nicholas.carter@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Melissa Ward', 'OPERATIONS', 'melissa.ward@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Justin Perry', 'IT', 'justin.perry@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Megan Hughes', 'FINANCE', 'megan.hughes@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Tyler Richardson', 'HR', 'tyler.richardson@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Courtney Simmons', 'MARKETING', 'courtney.simmons@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Brandon Powell', 'OPERATIONS', 'brandon.powell@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Rebecca Long', 'IT', 'rebecca.long@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Sean Patterson', 'FINANCE', 'sean.patterson@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Heather Bell', 'HR', 'heather.bell@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Dylan Murphy', 'MARKETING', 'dylan.murphy@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE'),
('Victoria Bailey', 'OPERATIONS', 'victoria.bailey@sageassets.co.za', 'BORROWER', 'password', NOW(), 'ACTIVE');

INSERT INTO asset
(title, category, serial_number, acquisition_date, cost, location, asset_condition, photo_path, created_at, status)
VALUES
('Dell Latitude 5440','Laptop','DL5440-001','2025-01-10',18000,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Dell Latitude 5440','Laptop','DL5440-002','2025-01-10',18000,'IT Office','Excellent',NULL,NOW(),'LOANED'),
('Dell Latitude 5440','Laptop','DL5440-003','2025-01-10',18000,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Dell Latitude 5440','Laptop','DL5440-004','2025-01-10',18000,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Dell Latitude 5440','Laptop','DL5440-005','2025-01-10',18000,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('HP ProBook 450','Laptop','HP450-001','2024-11-12',16500,'IT Office','Good',NULL,NOW(),'AVAILABLE'),
('HP ProBook 450','Laptop','HP450-002','2024-11-12',16500,'IT Office','Good',NULL,NOW(),'LOANED'),
('HP ProBook 450','Laptop','HP450-003','2024-11-12',16500,'IT Office','Good',NULL,NOW(),'AVAILABLE'),
('HP ProBook 450','Laptop','HP450-004','2024-11-12',16500,'IT Office','Good',NULL,NOW(),'AVAILABLE'),
('HP ProBook 450','Laptop','HP450-005','2024-11-12',16500,'IT Office','Good',NULL,NOW(),'AVAILABLE'),
('Lenovo ThinkPad E14','Laptop','LENE14-001','2024-10-01',17000,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Lenovo ThinkPad E14','Laptop','LENE14-002','2024-10-01',17000,'IT Office','Excellent',NULL,NOW(),'LOANED'),
('Lenovo ThinkPad E14','Laptop','LENE14-003','2024-10-01',17000,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Lenovo ThinkPad E14','Laptop','LENE14-004','2024-10-01',17000,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Lenovo ThinkPad E14','Laptop','LENE14-005','2024-10-01',17000,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('MacBook Air M2','Laptop','MBA-M2-001','2025-02-01',25000,'Executive Office','Excellent',NULL,NOW(),'LOANED'),
('MacBook Air M2','Laptop','MBA-M2-002','2025-02-01',25000,'Executive Office','Excellent',NULL,NOW(),'AVAILABLE'),
('MacBook Air M2','Laptop','MBA-M2-003','2025-02-01',25000,'Executive Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Canon EOS R50','Camera','CANON-001','2025-03-15',14500,'Media Room','Excellent',NULL,NOW(),'AVAILABLE'),
('Canon EOS R50','Camera','CANON-002','2025-03-15',14500,'Media Room','Excellent',NULL,NOW(),'LOANED'),
('Canon EOS R50','Camera','CANON-003','2025-03-15',14500,'Media Room','Excellent',NULL,NOW(),'AVAILABLE'),
('Sony ZV-E10','Camera','SONY-001','2025-03-15',13500,'Media Room','Excellent',NULL,NOW(),'AVAILABLE'),
('Sony ZV-E10','Camera','SONY-002','2025-03-15',13500,'Media Room','Excellent',NULL,NOW(),'LOANED'),
('Rode Wireless Pro','Audio','RODE-001','2025-04-01',8500,'Media Room','Excellent',NULL,NOW(),'AVAILABLE'),
('Rode Wireless Pro','Audio','RODE-002','2025-04-01',8500,'Media Room','Excellent',NULL,NOW(),'LOANED'),
('Rode Wireless Pro','Audio','RODE-003','2025-04-01',8500,'Media Room','Excellent',NULL,NOW(),'AVAILABLE'),
('Samsung 27 Monitor','Monitor','MON-001','2024-06-01',4200,'Training Room','Good',NULL,NOW(),'AVAILABLE'),
('Samsung 27 Monitor','Monitor','MON-002','2024-06-01',4200,'Training Room','Good',NULL,NOW(),'AVAILABLE'),
('Samsung 27 Monitor','Monitor','MON-003','2024-06-01',4200,'Training Room','Good',NULL,NOW(),'AVAILABLE'),
('Samsung 27 Monitor','Monitor','MON-004','2024-06-01',4200,'Training Room','Good',NULL,NOW(),'AVAILABLE'),
('Samsung 27 Monitor','Monitor','MON-005','2024-06-01',4200,'Training Room','Good',NULL,NOW(),'AVAILABLE'),
('Dell OptiPlex 7010','Desktop','OPTI-001','2020-01-01',9000,'Storage','Fair',NULL,NOW(),'RETIRED'),
('Dell OptiPlex 7010','Desktop','OPTI-002','2020-01-01',9000,'Storage','Fair',NULL,NOW(),'RETIRED'),
('Dell OptiPlex 7010','Desktop','OPTI-003','2020-01-01',9000,'Storage','Fair',NULL,NOW(),'RETIRED'),
('Cisco Switch 24 Port','Network','SWITCH-001','2024-05-01',9500,'Server Room','Excellent',NULL,NOW(),'AVAILABLE'),
('Cisco Switch 24 Port','Network','SWITCH-002','2024-05-01',9500,'Server Room','Excellent',NULL,NOW(),'AVAILABLE'),
('UPS APC 1500VA','Power','UPS-001','2024-08-01',5000,'Server Room','Good',NULL,NOW(),'AVAILABLE'),
('UPS APC 1500VA','Power','UPS-002','2024-08-01',5000,'Server Room','Good',NULL,NOW(),'AVAILABLE'),
('iPad Pro 11','Tablet','IPAD-001','2025-01-15',18000,'Marketing','Excellent',NULL,NOW(),'AVAILABLE'),
('iPad Pro 11','Tablet','IPAD-002','2025-01-15',18000,'Marketing','Excellent',NULL,NOW(),'LOANED'),
('Logitech MX Keys','Accessory','MXKEY-001','2025-03-01',2500,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Logitech MX Keys','Accessory','MXKEY-002','2025-03-01',2500,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Logitech MX Master 3','Accessory','MXMOUSE-001','2025-03-01',1800,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Logitech MX Master 3','Accessory','MXMOUSE-002','2025-03-01',1800,'IT Office','Excellent',NULL,NOW(),'AVAILABLE'),
('Poly Studio Webcam','Accessory','WEBCAM-001','2025-04-10',3500,'Meeting Room','Excellent',NULL,NOW(),'AVAILABLE'),
('Poly Studio Webcam','Accessory','WEBCAM-002','2025-04-10',3500,'Meeting Room','Excellent',NULL,NOW(),'AVAILABLE'),
('Epson Projector X49','Projector','PROJ-001','2024-07-01',7200,'Boardroom','Good',NULL,NOW(),'AVAILABLE'),
('Epson Projector X49','Projector','PROJ-002','2024-07-01',7200,'Boardroom','Good',NULL,NOW(),'LOANED');

INSERT INTO loan
(asset_id, user_id, request_date, status, checkout_date, due_date, return_date, approved_by)
VALUES
(2, 4, '2026-06-01 09:00:00', 'APPROVED', '2026-06-02 09:00:00', '2026-06-23 09:00:00', NULL, 2),
(7, 5, '2026-06-02 10:00:00', 'APPROVED', '2026-06-03 10:00:00', '2026-06-24 10:00:00', NULL, 2),
(12, 6, '2026-06-03 11:00:00', 'APPROVED', '2026-06-04 11:00:00', '2026-06-25 11:00:00', NULL, 2),
(16, 7, '2026-06-04 08:30:00', 'APPROVED', '2026-06-05 08:30:00', '2026-06-26 08:30:00', NULL, 2),
(20, 8, '2026-06-05 09:15:00', 'APPROVED', '2026-06-06 09:15:00', '2026-06-27 09:15:00', NULL, 2),
(23, 9, '2026-06-06 13:00:00', 'APPROVED', '2026-06-07 13:00:00', '2026-06-28 13:00:00', NULL, 2),
(25, 10, '2026-06-07 14:00:00', 'APPROVED', '2026-06-08 14:00:00', '2026-06-29 14:00:00', NULL, 2),
(40, 11, '2026-06-08 15:00:00', 'APPROVED', '2026-06-09 15:00:00', '2026-06-30 15:00:00', NULL, 2),
(48, 12, '2026-06-09 16:00:00', 'APPROVED', '2026-06-10 16:00:00', '2026-07-01 16:00:00', NULL, 2),

(1, 13, '2026-06-15 08:00:00', 'PENDING', NULL, NULL, NULL, NULL),
(3, 14, '2026-06-15 09:00:00', 'PENDING', NULL, NULL, NULL, NULL),
(4, 15, '2026-06-16 10:00:00', 'PENDING', NULL, NULL, NULL, NULL),
(5, 16, '2026-06-16 11:00:00', 'PENDING', NULL, NULL, NULL, NULL),
(6, 17, '2026-06-17 12:00:00', 'PENDING', NULL, NULL, NULL, NULL),
(8, 18, '2026-06-17 13:00:00', 'PENDING', NULL, NULL, NULL, NULL),

(9, 19, '2026-06-01 09:00:00', 'REJECTED', NULL, NULL, NULL, 2),
(10, 20, '2026-06-02 10:00:00', 'REJECTED', NULL, NULL, NULL, 2),
(11, 21, '2026-06-03 11:00:00', 'REJECTED', NULL, NULL, NULL, 2),

(27, 9, '2026-05-01 09:00:00', 'RETURNED', '2026-05-02 09:00:00', '2026-05-23 09:00:00', '2026-05-20 15:00:00', 2),
(28, 10, '2026-05-03 09:00:00', 'RETURNED', '2026-05-04 09:00:00', '2026-05-25 09:00:00', '2026-05-21 10:00:00', 2);

CREATE TABLE auditLog (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    entity_type VARCHAR(255),
    entity_id BIGINT,
    action VARCHAR(255),
    timestamp DATETIME,
    old_value TEXT,
    new_value TEXT,

    CONSTRAINT fk_auditlog_user
        FOREIGN KEY (user_id) REFERENCES `user`(user_id)
);

INSERT INTO auditLog
(user_id, entity_type, entity_id, action, timestamp, old_value, new_value)
VALUES
(1, 'USER', 3, 'CREATE_USER', NOW(), NULL, 'Created borrower Samantha Reed'),
(1, 'USER', 4, 'CREATE_USER', NOW(), NULL, 'Created borrower Brian Foster'),

(2, 'ASSET', 1, 'CREATE_ASSET', NOW(), NULL, 'Created Dell Latitude 5440'),
(2, 'ASSET', 2, 'UPDATE_ASSET_STATUS', NOW(), 'AVAILABLE', 'LOANED'),
(2, 'ASSET', 32, 'RETIRE_ASSET', NOW(), 'AVAILABLE', 'RETIRED'),

(2, 'LOAN', 1, 'APPROVE_LOAN', NOW(), 'PENDING', 'APPROVED'),
(2, 'LOAN', 2, 'APPROVE_LOAN', NOW(), 'PENDING', 'APPROVED'),
(2, 'LOAN', 16, 'REJECT_LOAN', NOW(), 'PENDING', 'REJECTED'),
(2, 'LOAN', 19, 'RETURN_LOAN', NOW(), 'APPROVED', 'RETURNED');


SELECT COUNT(*) AS total_users FROM `user`;
SELECT COUNT(*) AS total_assets FROM asset;
SELECT COUNT(*) AS total_loans FROM loan;