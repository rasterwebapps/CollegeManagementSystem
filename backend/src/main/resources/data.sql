-- =============================================================================
-- College Management System - Comprehensive Mock Data for Local Development (H2)
-- =============================================================================
-- Auto-executed by Spring Boot on startup when using H2 embedded database.
-- Covers all 38 entities with rich, interconnected test data for every module.
-- =============================================================================

-- =============================================
-- TIER 0: No foreign-key dependencies
-- =============================================

-- Departments (7)
INSERT INTO departments (id, name, code, created_at, updated_at) VALUES
(1, 'Computer Science & Engineering', 'CSE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Electronics & Communication Engineering', 'ECE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Mechanical Engineering', 'ME', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Civil Engineering', 'CE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Information Technology', 'IT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Electrical Engineering', 'EE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Mathematics & Computing', 'MC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Academic Years (3)
INSERT INTO academic_years (id, name, start_date, end_date, active, created_at, updated_at) VALUES
(1, '2023-24', '2023-07-01', '2024-06-30', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '2024-25', '2024-07-01', '2025-06-30', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, '2025-26', '2025-07-01', '2026-06-30', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Lab Types (8)
INSERT INTO lab_types (id, name, description, created_at, updated_at) VALUES
(1, 'Computer Lab',      'General-purpose computer laboratory for programming and software development',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Electronics Lab',   'Laboratory for electronic circuits, digital systems, and embedded systems',          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Physics Lab',       'General physics laboratory for engineering physics experiments',                     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Networking Lab',    'Computer networking, cloud computing, and cybersecurity laboratory',                  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Workshop',          'Mechanical and electrical workshop for hands-on fabrication and machining',          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Chemistry Lab',     'General and applied chemistry laboratory',                                           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Simulation Lab',    'CAD/CAM simulation and modeling laboratory',                                         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'Research Lab',      'Advanced research laboratory for postgraduate and doctoral research',                CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =============================================
-- TIER 1: Depends on Tier 0
-- =============================================

-- Programs (10)
INSERT INTO programs (id, name, code, department_id, duration_years, degree_type, created_at, updated_at) VALUES
(1,  'B.Tech Computer Science & Engineering',    'BTCSE', 1, 4, 'B.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  'B.Tech Electronics & Communication',       'BTECE', 2, 4, 'B.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  'B.Tech Mechanical Engineering',            'BTME',  3, 4, 'B.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  'B.Tech Civil Engineering',                 'BTCE',  4, 4, 'B.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  'B.Tech Information Technology',            'BTIT',  5, 4, 'B.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  'M.Tech Computer Science',                  'MTCSE', 1, 2, 'M.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  'M.Tech VLSI Design',                       'MTVLSI',2, 2, 'M.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  'B.Tech Electrical Engineering',            'BTEE',  6, 4, 'B.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  'B.Tech Mathematics & Computing',           'BTMC',  7, 4, 'B.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'M.Tech Data Science',                      'MTDS',  1, 2, 'M.Tech', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Semesters (6: two per academic year)
INSERT INTO semesters (id, name, semester_number, academic_year_id, start_date, end_date, created_at, updated_at) VALUES
(1, 'Odd Semester 2023-24',  1, 1, '2023-07-15', '2023-12-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Even Semester 2023-24', 2, 1, '2024-01-10', '2024-06-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Odd Semester 2024-25',  1, 2, '2024-07-15', '2024-12-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Even Semester 2024-25', 2, 2, '2025-01-10', '2025-06-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Odd Semester 2025-26',  1, 3, '2025-07-15', '2025-12-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Even Semester 2025-26', 2, 3, '2026-01-10', '2026-06-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Labs (12)
INSERT INTO labs (id, name, code, lab_type_id, department_id, location, capacity, status, created_at, updated_at) VALUES
(1,  'Programming Lab',            'PL-101',  1, 1, 'Block A, Room 101', 60, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  'Data Structures Lab',        'DS-102',  1, 1, 'Block A, Room 102', 40, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  'Digital Electronics Lab',    'DE-201',  2, 2, 'Block B, Room 201', 30, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  'Communication Systems Lab',  'CS-202',  2, 2, 'Block B, Room 202', 30, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  'Networking & Security Lab',  'NS-103',  4, 1, 'Block A, Room 103', 40, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  'Engineering Workshop',       'WS-301',  5, 3, 'Workshop Building',  50, 'AVAILABLE',        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  'AI & Machine Learning Lab',  'AI-104',  1, 1, 'Block A, Room 104', 35, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  'Physics Lab',                'PH-401',  3, 4, 'Block D, Room 401', 40, 'UNDER_MAINTENANCE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  'Chemistry Lab',              'CH-402',  6, 4, 'Block D, Room 402', 35, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'CAD/CAM Simulation Lab',     'SIM-302', 7, 3, 'Block C, Room 302', 30, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'Advanced Research Lab',      'RL-105',  8, 1, 'Block A, Room 105', 20, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'Electrical Machines Lab',    'EM-501',  2, 6, 'Block E, Room 501', 25, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Calendar Events (15)
INSERT INTO calendar_events (id, title, description, event_date, event_type, academic_year_id, created_at, updated_at) VALUES
(1,  'Orientation Day',                'New student orientation and campus tour',                 '2025-07-16', 'ACADEMIC', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  'Independence Day',              'National holiday — No classes',                           '2025-08-15', 'HOLIDAY',  3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  'Mid-Semester Exams Begin',      'Mid-semester examination week starts',                    '2025-09-22', 'EXAM',     3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  'Technical Fest - TechnoVista',   'Annual inter-college technical festival',                 '2025-10-10', 'CULTURAL', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  'Diwali Break',                  'Diwali vacation period',                                  '2025-10-20', 'HOLIDAY',  3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  'End-Semester Exams Begin',      'Final examinations for odd semester',                     '2025-11-25', 'EXAM',     3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  'Republic Day',                  'National holiday — Republic Day celebrations',            '2026-01-26', 'HOLIDAY',  3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  'Annual Sports Day',             'Inter-department sports competition and cultural meet',   '2026-02-15', 'CULTURAL', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  'Workshop on AI/ML',             'Two-day workshop on Artificial Intelligence and ML',      '2025-08-25', 'ACADEMIC', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Hackathon - CodeStorm 2025',    '24-hour inter-college hackathon',                         '2025-09-05', 'CULTURAL', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'Guest Lecture - Industry 4.0',  'Guest lecture by industry expert on Industry 4.0',        '2025-09-15', 'ACADEMIC', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'Placement Drive - TCS',         'TCS campus recruitment drive',                            '2025-10-05', 'ACADEMIC', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'Gandhinagar Cultural Fest',     'Inter-college cultural festival',                         '2025-11-05', 'CULTURAL', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'Winter Break Start',            'Winter vacation begins',                                  '2025-12-20', 'HOLIDAY',  3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'Even Semester Registration',    'Registration for even semester 2025-26',                  '2026-01-05', 'ACADEMIC', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =============================================
-- TIER 2: Depends on Tier 1
-- =============================================

-- Courses (20)
INSERT INTO courses (id, name, code, program_id, credits, course_type, created_at, updated_at) VALUES
(1,  'Data Structures & Algorithms',    'CS201',  1, 4, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  'Database Management Systems',     'CS301',  1, 4, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  'Operating Systems',               'CS302',  1, 3, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  'Computer Networks',               'CS401',  1, 3, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  'Artificial Intelligence',         'CS402',  1, 3, 'ELECTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  'Digital Signal Processing',       'EC301',  2, 4, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  'VLSI Design',                     'EC401',  2, 3, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  'Thermodynamics',                  'ME201',  3, 4, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  'Structural Analysis',             'CE301',  4, 4, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Web Technologies',                'IT301',  5, 3, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'Machine Learning',                'CS501',  6, 4, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'Software Engineering',            'CS303',  1, 3, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'Microprocessors & Microcontrollers', 'EC302', 2, 4, 'CORE',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'Fluid Mechanics',                 'ME301',  3, 3, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'Concrete Technology',             'CE401',  4, 3, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 'Cybersecurity Fundamentals',      'IT401',  5, 3, 'ELECTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 'Deep Learning',                   'CS502',  6, 4, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 'Power Systems',                   'EE301',  8, 4, 'CORE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 'Numerical Methods',              'MC201',  9, 3, 'CORE',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'Cloud Computing',                'CS403',  1, 3, 'ELECTIVE',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Faculty Profiles (15)
INSERT INTO faculty_profiles (id, keycloak_id, employee_code, first_name, last_name, email, phone, department_id, designation, joining_date, status, created_at, updated_at) VALUES
(1,  'kc-fac-001', 'FAC001', 'Rajesh',    'Kumar',      'rajesh.kumar@cms.edu',      '9876543210', 1, 'Professor',            '2015-07-01', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  'kc-fac-002', 'FAC002', 'Priya',     'Sharma',     'priya.sharma@cms.edu',      '9876543211', 1, 'Associate Professor',  '2017-08-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  'kc-fac-003', 'FAC003', 'Suresh',    'Patel',      'suresh.patel@cms.edu',      '9876543212', 2, 'Professor',            '2014-06-01', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  'kc-fac-004', 'FAC004', 'Anita',     'Desai',      'anita.desai@cms.edu',       '9876543213', 2, 'Assistant Professor',  '2020-01-10', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  'kc-fac-005', 'FAC005', 'Vikram',    'Singh',      'vikram.singh@cms.edu',      '9876543214', 3, 'Professor',            '2012-03-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  'kc-fac-006', 'FAC006', 'Meera',     'Nair',       'meera.nair@cms.edu',        '9876543215', 4, 'Associate Professor',  '2018-07-20', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  'kc-fac-007', 'FAC007', 'Arjun',     'Reddy',      'arjun.reddy@cms.edu',       '9876543216', 5, 'Assistant Professor',  '2021-06-01', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  'kc-fac-008', 'FAC008', 'Deepa',     'Gupta',      'deepa.gupta@cms.edu',       '9876543217', 1, 'Assistant Professor',  '2022-01-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  'kc-fac-009', 'FAC009', 'Ramesh',    'Iyer',       'ramesh.iyer@cms.edu',       '9876543218', 6, 'Professor',            '2010-07-01', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'kc-fac-010', 'FAC010', 'Sunita',    'Bhatt',      'sunita.bhatt@cms.edu',      '9876543219', 7, 'Associate Professor',  '2016-08-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'kc-fac-011', 'FAC011', 'Anil',      'Chopra',     'anil.chopra@cms.edu',       '9876543220', 3, 'Assistant Professor',  '2023-01-10', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'kc-fac-012', 'FAC012', 'Lakshmi',   'Venkatesh',  'lakshmi.venkatesh@cms.edu', '9876543221', 2, 'Associate Professor',  '2019-07-01', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'kc-fac-013', 'FAC013', 'Prakash',   'Jha',        'prakash.jha@cms.edu',       '9876543222', 4, 'Assistant Professor',  '2022-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'kc-fac-014', 'FAC014', 'Kavitha',   'Raman',      'kavitha.raman@cms.edu',     '9876543223', 5, 'Professor',            '2013-03-01', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'kc-fac-015', 'FAC015', 'Harish',    'Srinivasan', 'harish.srinivasan@cms.edu', '9876543224', 1, 'Professor',            '2011-07-01', 'ON_LEAVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Student Profiles (30)
INSERT INTO student_profiles (id, keycloak_id, enrollment_number, first_name, last_name, email, phone, program_id, department_id, current_semester, admission_date, status, created_at, updated_at) VALUES
(1,  'kc-stu-001', 'ENR2024001', 'Aarav',     'Mehta',     'aarav.mehta@student.cms.edu',      '9123456701', 1, 1, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  'kc-stu-002', 'ENR2024002', 'Ishita',    'Verma',     'ishita.verma@student.cms.edu',     '9123456702', 1, 1, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  'kc-stu-003', 'ENR2024003', 'Rohan',     'Joshi',     'rohan.joshi@student.cms.edu',      '9123456703', 1, 1, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  'kc-stu-004', 'ENR2024004', 'Sneha',     'Iyer',      'sneha.iyer@student.cms.edu',       '9123456704', 2, 2, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  'kc-stu-005', 'ENR2024005', 'Karan',     'Thakur',    'karan.thakur@student.cms.edu',     '9123456705', 2, 2, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  'kc-stu-006', 'ENR2024006', 'Divya',     'Rao',       'divya.rao@student.cms.edu',        '9123456706', 3, 3, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  'kc-stu-007', 'ENR2024007', 'Amit',      'Saxena',    'amit.saxena@student.cms.edu',      '9123456707', 4, 4, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  'kc-stu-008', 'ENR2024008', 'Neha',      'Kapoor',    'neha.kapoor@student.cms.edu',      '9123456708', 5, 5, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  'kc-stu-009', 'ENR2025001', 'Rahul',     'Mishra',    'rahul.mishra@student.cms.edu',     '9123456709', 1, 1, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'kc-stu-010', 'ENR2025002', 'Pooja',     'Agarwal',   'pooja.agarwal@student.cms.edu',    '9123456710', 1, 1, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'kc-stu-011', 'ENR2025003', 'Vikrant',   'Chauhan',   'vikrant.chauhan@student.cms.edu',  '9123456711', 2, 2, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'kc-stu-012', 'ENR2025004', 'Anjali',    'Pandey',    'anjali.pandey@student.cms.edu',    '9123456712', 3, 3, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'kc-stu-013', 'ENR2023001', 'Sunil',     'Bhat',      'sunil.bhat@student.cms.edu',       '9123456713', 6, 1, 3, '2023-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'kc-stu-014', 'ENR2024009', 'Kavita',    'Menon',     'kavita.menon@student.cms.edu',     '9123456714', 5, 5, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 'kc-stu-015', 'ENR2024010', 'Nikhil',    'Kulkarni',  'nikhil.kulkarni@student.cms.edu',  '9123456715', 1, 1, 3, '2024-07-15', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 'kc-stu-016', 'ENR2024011', 'Priyanka',  'Das',       'priyanka.das@student.cms.edu',     '9123456716', 8, 6, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 'kc-stu-017', 'ENR2024012', 'Siddharth', 'Malhotra',  'siddharth.malhotra@student.cms.edu','9123456717',9, 7, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 'kc-stu-018', 'ENR2025005', 'Tanvi',     'Bose',      'tanvi.bose@student.cms.edu',       '9123456718', 4, 4, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 'kc-stu-019', 'ENR2025006', 'Mohit',     'Khandelwal','mohit.khandelwal@student.cms.edu', '9123456719', 5, 5, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'kc-stu-020', 'ENR2025007', 'Riya',      'Sengupta',  'riya.sengupta@student.cms.edu',    '9123456720', 2, 2, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 'kc-stu-021', 'ENR2023002', 'Vivek',     'Tiwari',    'vivek.tiwari@student.cms.edu',     '9123456721', 10,1, 3, '2023-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 'kc-stu-022', 'ENR2024013', 'Shruti',    'Hegde',     'shruti.hegde@student.cms.edu',     '9123456722', 1, 1, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'kc-stu-023', 'ENR2024014', 'Aditya',    'Goel',      'aditya.goel@student.cms.edu',      '9123456723', 1, 1, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 'kc-stu-024', 'ENR2024015', 'Meghna',    'Chatterjee','meghna.chatterjee@student.cms.edu','9123456724', 2, 2, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 'kc-stu-025', 'ENR2024016', 'Aryan',     'Dubey',     'aryan.dubey@student.cms.edu',      '9123456725', 3, 3, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 'kc-stu-026', 'ENR2025008', 'Nandini',   'Pillai',    'nandini.pillai@student.cms.edu',   '9123456726', 8, 6, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 'kc-stu-027', 'ENR2025009', 'Gaurav',    'Kashyap',   'gaurav.kashyap@student.cms.edu',   '9123456727', 9, 7, 1, '2025-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 'kc-stu-028', 'ENR2024017', 'Swati',     'Mishra',    'swati.mishra@student.cms.edu',     '9123456728', 7, 2, 3, '2024-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(29, 'kc-stu-029', 'ENR2024018', 'Tushar',    'Bhardwaj',  'tushar.bhardwaj@student.cms.edu',  '9123456729', 1, 1, 3, '2024-07-15', 'SUSPENDED',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 'kc-stu-030', 'ENR2023003', 'Pallavi',   'Sinha',     'pallavi.sinha@student.cms.edu',     '9123456730', 6, 1, 3, '2023-07-15', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Equipment (20)
INSERT INTO equipments (id, lab_id, name, model_number, serial_number, purchase_date, purchase_cost, warranty_expiry, status, created_at, updated_at) VALUES
(1,  1,  'Dell OptiPlex 7090',            'OP7090-i7',    'SN-DELL-001',  '2023-06-15', 85000.00,   '2026-06-15', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1,  'Dell OptiPlex 7090',            'OP7090-i7',    'SN-DELL-002',  '2023-06-15', 85000.00,   '2026-06-15', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  2,  'HP ProDesk 400 G7',             'PD400G7',      'SN-HP-001',    '2022-01-10', 72000.00,   '2025-01-10', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  3,  'Tektronix Oscilloscope',        'TBS2104B',     'SN-TEK-001',   '2021-03-20', 125000.00,  '2024-03-20', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  3,  'Keysight Digital Multimeter',   '34465A',       'SN-KEY-001',   '2022-05-10', 65000.00,   '2025-05-10', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  4,  'Spectrum Analyzer',             'RSA306B',      'SN-RSA-001',   '2023-02-28', 350000.00,  '2026-02-28', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  5,  'Cisco Catalyst Switch',         'C9200L-24T',   'SN-CISCO-001', '2023-08-01', 195000.00,  '2026-08-01', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  5,  'Cisco Router ISR',              'ISR4321',      'SN-CISCO-002', '2023-08-01', 280000.00,  '2026-08-01', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  6,  'Lathe Machine',                 'LT-300',       'SN-LT-001',    '2019-11-15', 450000.00,  '2024-11-15', 'UNDER_REPAIR',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 6,  'CNC Milling Machine',           'VMC-400',      'SN-CNC-001',   '2021-06-20', 1200000.00, '2026-06-20', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 7,  'NVIDIA DGX Workstation',        'DGX-A100',     'SN-NV-001',    '2024-01-15', 2500000.00, '2027-01-15', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 8,  'Optical Bench Setup',           'OB-200',       'SN-OPT-001',   '2020-08-10', 95000.00,   '2023-08-10', 'DECOMMISSIONED',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 1,  'Dell Monitor 27 inch',          'U2723QE',      'SN-MON-001',   '2023-06-15', 42000.00,   '2026-06-15', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 2,  'HP ProDesk 400 G7',             'PD400G7',      'SN-HP-002',    '2022-01-10', 72000.00,   '2025-01-10', 'UNDER_REPAIR',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 9,  'Chemical Fume Hood',            'FH-200X',      'SN-FH-001',    '2022-03-15', 180000.00,  '2027-03-15', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 9,  'Analytical Balance',            'AB-220',       'SN-AB-001',    '2023-01-20', 95000.00,   '2028-01-20', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 10, 'CAD Workstation',               'HP-Z6',        'SN-HPZ6-001',  '2023-09-01', 175000.00,  '2026-09-01', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 11, 'GPU Server Rack',               'DGXS-2',       'SN-NV-002',    '2024-06-01', 3500000.00, '2027-06-01', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 12, 'DC Motor Training Kit',         'DCM-500',      'SN-DCM-001',   '2021-07-15', 65000.00,   '2024-07-15', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 12, 'Power Electronics Trainer',     'PET-300',      'SN-PET-001',   '2022-02-10', 120000.00,  '2025-02-10', 'OPERATIONAL',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Consumable Stocks (15)
INSERT INTO consumable_stocks (id, lab_id, item_name, quantity, unit, minimum_threshold, unit_cost, last_restocked, status, created_at, updated_at) VALUES
(1,  1,  'Ethernet Cable CAT6 (3m)',       200, 'pieces',  50,  150.00,   '2025-06-01', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1,  'USB Flash Drive 32GB',           80,  'pieces',  20,  450.00,   '2025-05-15', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  3,  'Breadboard (830 points)',        45,  'pieces',  10,  180.00,   '2025-04-20', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  3,  'Resistor Kit (assorted)',        30,  'kits',    5,   350.00,   '2025-03-10', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  3,  'LED Pack (assorted colors)',     100, 'packs',   20,  120.00,   '2025-04-20', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  5,  'RJ45 Connectors',               500, 'pieces',  100, 15.00,    '2025-06-10', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  6,  'Cutting Tool Inserts',           8,   'pieces',  10,  2200.00,  '2025-02-01', 'LOW_STOCK',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  6,  'Coolant Fluid (1L)',             3,   'liters',  5,   850.00,   '2025-01-15', 'LOW_STOCK',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  9,  'pH Paper Strips',               250, 'strips',  50,  25.00,    '2025-05-20', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 9,  'Beakers 250ml',                 40,  'pieces',  10,  180.00,   '2025-04-10', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 9,  'HCl Solution (500ml)',           5,   'bottles', 8,   320.00,   '2025-01-20', 'LOW_STOCK',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 7,  'HDMI Cables (2m)',              30,  'pieces',  10,  350.00,   '2025-06-15', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 10, 'Printer Filament PLA (1kg)',    12,  'spools',  5,   1200.00,  '2025-05-01', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 12, 'Fuse Wire Pack',               60,  'packs',   15,  45.00,    '2025-06-01', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1,  'Keyboard & Mouse Combo',        15,  'sets',    10,  800.00,   '2025-03-15', 'IN_STOCK',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Lab Staff Assignments (10)
INSERT INTO lab_staff_assignments (id, lab_id, staff_keycloak_id, staff_role, assigned_date, end_date, active, created_at, updated_at) VALUES
(1,  1,  'kc-staff-001', 'LAB_INCHARGE', '2024-07-01', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  2,  'kc-staff-002', 'TECHNICIAN',   '2024-07-01', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  3,  'kc-staff-003', 'LAB_INCHARGE', '2024-07-01', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  5,  'kc-staff-004', 'LAB_INCHARGE', '2024-07-01', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  6,  'kc-staff-005', 'LAB_INCHARGE', '2023-01-15', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  7,  'kc-staff-001', 'TECHNICIAN',   '2024-07-01', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  9,  'kc-staff-006', 'LAB_INCHARGE', '2023-07-01', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  10, 'kc-staff-007', 'TECHNICIAN',   '2024-01-15', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  11, 'kc-staff-001', 'LAB_INCHARGE', '2024-07-01', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 12, 'kc-staff-008', 'LAB_INCHARGE', '2023-07-01', NULL,         TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Room Resources (12)
INSERT INTO room_resources (id, room_number, building, floor, room_type, capacity, lab_id, status, created_at, updated_at) VALUES
(1,  'A-101', 'Block A', 1, 'LAB',       60,   1,    'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  'A-102', 'Block A', 1, 'LAB',       40,   2,    'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  'A-103', 'Block A', 1, 'LAB',       40,   5,    'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  'A-104', 'Block A', 1, 'LAB',       35,   7,    'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  'B-201', 'Block B', 2, 'LAB',       30,   3,    'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  'B-202', 'Block B', 2, 'LAB',       30,   4,    'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  'C-101', 'Block C', 1, 'CLASSROOM', 120,  NULL, 'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  'D-401', 'Block D', 4, 'LAB',       40,   8,    'UNDER_MAINTENANCE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  'D-402', 'Block D', 4, 'LAB',       35,   9,    'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'C-302', 'Block C', 3, 'LAB',       30,   10,   'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'A-105', 'Block A', 1, 'LAB',       20,   11,   'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 'E-501', 'Block E', 5, 'LAB',       25,   12,   'AVAILABLE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Syllabi (10)
INSERT INTO syllabi (id, course_id, academic_year_id, content, objectives, status, created_at, updated_at) VALUES
(1,  1,  3, 'Arrays, Linked Lists, Stacks, Queues, Trees, Graphs, Hashing, Sorting & Searching Algorithms, Complexity Analysis',                              'Understand fundamental data structures and algorithm design paradigms',    'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  2,  3, 'ER Modeling, Relational Algebra, SQL, Normalization, Transaction Management, Indexing, NoSQL Introduction',                                       'Design and implement relational databases with proper normalization',      'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  4,  3, 'OSI Model, TCP/IP, Routing, Switching, Socket Programming, Network Security, DNS, DHCP, HTTP',                                                   'Understand computer networking protocols and implement socket programs',   'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  6,  3, 'Signals & Systems Review, Z-Transform, DFT, FFT, FIR & IIR Filter Design, Multirate Processing',                                                 'Analyze and design digital signal processing systems',                    'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  11, 3, 'Supervised Learning, Unsupervised Learning, Neural Networks, Deep Learning, CNNs, RNNs, Reinforcement Learning, Model Evaluation',                'Build and evaluate machine learning models for real-world problems',      'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  10, 3, 'HTML5, CSS3, JavaScript ES6+, TypeScript, Angular Framework, RESTful APIs, Node.js, Express.js, MongoDB',                                         'Develop full-stack web applications using modern frameworks',             'DRAFT',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  12, 3, 'SDLC Models, Requirements Engineering, UML Diagrams, Design Patterns, Testing Strategies, Agile & DevOps',                                       'Apply software engineering principles to manage software projects',       'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  13, 3, '8085/8086 Architecture, Assembly Programming, Interrupt Handling, PIC/ARM Microcontrollers, Interfacing Peripherals',                              'Program microprocessors and interface external devices',                  'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  18, 3, 'Power Generation, Transmission Lines, Load Flow Analysis, Fault Analysis, Power System Protection, Smart Grids',                                  'Analyze and design electrical power distribution systems',               'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 20, 3, 'Cloud Service Models (IaaS, PaaS, SaaS), Virtualization, Docker, Kubernetes, AWS/Azure Basics, Cloud Security',                                   'Deploy and manage applications on cloud platforms',                       'DRAFT',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Course Outcomes (20: 2 per syllabus)
INSERT INTO course_outcomes (id, syllabus_id, code, description, bloom_level, created_at, updated_at) VALUES
(1,  1, 'CO1-DS',  'Implement fundamental data structures (arrays, linked lists, trees, graphs) in a programming language',   'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1, 'CO2-DS',  'Analyze time and space complexity of algorithms using asymptotic notation',                                'ANALYZE',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  2, 'CO1-DB',  'Design normalized relational database schemas from real-world requirements',                               'CREATE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  2, 'CO2-DB',  'Write complex SQL queries involving joins, subqueries, and aggregations',                                  'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  3, 'CO1-CN',  'Explain the OSI and TCP/IP reference models and protocols at each layer',                                  'UNDERSTAND', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  3, 'CO2-CN',  'Implement client-server applications using socket programming',                                            'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  4, 'CO1-DSP', 'Compute DFT and FFT of discrete-time signals and interpret frequency-domain representations',              'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  4, 'CO2-DSP', 'Design FIR and IIR digital filters to meet given specifications',                                          'CREATE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  5, 'CO1-ML',  'Implement and evaluate supervised learning algorithms (regression, classification)',                        'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 5, 'CO2-ML',  'Design and train deep neural networks for image and sequence data',                                        'CREATE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 6, 'CO1-WT',  'Build responsive web pages using HTML5, CSS3, and JavaScript',                                             'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 6, 'CO2-WT',  'Develop RESTful APIs and integrate them with a frontend framework',                                        'CREATE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 7, 'CO1-SE',  'Apply software development life cycle models to plan and execute projects',                                'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 7, 'CO2-SE',  'Design UML diagrams and apply design patterns in software architecture',                                   'CREATE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 8, 'CO1-MP',  'Write assembly language programs for 8085/8086 microprocessors',                                           'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 8, 'CO2-MP',  'Interface peripherals like ADC, DAC, keyboard, and display with microcontrollers',                         'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 9, 'CO1-PS',  'Perform load flow analysis using Gauss-Seidel and Newton-Raphson methods',                                'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 9, 'CO2-PS',  'Analyze symmetrical and unsymmetrical faults in power systems',                                            'ANALYZE',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 10,'CO1-CC',  'Deploy containerized applications using Docker and Kubernetes',                                            'APPLY',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 10,'CO2-CC',  'Design cloud-native architectures using microservices and serverless patterns',                             'CREATE',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Experiments (20)
INSERT INTO experiments (id, syllabus_id, experiment_number, title, description, lab_type_id, duration_hours, created_at, updated_at) VALUES
(1,  1, 1, 'Array Operations & Searching',           'Implement linear search, binary search, and basic array manipulations',          1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1, 2, 'Linked List Implementation',              'Implement singly & doubly linked lists with insertion, deletion, and traversal', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  1, 3, 'Stack & Queue Applications',              'Implement stack-based expression evaluation and queue-based BFS',               1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  1, 4, 'Binary Search Tree Operations',           'Implement BST insertion, deletion, search, and traversal algorithms',           1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  2, 1, 'ER Diagram & Schema Design',              'Design ER diagrams and convert to relational schemas for a case study',         1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  2, 2, 'SQL Queries & Joins',                     'Write complex queries with inner, outer, cross joins and subqueries',           1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  3, 1, 'Socket Programming - TCP Chat',           'Build a TCP-based chat application using Java or Python sockets',               4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  3, 2, 'Packet Analysis with Wireshark',          'Capture and analyze network packets using Wireshark tool',                      4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  4, 1, 'DFT & FFT Computation',                   'Compute DFT and FFT of discrete signals using MATLAB/Python',                  2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 4, 2, 'FIR Filter Design',                       'Design and simulate FIR low-pass and band-pass filters',                       2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 5, 1, 'Linear Regression & Classification',      'Implement linear regression and logistic classification on datasets',           1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 5, 2, 'CNN for Image Classification',            'Build and train a CNN model for CIFAR-10 image classification',                 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 7, 1, 'UML Use Case & Class Diagrams',           'Draw use case and class diagrams for a library management system',             1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 7, 2, 'Unit Testing with JUnit',                 'Write and run unit tests for a Java application using JUnit 5',                1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 8, 1, '8085 Assembly - Arithmetic Operations',   'Write 8085 assembly programs for addition, subtraction, multiplication',        2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 8, 2, 'LED Interfacing with ARM Microcontroller','Interface LEDs and push buttons with ARM Cortex-M4 board',                     2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 9, 1, 'Load Flow Analysis Simulation',           'Simulate load flow analysis using PowerWorld or ETAP software',                7, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 9, 2, 'Fault Analysis Simulation',               'Simulate symmetrical and unsymmetrical faults on a 5-bus system',              7, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 10,1, 'Docker Containerization',                 'Containerize a web application using Docker and Docker Compose',               1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 10,2, 'Kubernetes Deployment',                   'Deploy a multi-container app on a Kubernetes cluster',                          1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Experiment-Outcome Mappings (20)
INSERT INTO experiment_outcome_mappings (id, experiment_id, course_outcome_id, created_at) VALUES
(1,  1,  1,  CURRENT_TIMESTAMP),
(2,  2,  1,  CURRENT_TIMESTAMP),
(3,  3,  1,  CURRENT_TIMESTAMP),
(4,  4,  2,  CURRENT_TIMESTAMP),
(5,  5,  3,  CURRENT_TIMESTAMP),
(6,  6,  4,  CURRENT_TIMESTAMP),
(7,  7,  6,  CURRENT_TIMESTAMP),
(8,  8,  5,  CURRENT_TIMESTAMP),
(9,  9,  7,  CURRENT_TIMESTAMP),
(10, 10, 8,  CURRENT_TIMESTAMP),
(11, 11, 9,  CURRENT_TIMESTAMP),
(12, 12, 10, CURRENT_TIMESTAMP),
(13, 13, 13, CURRENT_TIMESTAMP),
(14, 14, 14, CURRENT_TIMESTAMP),
(15, 15, 15, CURRENT_TIMESTAMP),
(16, 16, 16, CURRENT_TIMESTAMP),
(17, 17, 17, CURRENT_TIMESTAMP),
(18, 18, 18, CURRENT_TIMESTAMP),
(19, 19, 19, CURRENT_TIMESTAMP),
(20, 20, 20, CURRENT_TIMESTAMP);

-- Fee Structures (15)
INSERT INTO fee_structures (id, program_id, semester_id, fee_type, amount, currency, effective_from, effective_to, status, created_at, updated_at) VALUES
(1,  1, 5, 'TUITION',       75000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1, 5, 'LAB_FEE',       15000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  1, 5, 'LIBRARY',       5000.00,   'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  2, 5, 'TUITION',       70000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  2, 5, 'LAB_FEE',       18000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  3, 5, 'TUITION',       65000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  6, 5, 'TUITION',       95000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  5, 5, 'TUITION',       72000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  4, 5, 'TUITION',       60000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 8, 5, 'TUITION',       68000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 9, 5, 'TUITION',       70000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 10,5, 'TUITION',       98000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 7, 5, 'TUITION',       90000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 3, 5, 'LAB_FEE',       12000.00,  'INR', '2025-07-01', '2025-12-31', 'ACTIVE',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 1, 3, 'TUITION',       72000.00,  'INR', '2024-07-01', '2024-12-31', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Accreditation Reports (6)
INSERT INTO accreditation_reports (id, department_id, report_type, academic_year, generated_date, overall_score, status, summary, created_at, updated_at) VALUES
(1, 1, 'NAAC',  '2025-26', '2025-09-15', 3.45,  'SUBMITTED', 'CSE department received A+ grade with strong research output and industry partnerships.',        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'NBA',   '2025-26', '2025-09-20', 3.20,  'DRAFT',     'ECE program meets all NBA criteria; lab infrastructure needs minor upgrades.',                   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'NIRF',  '2025-26', '2025-10-01', 72.50, 'SUBMITTED', 'College ranked in top 100 engineering institutions based on teaching, research, and placement.',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 3, 'NBA',   '2025-26', '2025-10-10', 2.95,  'DRAFT',     'ME program close to NBA accreditation threshold — workshop upgrades recommended.',               CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 5, 'NAAC',  '2025-26', '2025-10-15', 3.30,  'SUBMITTED', 'IT department shows strong industry collaboration and placement record.',                        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 4, 'NBA',   '2024-25', '2024-09-10', 3.10,  'SUBMITTED', 'CE program accredited for 3 years with recommendations for lab modernization.',                 CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =============================================
-- TIER 3: Depends on Tier 2
-- =============================================

-- Admissions (20)
INSERT INTO admissions (id, student_id, application_number, program_id, admission_date, admission_type, status, remarks, created_at, updated_at) VALUES
(1,  1,  'APP2024-CSE-001',   1,  '2024-07-10', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 15234',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  2,  'APP2024-CSE-002',   1,  '2024-07-10', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 18940',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  3,  'APP2024-CSE-003',   1,  '2024-07-12', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 22105',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  4,  'APP2024-ECE-001',   2,  '2024-07-10', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 28340',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  5,  'APP2024-ECE-002',   2,  '2024-07-11', 'MANAGEMENT', 'CONFIRMED',  'Management quota admission',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  6,  'APP2024-ME-001',    3,  '2024-07-13', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 35120',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  7,  'APP2024-CE-001',    4,  '2024-07-14', 'LATERAL',    'CONFIRMED',  'Lateral entry from diploma',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  8,  'APP2024-IT-001',    5,  '2024-07-10', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 41230',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  9,  'APP2025-CSE-001',   1,  '2025-07-10', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 12870',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 10, 'APP2025-CSE-002',   1,  '2025-07-11', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 19450',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 13, 'APP2023-MTCSE-001', 6,  '2023-07-10', 'GATE',       'CONFIRMED',  'GATE score: 620, AIR: 1450',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 15, 'APP2024-CSE-004',   1,  '2024-07-15', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 45000 — student inactive', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 16, 'APP2024-EE-001',    8,  '2024-07-12', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 32100',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 17, 'APP2024-MC-001',    9,  '2024-07-13', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 25670',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 18, 'APP2025-CE-001',    4,  '2025-07-12', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 38900',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 22, 'APP2024-CSE-005',   1,  '2024-07-11', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 20100',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 23, 'APP2024-CSE-006',   1,  '2024-07-12', 'MANAGEMENT', 'CONFIRMED',  'Management quota with good academic record', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 28, 'APP2024-MTVLSI-001',7,  '2024-07-14', 'GATE',       'CONFIRMED',  'GATE score: 580, AIR: 2100',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 29, 'APP2024-CSE-007',   1,  '2024-07-15', 'REGULAR',    'CONFIRMED',  'JEE Main rank: 48000 — disciplinary suspension', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 30, 'APP2023-MTCSE-002', 6,  '2023-07-12', 'GATE',       'CONFIRMED',  'GATE score: 590, AIR: 1800',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Student Enrollments (35: students -> courses in semesters)
INSERT INTO student_enrollments (id, student_id, course_id, semester_id, lab_batch_group, enrollment_date, status, created_at, updated_at) VALUES
-- Aarav (CSE, sem 5 -> current odd sem 2025-26)
(1,  1,  1,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1,  2,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  1,  12, 5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Ishita (CSE, sem 5)
(4,  2,  1,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  2,  2,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  2,  12, 5, 'B', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Rohan (CSE, sem 5)
(7,  3,  1,  5, 'B', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  3,  2,  5, 'B', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Sneha (ECE, sem 5)
(9,  4,  6,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 4,  7,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 4,  13, 5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Karan (ECE, sem 5)
(12, 5,  6,  5, 'B', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 5,  13, 5, 'B', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Divya (ME, sem 5)
(14, 6,  8,  5, NULL,'2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 6,  14, 5, NULL,'2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Amit (CE, sem 5)
(16, 7,  9,  5, NULL,'2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 7,  15, 5, NULL,'2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Neha (IT, sem 5)
(18, 8,  10, 5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 8,  16, 5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Rahul (CSE, sem 1 -> new intake)
(20, 9,  1,  5, 'C', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(21, 9,  12, 5, 'C', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Pooja (CSE, sem 1)
(22, 10, 1,  5, 'C', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Sunil (M.Tech CS, sem 5)
(23, 13, 11, 5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 13, 17, 5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Kavita (IT, sem 5)
(25, 14, 10, 5, 'B', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Historical: Aarav enrolled in a course last semester
(26, 1,  3,  4, 'A', '2025-01-11', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Shruti (CSE, sem 5)
(27, 22, 1,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 22, 4,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Aditya (CSE, sem 5)
(29, 23, 1,  5, 'B', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 23, 20, 5, 'B', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Priyanka (EE, sem 5)
(31, 16, 18, 5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Siddharth (MC, sem 5)
(32, 17, 19, 5, NULL,'2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Meghna (ECE, sem 5)
(33, 24, 6,  5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Aryan (ME, sem 5)
(34, 25, 8,  5, NULL,'2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Vivek (M.Tech DS, sem 5)
(35, 21, 11, 5, 'A', '2025-07-16', 'ENROLLED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Faculty Lab Assignments (10)
INSERT INTO faculty_lab_assignments (id, faculty_id, lab_id, semester_id, assigned_date, active, created_at, updated_at) VALUES
(1,  1,  1,  5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  2,  2,  5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  3,  3,  5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  4,  4,  5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  8,  7,  5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  1,  5,  5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  5,  6,  5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  9,  12, 5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  11, 10, 5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 15, 11, 5, '2025-07-10', TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Faculty Lab Expertise (12)
INSERT INTO faculty_lab_expertise (id, faculty_id, lab_type_id, proficiency_level, certified_date, created_at, updated_at) VALUES
(1,  1,  1, 'EXPERT',        '2016-01-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1,  4, 'ADVANCED',      '2018-06-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  2,  1, 'ADVANCED',      '2018-03-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  3,  2, 'EXPERT',        '2015-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  4,  2, 'INTERMEDIATE',  '2021-02-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  5,  5, 'EXPERT',        '2013-08-20', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  7,  1, 'INTERMEDIATE',  '2022-01-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  8,  1, 'ADVANCED',      '2023-06-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  9,  2, 'EXPERT',        '2012-03-10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 10, 1, 'ADVANCED',      '2017-09-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 11, 5, 'INTERMEDIATE',  '2023-06-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 12, 2, 'ADVANCED',      '2020-01-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Lab Timetables (15)
INSERT INTO lab_timetables (id, lab_id, course_id, semester_id, faculty_id, day_of_week, start_time, end_time, batch_group, status, created_at, updated_at) VALUES
(1,  1,  1,  5, 1,  'MONDAY',    '09:00:00', '11:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1,  1,  5, 1,  'MONDAY',    '11:00:00', '13:00:00', 'B', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  2,  2,  5, 2,  'TUESDAY',   '09:00:00', '12:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  2,  2,  5, 2,  'TUESDAY',   '14:00:00', '17:00:00', 'B', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  3,  6,  5, 3,  'WEDNESDAY', '09:00:00', '12:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  4,  6,  5, 4,  'WEDNESDAY', '14:00:00', '17:00:00', 'B', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  5,  4,  5, 1,  'THURSDAY',  '09:00:00', '12:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  7,  11, 5, 8,  'FRIDAY',    '09:00:00', '12:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  1,  1,  5, 1,  'FRIDAY',    '14:00:00', '16:00:00', 'C', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 1,  12, 5, 2,  'WEDNESDAY', '09:00:00', '11:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 3,  13, 5, 12, 'THURSDAY',  '09:00:00', '12:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 5,  20, 5, 8,  'MONDAY',    '14:00:00', '17:00:00', 'B', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 12, 18, 5, 9,  'TUESDAY',   '09:00:00', '12:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 10, 14, 5, 11, 'FRIDAY',    '14:00:00', '17:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 7,  17, 5, 8,  'THURSDAY',  '14:00:00', '17:00:00', 'A', 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Lab Fees (10)
INSERT INTO lab_fees (id, lab_id, course_id, semester_id, amount, description, status, created_at, updated_at) VALUES
(1,  1,  1,  5, 3000.00,  'Data Structures Lab usage fee — Programming Lab',   'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  2,  2,  5, 3000.00,  'DBMS Lab usage fee — Data Structures Lab',          'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  3,  6,  5, 5000.00,  'DSP Lab usage fee — Digital Electronics Lab',       'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  5,  4,  5, 4000.00,  'Networks Lab usage fee — Networking Lab',           'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  7,  11, 5, 8000.00,  'ML Lab usage fee — AI Lab (GPU compute charges)',   'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  1,  12, 5, 2500.00,  'Software Engineering Lab usage fee',                'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  3,  13, 5, 4500.00,  'Microprocessor Lab usage fee',                      'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  12, 18, 5, 3500.00,  'Power Systems Lab usage fee',                       'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  5,  20, 5, 5000.00,  'Cloud Computing Lab usage fee',                     'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 7,  17, 5, 10000.00, 'Deep Learning Lab usage fee (GPU compute)',         'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Lab Utilization KPIs (12)
INSERT INTO lab_utilization_kpis (id, lab_id, semester_id, total_slots, utilized_slots, utilization_percentage, peak_hours, avg_occupancy, measurement_date, created_at, updated_at) VALUES
(1,  1,  5, 50, 42, 84.00, '09:00-13:00', 75.50, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  2,  5, 40, 30, 75.00, '09:00-12:00', 68.20, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  3,  5, 30, 18, 60.00, '09:00-12:00', 52.10, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  5,  5, 40, 35, 87.50, '09:00-17:00', 80.00, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  7,  5, 30, 28, 93.33, '09:00-18:00', 88.40, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  1,  4, 50, 38, 76.00, '10:00-14:00', 70.30, '2025-04-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  4,  5, 30, 22, 73.33, '14:00-17:00', 65.00, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  6,  5, 40, 15, 37.50, '09:00-12:00', 30.00, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  9,  5, 30, 20, 66.67, '09:00-12:00', 58.50, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 10, 5, 25, 22, 88.00, '14:00-17:00', 82.00, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 11, 5, 20, 18, 90.00, '09:00-18:00', 85.00, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 12, 5, 25, 12, 48.00, '09:00-12:00', 40.00, '2025-09-15', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Analytics Snapshots (12)
INSERT INTO analytics_snapshots (id, department_id, program_id, semester_id, snapshot_type, metric_name, metric_value, snapshot_date, details, created_at, updated_at) VALUES
(1,  1, 1,    5, 'ENROLLMENT', 'total_enrolled_students',     120.00, '2025-08-01', 'B.Tech CSE enrollment count for odd semester 2025-26',           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1, NULL, 5, 'PLACEMENT',  'placement_percentage',         92.50, '2025-08-01', 'Overall CSE department placement rate',                          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  2, 2,    5, 'ENROLLMENT', 'total_enrolled_students',      85.00, '2025-08-01', 'B.Tech ECE enrollment count for odd semester 2025-26',           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  1, 1,    5, 'ACADEMIC',   'avg_gpa',                       8.24, '2025-08-01', 'Average GPA of CSE students',                                   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  3, 3,    5, 'ENROLLMENT', 'total_enrolled_students',      60.00, '2025-08-01', 'B.Tech ME enrollment count for odd semester 2025-26',            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  1, 1,    4, 'ACADEMIC',   'pass_percentage',              96.80, '2025-06-15', 'Pass percentage for CSE students in even semester 2024-25',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  5, 5,    5, 'ENROLLMENT', 'total_enrolled_students',      55.00, '2025-08-01', 'B.Tech IT enrollment count',                                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  4, 4,    5, 'ENROLLMENT', 'total_enrolled_students',      45.00, '2025-08-01', 'B.Tech CE enrollment count',                                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  2, NULL, 5, 'PLACEMENT',  'placement_percentage',         85.20, '2025-08-01', 'ECE department placement rate',                                  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 6, 8,    5, 'ENROLLMENT', 'total_enrolled_students',      40.00, '2025-08-01', 'B.Tech EE enrollment count',                                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 7, 9,    5, 'ENROLLMENT', 'total_enrolled_students',      35.00, '2025-08-01', 'B.Tech MC enrollment count',                                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 1, NULL, 5, 'ACADEMIC',   'research_publications',        28.00, '2025-08-01', 'Total research publications from CSE department this year',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Scholarships (8)
INSERT INTO scholarships (id, student_id, name, amount, scholarship_type, awarded_date, expiry_date, status, remarks, created_at, updated_at) VALUES
(1, 1,  'Merit Scholarship',                50000.00,  'MERIT',        '2025-08-01', '2026-06-30', 'ACTIVE',  'Top 5% in department — full tuition waiver component',          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 4,  'SC/ST Scholarship',                35000.00,  'GOVERNMENT',   '2025-08-01', '2026-06-30', 'ACTIVE',  'Government post-matric scholarship for SC/ST students',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 9,  'Freshman Academic Excellence',     25000.00,  'MERIT',        '2025-08-15', '2026-06-30', 'ACTIVE',  'Awarded for outstanding JEE performance — AIR under 15000',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 13, 'GATE Scholarship',                 60000.00,  'GOVERNMENT',   '2023-08-01', '2025-07-31', 'EXPIRED', 'AICTE GATE scholarship for M.Tech students — now expired',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 2,  'Women in STEM Scholarship',        30000.00,  'MERIT',        '2025-08-01', '2026-06-30', 'ACTIVE',  'Encouragement for women in engineering — sponsored by TCS',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 6,  'Sports Excellence Award',          20000.00,  'MERIT',        '2025-09-01', '2026-06-30', 'ACTIVE',  'State-level badminton champion',                                CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 21, 'GATE Scholarship',                 60000.00,  'GOVERNMENT',   '2023-08-01', '2025-07-31', 'EXPIRED', 'AICTE GATE scholarship for M.Tech Data Science — expired',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 22, 'Academic Excellence Award',        40000.00,  'MERIT',        '2025-08-15', '2026-06-30', 'ACTIVE',  'Top 3 in CSE batch — outstanding lab performance',             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Payments (20)
INSERT INTO payments (id, student_id, fee_structure_id, amount, payment_date, payment_method, transaction_reference, status, remarks, created_at, updated_at) VALUES
(1,  1,  1,  75000.00,  '2025-07-20', 'ONLINE',   'TXN-20250720-001', 'COMPLETED', 'Tuition fee paid via net banking',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1,  2,  15000.00,  '2025-07-20', 'ONLINE',   'TXN-20250720-002', 'COMPLETED', 'Lab fee paid via net banking',                  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  1,  3,  5000.00,   '2025-07-20', 'ONLINE',   'TXN-20250720-003', 'COMPLETED', 'Library fee paid via net banking',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  2,  1,  75000.00,  '2025-07-22', 'UPI',      'TXN-20250722-001', 'COMPLETED', 'Tuition fee paid via UPI',                     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  3,  1,  75000.00,  '2025-07-25', 'ONLINE',   'TXN-20250725-001', 'COMPLETED', 'Tuition fee paid via NEFT',                    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  4,  4,  70000.00,  '2025-07-21', 'ONLINE',   'TXN-20250721-001', 'COMPLETED', 'ECE tuition fee paid',                         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  5,  4,  70000.00,  '2025-08-05', 'CHEQUE',   'CHQ-882341',       'COMPLETED', 'ECE tuition fee paid via demand draft',        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  9,  1,  75000.00,  '2025-07-18', 'ONLINE',   'TXN-20250718-001', 'COMPLETED', 'Tuition fee for new admission 2025',           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  6,  6,  65000.00,  '2025-08-01', 'UPI',      'TXN-20250801-001', 'COMPLETED', 'ME tuition fee',                               CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 13, 7,  95000.00,  '2025-07-25', 'ONLINE',   'TXN-20250725-002', 'PENDING',   'M.Tech tuition fee — awaiting bank clearance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 7,  9,  60000.00,  '2025-07-22', 'ONLINE',   'TXN-20250722-002', 'COMPLETED', 'CE tuition fee paid via NEFT',                 CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 8,  8,  72000.00,  '2025-07-23', 'UPI',      'TXN-20250723-001', 'COMPLETED', 'IT tuition fee paid via UPI',                  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 16, 10, 68000.00,  '2025-07-24', 'ONLINE',   'TXN-20250724-001', 'COMPLETED', 'EE tuition fee paid',                          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 17, 11, 70000.00,  '2025-07-25', 'ONLINE',   'TXN-20250725-003', 'COMPLETED', 'MC tuition fee paid',                          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 22, 1,  75000.00,  '2025-07-26', 'ONLINE',   'TXN-20250726-001', 'COMPLETED', 'CSE tuition fee paid',                         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 23, 1,  75000.00,  '2025-08-10', 'CHEQUE',   'CHQ-993412',       'COMPLETED', 'CSE tuition fee paid via cheque',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 10, 1,  75000.00,  '2025-07-19', 'UPI',      'TXN-20250719-001', 'COMPLETED', 'Tuition fee for new CSE admission 2025',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 14, 8,  72000.00,  '2025-08-12', 'ONLINE',   'TXN-20250812-001', 'COMPLETED', 'IT tuition fee paid',                          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 25, 6,  65000.00,  '2025-07-28', 'ONLINE',   'TXN-20250728-001', 'COMPLETED', 'ME tuition fee paid',                          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 30, 7,  95000.00,  '2025-08-15', 'ONLINE',   'TXN-20250815-001', 'PENDING',   'M.Tech CSE fee — payment processing',          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Attendance Alerts (8)
INSERT INTO attendance_alerts (id, student_id, course_id, alert_type, message, threshold_percentage, current_percentage, resolved, created_at, updated_at) VALUES
(1, 3,  1,  'LOW_ATTENDANCE',  'Attendance below 75% threshold in Data Structures lab sessions',              75.00, 66.67, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 5,  6,  'LOW_ATTENDANCE',  'Attendance dropped below 75% in DSP lab — please attend remaining sessions', 75.00, 60.00, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 15, 1,  'CHRONIC_ABSENCE', 'Student has been absent for 3 consecutive lab sessions',                      75.00, 33.33, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 1,  3,  'LOW_ATTENDANCE',  'Attendance was below threshold but has now recovered',                        75.00, 80.00, TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 23, 1,  'LOW_ATTENDANCE',  'DS lab attendance falling — 2 consecutive absences',                          75.00, 66.67, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 24, 6,  'LOW_ATTENDANCE',  'DSP lab attendance below minimum — attended only 2 of 4 sessions',           75.00, 50.00, FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 29, 1,  'CHRONIC_ABSENCE', 'Suspended student — absent for 5 consecutive sessions',                       75.00, 0.00,  FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 7,  9,  'LOW_ATTENDANCE',  'Structural Analysis lab attendance recovered after warning',                   75.00, 78.00, TRUE,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- GPA Records (20)
INSERT INTO gpa_records (id, student_id, semester_id, semester_gpa, cgpa, total_credits, earned_credits, lab_component_gpa, calculation_date, status, created_at, updated_at) VALUES
(1,  1,  3, 8.50, 8.50, 22, 22, 9.00, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1,  4, 8.80, 8.65, 24, 24, 9.20, '2025-07-01', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  2,  3, 9.10, 9.10, 22, 22, 9.50, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  2,  4, 8.90, 9.00, 24, 24, 9.00, '2025-07-01', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  3,  3, 7.20, 7.20, 22, 20, 6.50, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  3,  4, 7.50, 7.35, 24, 22, 7.00, '2025-07-01', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  4,  3, 8.00, 8.00, 22, 22, 8.50, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  4,  4, 8.30, 8.15, 24, 24, 8.80, '2025-07-01', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  13, 1, 9.40, 9.40, 18, 18, 9.60, '2024-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 13, 2, 9.20, 9.30, 18, 18, 9.40, '2024-07-01', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 5,  3, 7.80, 7.80, 22, 22, 7.50, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 5,  4, 7.60, 7.70, 24, 22, 7.20, '2025-07-01', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 6,  3, 8.20, 8.20, 22, 22, 8.00, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 7,  3, 7.90, 7.90, 22, 22, 7.80, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 8,  3, 8.60, 8.60, 22, 22, 8.90, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 22, 3, 9.00, 9.00, 22, 22, 9.30, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 22, 4, 8.80, 8.90, 24, 24, 9.10, '2025-07-01', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 23, 3, 7.40, 7.40, 22, 20, 6.80, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 16, 3, 8.10, 8.10, 22, 22, 8.30, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 17, 3, 8.40, 8.40, 22, 22, 8.60, '2025-01-05', 'FINAL',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Assets (12)
INSERT INTO assets (id, equipment_id, asset_code, name, category, purchase_date, purchase_cost, current_value, useful_life_years, depreciation_method, location, status, created_at, updated_at) VALUES
(1,  1,  'AST-DELL-001', 'Dell OptiPlex Workstation #1',   'IT_EQUIPMENT',         '2023-06-15', 85000.00,   63750.00,  5,  'STRAIGHT_LINE', 'Block A, Room 101', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  2,  'AST-DELL-002', 'Dell OptiPlex Workstation #2',   'IT_EQUIPMENT',         '2023-06-15', 85000.00,   63750.00,  5,  'STRAIGHT_LINE', 'Block A, Room 101', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  4,  'AST-TEK-001',  'Tektronix Oscilloscope',         'LAB_INSTRUMENT',       '2021-03-20', 125000.00,  62500.00,  8,  'STRAIGHT_LINE', 'Block B, Room 201', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  6,  'AST-RSA-001',  'Spectrum Analyzer',              'LAB_INSTRUMENT',       '2023-02-28', 350000.00,  280000.00, 10, 'STRAIGHT_LINE', 'Block B, Room 202', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  7,  'AST-CISCO-001','Cisco Catalyst Switch',           'NETWORK_EQUIPMENT',    '2023-08-01', 195000.00,  155000.00, 7,  'STRAIGHT_LINE', 'Block A, Room 103', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  9,  'AST-LT-001',   'Lathe Machine',                  'HEAVY_MACHINERY',      '2019-11-15', 450000.00,  180000.00, 15, 'STRAIGHT_LINE', 'Workshop Building', 'UNDER_REPAIR',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  10, 'AST-CNC-001',  'CNC Milling Machine',            'HEAVY_MACHINERY',      '2021-06-20', 1200000.00, 960000.00, 15, 'STRAIGHT_LINE', 'Workshop Building', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  11, 'AST-NV-001',   'NVIDIA DGX AI Workstation',      'HIGH_PERF_COMPUTING',  '2024-01-15', 2500000.00, 2250000.00,10, 'STRAIGHT_LINE', 'Block A, Room 104', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  14, 'AST-CHEM-001', 'Spectrophotometer UV-Vis',       'LAB_INSTRUMENT',       '2022-09-10', 180000.00,  135000.00, 8,  'STRAIGHT_LINE', 'Block C, Room 301', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 16, 'AST-CAD-001',  'CAD Workstation HP Z4',          'IT_EQUIPMENT',         '2024-03-15', 175000.00,  160000.00, 5,  'STRAIGHT_LINE', 'Block D, Room 401', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 18, 'AST-GPU-001',  'GPU Research Server',            'HIGH_PERF_COMPUTING',  '2024-06-01', 1800000.00, 1650000.00,8,  'STRAIGHT_LINE', 'Block A, Room 105', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 20, 'AST-EMTR-001', 'Electrical Machines Trainer',    'LAB_INSTRUMENT',       '2023-01-20', 250000.00,  200000.00, 10, 'STRAIGHT_LINE', 'Block E, Room 501', 'ACTIVE',         CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Maintenance Schedules (10)
INSERT INTO maintenance_schedules (id, equipment_id, maintenance_type, scheduled_date, completed_date, performed_by, cost, notes, status, created_at, updated_at) VALUES
(1,  1,  'PREVENTIVE',  '2025-06-15', '2025-06-15', 'Dell Service Engineer',     5000.00,  'Annual maintenance — cleaning, thermal paste replacement, OS updates',         'COMPLETED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  4,  'CALIBRATION', '2025-03-20', '2025-03-22', 'Tektronix Service Center',  12000.00, 'Annual calibration and probe replacement',                                     'COMPLETED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  7,  'PREVENTIVE',  '2025-09-01', NULL,          NULL,                        NULL,     'Scheduled firmware update and port inspection for Cisco switch',                'SCHEDULED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  9,  'CORRECTIVE',  '2025-08-10', NULL,          NULL,                        NULL,     'Lathe machine spindle bearing replacement — machine currently under repair',    'IN_PROGRESS',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  10, 'PREVENTIVE',  '2025-10-15', NULL,          NULL,                        NULL,     'CNC machine tool calibration and coolant system flush',                         'SCHEDULED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  11, 'PREVENTIVE',  '2025-12-01', NULL,          NULL,                        NULL,     'GPU diagnostics, driver update, and thermal monitoring for DGX workstation',    'SCHEDULED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  14, 'CALIBRATION', '2025-04-15', '2025-04-16', 'Lab Instrument Services',   8000.00,  'UV-Vis calibration and lamp replacement',                                      'COMPLETED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  16, 'PREVENTIVE',  '2025-11-01', NULL,          NULL,                        NULL,     'CAD workstation — SSD health check, RAM diagnostics, software license renewal', 'SCHEDULED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  18, 'PREVENTIVE',  '2025-08-20', '2025-08-21', 'NVIDIA Support Engineer',   25000.00, 'GPU thermal paste renewal and CUDA driver update',                             'COMPLETED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 20, 'CORRECTIVE',  '2025-07-10', '2025-07-12', 'Electrical Lab Technician', 15000.00, 'Motor winding replacement on trainer unit',                                    'COMPLETED',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Depreciations (10)
INSERT INTO depreciations (id, asset_id, fiscal_year, opening_value, depreciation_amount, closing_value, calculated_date, created_at, updated_at) VALUES
(1,  1, '2023-24', 85000.00,   17000.00,  68000.00,   '2024-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1, '2024-25', 68000.00,   17000.00,  51000.00,   '2025-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  3, '2021-22', 125000.00,  15625.00,  109375.00,  '2022-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  3, '2022-23', 109375.00,  15625.00,  93750.00,   '2023-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5,  3, '2023-24', 93750.00,   15625.00,  78125.00,   '2024-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  3, '2024-25', 78125.00,   15625.00,  62500.00,   '2025-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  9, '2022-23', 180000.00,  22500.00,  157500.00,  '2023-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  9, '2023-24', 157500.00,  22500.00,  135000.00,  '2024-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  10,'2024-25', 175000.00,  35000.00,  140000.00,  '2025-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 12,'2023-24', 250000.00,  25000.00,  225000.00,  '2024-03-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =============================================
-- TIER 4: Deepest dependencies
-- =============================================

-- Lab Attendances (35)
INSERT INTO lab_attendances (id, student_id, experiment_id, lab_timetable_id, attendance_date, status, remarks, created_at, updated_at) VALUES
-- Aarav: DS Lab (Batch A, Mon 9-11)
(1,  1, 1, 1, '2025-07-21', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1, 2, 1, '2025-07-28', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  1, 3, 1, '2025-08-04', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  1, 4, 1, '2025-08-11', 'ABSENT',   'Medical leave — fever',          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Ishita: DS Lab (Batch A, Mon 9-11)
(5,  2, 1, 1, '2025-07-21', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  2, 2, 1, '2025-07-28', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  2, 3, 1, '2025-08-04', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8,  2, 4, 1, '2025-08-11', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Rohan: DS Lab (Batch B, Mon 11-13)
(9,  3, 1, 2, '2025-07-21', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 3, 2, 2, '2025-07-28', 'ABSENT',   'No reason provided',             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 3, 3, 2, '2025-08-04', 'ABSENT',   'Family emergency',               CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(12, 3, 4, 2, '2025-08-11', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Aarav: DBMS Lab (Batch A, Tue 9-12)
(13, 1, 5, 3, '2025-07-22', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 1, 6, 3, '2025-07-29', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Sneha: DSP Lab (Batch A, Wed 9-12)
(15, 4, 9, 5, '2025-07-23', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 4, 10,5, '2025-07-30', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Karan: DSP Lab (Batch B, Wed 14-17)
(17, 5, 9, 6, '2025-07-23', 'ABSENT',   'Sick leave',                     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 5, 10,6, '2025-07-30', 'ABSENT',   'Transportation issue',           CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Sunil: ML Lab (Batch A, Fri 9-12)
(19, 13,11,8, '2025-07-25', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 13,12,8, '2025-08-01', 'PRESENT',  NULL,                             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Vivek (student 22): DS Lab (Batch A)
(21, 22, 1, 1, '2025-07-21', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 22, 2, 1, '2025-07-28', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 22, 3, 1, '2025-08-04', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(24, 22, 4, 1, '2025-08-11', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Megha (student 23): DS Lab (Batch B)
(25, 23, 1, 2, '2025-07-21', 'ABSENT',   'Late registration',             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 23, 2, 2, '2025-07-28', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(27, 23, 3, 2, '2025-08-04', 'ABSENT',   'No reason provided',            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(28, 23, 4, 2, '2025-08-11', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Tanvi (student 24): DSP Lab
(29, 24, 9, 5, '2025-07-23', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(30, 24, 10,5, '2025-07-30', 'ABSENT',   'Unwell',                        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Sidharth (student 16): DS Lab (Batch A)
(31, 16, 1, 1, '2025-07-21', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(32, 16, 2, 1, '2025-07-28', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(33, 16, 3, 1, '2025-08-04', 'ABSENT',   'College fest duty',             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(34, 16, 4, 1, '2025-08-11', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Arjun (student 17): DBMS Lab
(35, 17, 5, 3, '2025-07-22', 'PRESENT',  NULL,                            CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Continuous Evaluations (25)
INSERT INTO continuous_evaluations (id, student_id, course_id, experiment_id, semester_id, evaluation_type, marks_obtained, max_marks, evaluation_date, remarks, created_at, updated_at) VALUES
-- Aarav: DS course lab evaluations
(1,  1, 1, 1,    3, 'LAB_PRACTICAL',  18.00, 20.00, '2025-07-21', 'Excellent work on array operations',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2,  1, 1, 2,    3, 'LAB_PRACTICAL',  17.50, 20.00, '2025-07-28', 'Good linked list implementation',        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3,  1, 1, 3,    3, 'LAB_PRACTICAL',  19.00, 20.00, '2025-08-04', 'Outstanding stack/queue solution',       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4,  1, 1, NULL,  3, 'MID_SEMESTER',  38.00, 50.00, '2025-09-22', NULL,                                     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Ishita: DS course lab evaluations
(5,  2, 1, 1,    3, 'LAB_PRACTICAL',  19.50, 20.00, '2025-07-21', 'Exceptional performance',                CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6,  2, 1, 2,    3, 'LAB_PRACTICAL',  18.00, 20.00, '2025-07-28', 'Well-structured code',                   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7,  2, 1, NULL,  3, 'MID_SEMESTER',  42.00, 50.00, '2025-09-22', 'Highest scorer in section',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Rohan: DS course
(8,  3, 1, 1,    3, 'LAB_PRACTICAL',  14.00, 20.00, '2025-07-21', 'Needs improvement in coding style',      CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(9,  3, 1, NULL,  3, 'MID_SEMESTER',  28.00, 50.00, '2025-09-22', 'Below average — needs more practice',    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Aarav: DBMS lab evaluations
(10, 1, 2, 5,    3, 'LAB_PRACTICAL',  16.00, 20.00, '2025-07-22', 'Good ER diagram design',                 CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 1, 2, 6,    3, 'LAB_PRACTICAL',  18.50, 20.00, '2025-07-29', 'Excellent SQL query writing skills',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Sneha: DSP evaluations
(12, 4, 6, 9,    3, 'LAB_PRACTICAL',  17.00, 20.00, '2025-07-23', 'Good understanding of DFT concepts',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 4, 6, 10,   3, 'LAB_PRACTICAL',  16.50, 20.00, '2025-07-30', 'Filter design needs more precision',     CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Sunil: ML evaluations
(14, 13,11,11,   3, 'LAB_PRACTICAL',  19.00, 20.00, '2025-07-25', 'Excellent regression model',             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(15, 13,11,12,   3, 'LAB_PRACTICAL',  18.50, 20.00, '2025-08-01', 'Good CNN implementation using PyTorch',  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Vivek (student 22): DS evaluations
(16, 22, 1, 1,   3, 'LAB_PRACTICAL',  17.00, 20.00, '2025-07-21', 'Good effort on array operations',        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 22, 1, 2,   3, 'LAB_PRACTICAL',  18.00, 20.00, '2025-07-28', 'Improved linked list work',              CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(18, 22, 1, NULL, 3, 'MID_SEMESTER',  40.00, 50.00, '2025-09-22', 'Strong mid-semester performance',        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Megha (student 23): DS evaluations
(19, 23, 1, 1,   3, 'LAB_PRACTICAL',  12.00, 20.00, '2025-07-21', 'Missed initial setup — partial marks',   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 23, 1, NULL, 3, 'MID_SEMESTER',  25.00, 50.00, '2025-09-22', 'Needs significant improvement',          CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Sidharth (student 16): DS evaluations
(21, 16, 1, 1,   3, 'LAB_PRACTICAL',  16.50, 20.00, '2025-07-21', 'Solid implementation',                   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 16, 1, 2,   3, 'LAB_PRACTICAL',  15.00, 20.00, '2025-07-28', 'Minor logical errors',                   CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Arjun (student 17): DBMS evaluations
(23, 17, 2, 5,   3, 'LAB_PRACTICAL',  17.50, 20.00, '2025-07-22', 'Clean ER diagram',                       CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Tanvi (student 24): DSP evaluations
(24, 24, 6, 9,   3, 'LAB_PRACTICAL',  15.00, 20.00, '2025-07-23', 'Adequate DFT understanding',             CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
-- Karan (student 5): DSP mid-sem
(25, 5,  6, NULL, 3, 'MID_SEMESTER',  30.00, 50.00, '2025-09-22', 'Attendance issues affecting performance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Practical Exams (8)
INSERT INTO practical_exams (id, course_id, semester_id, experiment_id, exam_date, start_time, end_time, lab_id, max_marks, passing_marks, examiner, status, created_at, updated_at) VALUES
(1, 1,  5, NULL, '2025-11-28', '09:00', '13:00', 1,  50.00, 20.00, 'Prof. Rajesh Kumar',    'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2,  5, NULL, '2025-11-29', '09:00', '13:00', 2,  50.00, 20.00, 'Prof. Priya Sharma',    'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 6,  5, NULL, '2025-12-01', '09:00', '13:00', 3,  50.00, 20.00, 'Prof. Suresh Patel',    'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 11, 5, NULL, '2025-12-03', '09:00', '13:00', 7,  50.00, 20.00, 'Prof. Deepa Gupta',     'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 9,  5, NULL, '2025-12-05', '09:00', '13:00', 5,  50.00, 20.00, 'Prof. Vikram Singh',    'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 13, 5, NULL, '2025-12-08', '09:00', '13:00', 1,  50.00, 20.00, 'Prof. Rajesh Kumar',    'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 17, 5, NULL, '2025-12-10', '09:00', '13:00', 12, 50.00, 20.00, 'Prof. Ramesh Yadav',    'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 1,  3, NULL, '2025-05-15', '09:00', '13:00', 1,  50.00, 20.00, 'Prof. Rajesh Kumar',    'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- =============================================
-- Reset identity sequences so API-created records
-- don't collide with seed data IDs
-- =============================================
ALTER TABLE departments               ALTER COLUMN id RESTART WITH 100;
ALTER TABLE academic_years             ALTER COLUMN id RESTART WITH 100;
ALTER TABLE lab_types                  ALTER COLUMN id RESTART WITH 100;
ALTER TABLE programs                   ALTER COLUMN id RESTART WITH 100;
ALTER TABLE semesters                  ALTER COLUMN id RESTART WITH 100;
ALTER TABLE labs                       ALTER COLUMN id RESTART WITH 100;
ALTER TABLE calendar_events            ALTER COLUMN id RESTART WITH 100;
ALTER TABLE courses                    ALTER COLUMN id RESTART WITH 100;
ALTER TABLE faculty_profiles           ALTER COLUMN id RESTART WITH 100;
ALTER TABLE student_profiles           ALTER COLUMN id RESTART WITH 100;
ALTER TABLE equipments                 ALTER COLUMN id RESTART WITH 100;
ALTER TABLE consumable_stocks          ALTER COLUMN id RESTART WITH 100;
ALTER TABLE lab_staff_assignments      ALTER COLUMN id RESTART WITH 100;
ALTER TABLE room_resources             ALTER COLUMN id RESTART WITH 100;
ALTER TABLE syllabi                    ALTER COLUMN id RESTART WITH 100;
ALTER TABLE course_outcomes            ALTER COLUMN id RESTART WITH 100;
ALTER TABLE experiments                ALTER COLUMN id RESTART WITH 100;
ALTER TABLE experiment_outcome_mappings ALTER COLUMN id RESTART WITH 100;
ALTER TABLE fee_structures             ALTER COLUMN id RESTART WITH 100;
ALTER TABLE accreditation_reports      ALTER COLUMN id RESTART WITH 100;
ALTER TABLE admissions                 ALTER COLUMN id RESTART WITH 100;
ALTER TABLE student_enrollments        ALTER COLUMN id RESTART WITH 100;
ALTER TABLE faculty_lab_assignments    ALTER COLUMN id RESTART WITH 100;
ALTER TABLE faculty_lab_expertise      ALTER COLUMN id RESTART WITH 100;
ALTER TABLE lab_timetables             ALTER COLUMN id RESTART WITH 100;
ALTER TABLE lab_fees                   ALTER COLUMN id RESTART WITH 100;
ALTER TABLE lab_utilization_kpis       ALTER COLUMN id RESTART WITH 100;
ALTER TABLE analytics_snapshots        ALTER COLUMN id RESTART WITH 100;
ALTER TABLE scholarships               ALTER COLUMN id RESTART WITH 100;
ALTER TABLE payments                   ALTER COLUMN id RESTART WITH 100;
ALTER TABLE attendance_alerts          ALTER COLUMN id RESTART WITH 100;
ALTER TABLE gpa_records                ALTER COLUMN id RESTART WITH 100;
ALTER TABLE assets                     ALTER COLUMN id RESTART WITH 100;
ALTER TABLE maintenance_schedules      ALTER COLUMN id RESTART WITH 100;
ALTER TABLE depreciations              ALTER COLUMN id RESTART WITH 100;
ALTER TABLE lab_attendances            ALTER COLUMN id RESTART WITH 100;
ALTER TABLE continuous_evaluations     ALTER COLUMN id RESTART WITH 100;
ALTER TABLE practical_exams            ALTER COLUMN id RESTART WITH 100;

-- ============================================================
-- SEED DATA FOR NEW ENTITIES
-- ============================================================

-- Companies (parent for placements/internships)
INSERT INTO companies (id, name, industry, website, contact_person, contact_email, contact_phone, tier, status, created_at, updated_at) VALUES
(1, 'TechCorp Solutions', 'IT', 'https://techcorp.example.com', 'Rajesh Mehta', 'hr@techcorp.example.com', '9876543100', 'TIER1', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'DataViz Analytics', 'IT', 'https://dataviz.example.com', 'Priya Sharma', 'hr@dataviz.example.com', '9876543101', 'TIER2', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Placement Drives (parent for placement offers)
INSERT INTO placement_drives (id, company_id, academic_year_id, title, drive_date, last_date_to_apply, roles_offered, package_min, status, created_at, updated_at) VALUES
(1, 1, 1, 'TechCorp Campus Recruitment 2024', '2024-10-15', '2024-10-10', 'Software Engineer, Data Analyst', 800000.00, 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Exam Schedules (parent for question papers, seating plans, revaluation requests)
INSERT INTO exam_schedules (id, course_id, semester_id, exam_type, date, start_time, duration, room_location, max_marks, passing_marks, status, created_at, updated_at) VALUES
(1, 1, 1, 'END_SEMESTER', '2025-11-25', '09:00:00', 180, 'Hall-A', 100.00, 40.00, 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 1, 'END_SEMESTER', '2025-11-26', '09:00:00', 180, 'Hall-B', 100.00, 40.00, 'SCHEDULED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Hostels (parent for mess menus, hostel fees, visitor logs)
INSERT INTO hostels (id, name, code, hostel_type, total_capacity, status, created_at, updated_at) VALUES
(1, 'Boys Hostel Block A', 'BHA', 'BOYS', 200, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Girls Hostel Block A', 'GHA', 'GIRLS', 150, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Grievances (parent for grievance comments)
INSERT INTO grievances (id, ticket_number, complainant_keycloak_id, complainant_name, complainant_role, category, subject, description, priority, status, is_anonymous, created_at, updated_at) VALUES
(1, 'GRV-2024-001', 'student-kc-001', 'Alice Johnson', 'STUDENT', 'INFRASTRUCTURE', 'Broken AC in Room 301', 'The air conditioner in room 301 has not been working for the past week.', 'HIGH', 'OPEN', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Books (parent for book reservations)
INSERT INTO books (id, isbn, title, author, publisher, category, language, shelf_location, total_copies, available_copies, status, created_at, updated_at) VALUES
(1, '978-0-262-03384-8', 'Introduction to Algorithms', 'Thomas H. Cormen', 'MIT Press', 'Computer Science', 'English', 'CS-A-01', 5, 3, 'AVAILABLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '978-0-13-468599-1', 'Database System Concepts', 'Abraham Silberschatz', 'McGraw-Hill', 'Computer Science', 'English', 'CS-A-02', 4, 2, 'AVAILABLE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Library Members (parent for book reservations, library fines)
INSERT INTO library_members (id, membership_number, student_profile_id, member_type, max_books_allowed, status, created_at, updated_at) VALUES
(1, 'LIB-STU-001', 1, 'STUDENT', 5, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'LIB-STU-002', 2, 'STUDENT', 5, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Book Issues (parent for library fines)
INSERT INTO book_issues (id, book_id, member_id, issue_date, due_date, return_date, issued_by, status, created_at, updated_at) VALUES
(1, 1, 1, '2024-10-01', '2024-10-15', '2024-10-20', 'Librarian Admin', 'RETURNED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, '2024-10-05', '2024-10-19', NULL, 'Librarian Admin', 'ISSUED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Financial Years
INSERT INTO financial_years (id, name, start_date, end_date, active, created_at, updated_at) VALUES
(1, '2023-24', '2023-04-01', '2024-03-31', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, '2024-25', '2024-04-01', '2025-03-31', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Vehicles
INSERT INTO vehicles (id, type, registration_number, capacity, driver_name, driver_phone, insurance_expiry, status, created_at, updated_at) VALUES
(1, 'BUS', 'KA-01-AB-1234', 50, 'Raju Kumar', '9876543210', '2025-12-31', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'BUS', 'KA-01-CD-5678', 40, 'Suresh Babu', '9876543211', '2025-06-30', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'VAN', 'KA-01-EF-9012', 15, 'Mohan Das', '9876543212', '2025-09-30', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Transport Routes
INSERT INTO transport_routes (id, route_name, stops, start_time, end_time, vehicle_id, distance_km, status, created_at, updated_at) VALUES
(1, 'Route A - City Center', 'City Center, MG Road, Indiranagar, College', '07:30:00', '08:30:00', 1, 25.5, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Route B - Whitefield', 'Whitefield, Marathahalli, Bellandur, College', '07:00:00', '08:15:00', 2, 30.0, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Route C - Electronic City', 'Electronic City, Silk Board, College', '07:15:00', '08:30:00', 3, 20.0, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Transport Passes
INSERT INTO transport_passes (id, student_id, route_id, valid_from, valid_to, pass_type, fee, status, created_at, updated_at) VALUES
(1, 1, 1, '2024-07-01', '2024-12-31', 'SEMESTER', 5000.00, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, '2024-07-01', '2024-12-31', 'SEMESTER', 6000.00, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Buildings
INSERT INTO buildings (id, name, code, floors, address, status, created_at, updated_at) VALUES
(1, 'Main Academic Block', 'MAB', 5, 'Campus North Wing', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Science Block', 'SB', 4, 'Campus East Wing', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Administrative Block', 'AB', 3, 'Campus Main Entrance', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Campus Facilities
INSERT INTO campus_facilities (id, name, type, capacity, location, description, status, created_at, updated_at) VALUES
(1, 'Central Library', 'LIBRARY', 500, 'Main Campus Block A', 'Multi-floor library with digital resources', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Sports Complex', 'SPORTS', 200, 'South Campus', 'Indoor and outdoor sports facilities', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Auditorium', 'AUDITORIUM', 1000, 'Central Campus', 'Multi-purpose auditorium for events', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Cafeteria', 'FOOD', 300, 'Student Center', 'Multi-cuisine food court', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Maintenance Requests
INSERT INTO maintenance_requests (id, requester_keycloak_id, requester_name, location, description, priority, status, assigned_to, created_at, updated_at) VALUES
(1, 'student-kc-001', 'Alice Johnson', 'Room 301, MAB', 'Projector not working in classroom 301', 'HIGH', 'OPEN', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'faculty-kc-001', 'Dr. Smith', 'Lab 201, SB', 'AC unit making noise', 'MEDIUM', 'IN_PROGRESS', 'Maintenance Team A', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Room Allocations
INSERT INTO room_allocations (id, room_id, purpose, day_of_week, start_time, end_time, course_id, semester_id, created_at) VALUES
(1, 1, 'LECTURE', 'MONDAY', '09:00:00', '10:00:00', 1, 1, CURRENT_TIMESTAMP),
(2, 1, 'LECTURE', 'WEDNESDAY', '09:00:00', '10:00:00', 1, 1, CURRENT_TIMESTAMP),
(3, 2, 'LAB', 'TUESDAY', '14:00:00', '16:00:00', 2, 1, CURRENT_TIMESTAMP);

-- Theory Timetables
INSERT INTO theory_timetables (id, course_id, faculty_id, room_id, semester_id, day_of_week, start_time, end_time, created_at, updated_at) VALUES
(1, 1, 1, 1, 1, 'MONDAY', '09:00:00', '10:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 2, 1, 'TUESDAY', '10:00:00', '11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 1, 1, 1, 'WEDNESDAY', '09:00:00', '10:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Salary Structures
INSERT INTO salary_structures (id, designation, basic_pay, da, hra, special_allowance, deductions, created_at, updated_at) VALUES
(1, 'Professor', 80000.00, 16000.00, 24000.00, 10000.00, 5000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Associate Professor', 60000.00, 12000.00, 18000.00, 8000.00, 4000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Assistant Professor', 45000.00, 9000.00, 13500.00, 5000.00, 3000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Payroll
INSERT INTO payrolls (id, faculty_id, pay_month, pay_year, gross_pay, deductions, net_pay, status, paid_date, created_at, updated_at) VALUES
(1, 1, 1, 2025, 130000.00, 5000.00, 125000.00, 'PAID', '2025-01-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 1, 2025, 98000.00, 4000.00, 94000.00, 'PAID', '2025-01-31', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Department Budgets
INSERT INTO department_budgets (id, department_id, financial_year_id, allocated_amount, utilized_amount, created_at, updated_at) VALUES
(1, 1, 2, 5000000.00, 2500000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 4000000.00, 1800000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 2, 3500000.00, 1200000.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Expenses
INSERT INTO expenses (id, department_id, category, description, amount, expense_date, approved_by, receipt_number, status, created_at, updated_at) VALUES
(1, 1, 'EQUIPMENT', 'New lab computers', 500000.00, '2024-09-15', 'Dr. Admin', 'RCP-2024-001', 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'SUPPLIES', 'Chemical reagents for labs', 75000.00, '2024-10-01', 'Dr. Admin', 'RCP-2024-002', 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Fee Installments
INSERT INTO fee_installments (id, fee_structure_id, student_id, installment_number, amount, due_date, paid_date, status, late_fee, created_at, updated_at) VALUES
(1, 1, 1, 1, 50000.00, '2024-07-15', '2024-07-10', 'PAID', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 1, 2, 50000.00, '2024-10-15', NULL, 'PENDING', 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 2, 1, 50000.00, '2024-07-15', '2024-07-20', 'PAID', 500.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Fee Concessions
INSERT INTO fee_concessions (id, student_id, fee_structure_id, concession_type, amount, reason, approved_by, status, created_at, updated_at) VALUES
(1, 1, 1, 'MERIT', 25000.00, 'Academic excellence - top 5 rank', 'Dr. Admin', 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 1, 'NEED_BASED', 15000.00, 'Financial hardship application', 'Dr. Admin', 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Refunds
INSERT INTO refunds (id, student_id, payment_id, amount, reason, approved_by, processed_date, status, created_at, updated_at) VALUES
(1, 1, 1, 5000.00, 'Excess fee paid', 'Finance Office', '2024-08-15', 'PROCESSED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Grade Scales
INSERT INTO grade_scales (id, grade, min_percentage, max_percentage, grade_point) VALUES
(1, 'O', 90.00, 100.00, 10.00),
(2, 'A+', 80.00, 89.99, 9.00),
(3, 'A', 70.00, 79.99, 8.00),
(4, 'B+', 60.00, 69.99, 7.00),
(5, 'B', 50.00, 59.99, 6.00),
(6, 'C', 40.00, 49.99, 5.00),
(7, 'F', 0.00, 39.99, 0.00);

-- Question Bank entries
INSERT INTO question_bank (id, course_id, unit, question, type, bloom_level, marks, created_by, created_at) VALUES
(1, 1, 1, 'Explain the principles of object-oriented programming', 'DESCRIPTIVE', 'UNDERSTAND', 10, 'Dr. Smith', CURRENT_TIMESTAMP),
(2, 1, 2, 'What is polymorphism? Give examples.', 'DESCRIPTIVE', 'APPLY', 5, 'Dr. Smith', CURRENT_TIMESTAMP),
(3, 2, 1, 'Write SQL query to join three tables', 'PRACTICAL', 'APPLY', 10, 'Dr. Williams', CURRENT_TIMESTAMP);

-- Question Papers
INSERT INTO question_papers (id, exam_id, course_id, total_marks, sections, approved_by, status, created_at, updated_at) VALUES
(1, 1, 1, 100, 'Section A: 10x2=20, Section B: 5x10=50, Section C: 2x15=30', 'Dr. HOD', 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Mess Menus
INSERT INTO mess_menus (id, hostel_id, day_of_week, meal_type, items, date) VALUES
(1, 1, 'MONDAY', 'BREAKFAST', 'Idli, Dosa, Chutney, Sambar, Tea, Coffee', NULL),
(2, 1, 'MONDAY', 'LUNCH', 'Rice, Dal, Paneer Curry, Roti, Salad, Curd', NULL),
(3, 1, 'MONDAY', 'DINNER', 'Rice, Sambar, Chapati, Mixed Veg, Dessert', NULL),
(4, 1, 'TUESDAY', 'BREAKFAST', 'Poha, Upma, Boiled Eggs, Bread, Butter, Tea', NULL),
(5, 1, 'TUESDAY', 'LUNCH', 'Rice, Rajma, Chicken Curry, Roti, Raita', NULL);

-- Hostel Fees
INSERT INTO hostel_fees (id, student_id, hostel_id, semester_id, room_fee, mess_fee, status, created_at, updated_at) VALUES
(1, 1, 1, 1, 25000.00, 15000.00, 'PAID', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 1, 1, 25000.00, 15000.00, 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Visitor Logs
INSERT INTO visitor_logs (id, student_id, visitor_name, relation, purpose, in_time, out_time) VALUES
(1, 1, 'Mr. Rajesh Johnson', 'Father', 'Parent-Teacher Meeting', CURRENT_TIMESTAMP, NULL),
(2, 2, 'Mrs. Priya Kumar', 'Mother', 'Weekend Visit', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Placement Offers
INSERT INTO placement_offers (id, student_id, company_id, drive_id, role, package_amount, joining_date, status, created_at, updated_at) VALUES
(1, 1, 1, 1, 'Software Engineer', 1200000.00, '2025-07-01', 'ACCEPTED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 1, 'Data Analyst', 800000.00, '2025-07-15', 'OFFERED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Internships
INSERT INTO internships (id, student_id, company_id, role, start_date, end_date, stipend, mentor_name, status, created_at, updated_at) VALUES
(1, 1, 1, 'Software Engineering Intern', '2024-06-01', '2024-08-31', 30000.00, 'Mr. Tech Lead', 'COMPLETED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2, 'Data Science Intern', '2024-06-15', '2024-09-15', 25000.00, 'Dr. Data Scientist', 'ONGOING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Placement Statistics
INSERT INTO placement_statistics (id, academic_year_id, department_id, total_eligible, total_placed, highest_package, average_package, median_package) VALUES
(1, 1, 1, 120, 105, 4500000.00, 1200000.00, 1000000.00),
(2, 1, 2, 80, 65, 3500000.00, 900000.00, 800000.00);

-- Faculty Workloads
INSERT INTO faculty_workloads (id, faculty_id, semester_id, teaching_hours, lab_hours, admin_hours, research_hours, created_at, updated_at) VALUES
(1, 1, 1, 16, 4, 4, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 1, 14, 6, 2, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Faculty Appraisals
INSERT INTO faculty_appraisals (id, faculty_id, appraisal_year, teaching_rating, research_rating, service_rating, overall_rating, reviewed_by, remarks, status, created_at, updated_at) VALUES
(1, 1, 2024, 8.50, 9.00, 7.50, 8.33, 'Dr. Dean', 'Excellent teaching and research contributions', 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 2024, 7.80, 8.50, 8.00, 8.10, 'Dr. Dean', 'Very good overall performance', 'APPROVED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Leave Types
INSERT INTO leave_types (id, name, description, max_days_per_year, is_paid) VALUES
(1, 'Casual Leave', 'For personal/casual reasons', 12, true),
(2, 'Sick Leave', 'For medical reasons with certificate', 10, true),
(3, 'Earned Leave', 'Accrued leave for long service', 30, true),
(4, 'Academic Leave', 'For conferences, workshops, research', 15, true);

-- Committee Memberships
INSERT INTO committee_memberships (id, faculty_id, committee_name, role, start_date, end_date, status, created_at, updated_at) VALUES
(1, 1, 'Academic Council', 'Member', '2024-01-01', '2025-12-31', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'Examination Committee', 'Chairperson', '2024-01-01', '2025-12-31', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 1, 'Research Ethics Board', 'Member', '2024-06-01', NULL, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Notifications
INSERT INTO notifications (id, recipient_keycloak_id, title, message, type, read_status, link, created_at) VALUES
(1, 'student-kc-001', 'Fee Payment Reminder', 'Your semester fee installment is due on Oct 15', 'FINANCE', false, '/fees/payments', CURRENT_TIMESTAMP),
(2, 'student-kc-001', 'New Placement Drive', 'TechCorp is visiting campus on Nov 20', 'PLACEMENT', false, '/placement/drives/1', CURRENT_TIMESTAMP),
(3, 'faculty-kc-001', 'Leave Approved', 'Your casual leave for Dec 25-26 has been approved', 'LEAVE', true, '/leaves/my', CURRENT_TIMESTAMP);

-- Notification Preferences
INSERT INTO notification_preferences (id, keycloak_id, category, email_enabled, sms_enabled, push_enabled) VALUES
(1, 'student-kc-001', 'ACADEMIC', true, false, true),
(2, 'student-kc-001', 'FINANCE', true, true, true),
(3, 'faculty-kc-001', 'GENERAL', true, false, true);

-- Message Threads
INSERT INTO message_threads (id, subject, participants, last_message_at, status, created_at) VALUES
(1, 'Project Guidance Query', 'student-kc-001,faculty-kc-001', CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP),
(2, 'Leave Request Discussion', 'faculty-kc-001,admin-kc-001', CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP);

-- Messages
INSERT INTO messages (id, sender_keycloak_id, receiver_keycloak_id, subject, body, read_status, thread_id, created_at) VALUES
(1, 'student-kc-001', 'faculty-kc-001', 'Project Guidance', 'Dear Professor, I need guidance on my final year project topic.', true, 1, CURRENT_TIMESTAMP),
(2, 'faculty-kc-001', 'student-kc-001', 'Re: Project Guidance', 'Please come to my office hours on Monday to discuss.', false, 1, CURRENT_TIMESTAMP);

-- Grievance Comments
INSERT INTO grievance_comments (id, grievance_id, commenter_keycloak_id, commenter_name, comment, is_internal, created_at) VALUES
(1, 1, 'admin-kc-001', 'Admin User', 'This issue has been forwarded to the maintenance department.', false, CURRENT_TIMESTAMP),
(2, 1, 'admin-kc-001', 'Admin User', 'Internal note: Requires budget approval for repair.', true, CURRENT_TIMESTAMP);

-- Feedback Forms
INSERT INTO feedback_forms (id, title, target_audience, questions, start_date, end_date, is_anonymous, status, created_at, updated_at) VALUES
(1, 'Mid-Semester Course Feedback', 'STUDENTS', '[{"q":"Rate teaching quality","type":"rating"},{"q":"Suggestions for improvement","type":"text"}]', '2024-10-01', '2024-10-15', true, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Campus Facilities Survey', 'ALL', '[{"q":"Rate library facilities","type":"rating"},{"q":"Rate cafeteria quality","type":"rating"}]', '2024-11-01', '2024-11-30', false, 'DRAFT', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Feedback Responses
INSERT INTO feedback_responses (id, form_id, respondent_keycloak_id, answers, submitted_at) VALUES
(1, 1, 'student-kc-001', '{"q1":4,"q2":"More practical examples needed"}', CURRENT_TIMESTAMP),
(2, 1, NULL, '{"q1":5,"q2":"Excellent course content"}', CURRENT_TIMESTAMP);

-- Book Reservations
INSERT INTO book_reservations (id, member_id, book_id, reservation_date, queue_position, status, created_at) VALUES
(1, 1, 1, '2024-10-15', 1, 'ACTIVE', CURRENT_TIMESTAMP),
(2, 2, 2, '2024-10-16', 1, 'FULFILLED', CURRENT_TIMESTAMP);

-- Digital Resources
INSERT INTO digital_resources (id, title, type, url, access_level, publisher, author, description, status, created_at, updated_at) VALUES
(1, 'Introduction to Algorithms - PDF', 'E_BOOK', 'https://library.example.com/ebooks/intro-algorithms', 'ALL', 'MIT Press', 'CLRS', 'Classic algorithms textbook digital version', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Database Systems Video Lectures', 'VIDEO', 'https://library.example.com/videos/dbms', 'STUDENTS', 'College Media', 'Dr. Williams', 'Complete DBMS course video series', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'IEEE Research Paper Database', 'JOURNAL', 'https://library.example.com/ieee', 'FACULTY', 'IEEE', NULL, 'Access to IEEE digital library', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Library Fines
INSERT INTO library_fines (id, member_id, book_issue_id, amount, reason, paid_date, status, created_at) VALUES
(1, 1, 1, 50.00, 'Late return - 5 days overdue', '2024-10-20', 'PAID', CURRENT_TIMESTAMP),
(2, 2, 2, 100.00, 'Late return - 10 days overdue', NULL, 'UNPAID', CURRENT_TIMESTAMP);

-- Course Feedback
INSERT INTO course_feedback (id, course_id, semester_id, student_id, rating, comments, submitted_at) VALUES
(1, 1, 1, 1, 4.50, 'Excellent teaching methodology and course content', CURRENT_TIMESTAMP),
(2, 1, 1, 2, 3.80, 'Good course but needs more practical examples', CURRENT_TIMESTAMP),
(3, 2, 1, 1, 4.20, 'Very informative lab sessions', CURRENT_TIMESTAMP);

-- Exam Seating Plans
INSERT INTO exam_seating_plans (id, exam_id, student_id, room_number, seat_number) VALUES
(1, 1, 1, 'Hall-A', 'A-01'),
(2, 1, 2, 'Hall-A', 'A-02');

-- Revaluation Requests
INSERT INTO revaluation_requests (id, student_id, exam_id, reason, status, revised_marks, created_at, updated_at) VALUES
(1, 1, 1, 'I believe my answer for Q5 was graded incorrectly', 'PENDING', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Staff
INSERT INTO staff (id, keycloak_id, employee_code, first_name, last_name, email, phone, designation, staff_type, department_id, joining_date, status, created_at, updated_at) VALUES
(1, 'staff-kc-001', 'STF-001', 'Ramesh', 'Kumar', 'ramesh.kumar@cms.edu', '9876543220', 'Lab Technician', 'TECHNICAL', 1, '2020-01-15', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'staff-kc-002', 'STF-002', 'Lakshmi', 'Devi', 'lakshmi.devi@cms.edu', '9876543221', 'Office Assistant', 'ADMINISTRATIVE', 3, '2019-06-01', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- ID sequence resets for new tables
ALTER TABLE financial_years          ALTER COLUMN id RESTART WITH 100;
ALTER TABLE vehicles                 ALTER COLUMN id RESTART WITH 100;
ALTER TABLE transport_routes         ALTER COLUMN id RESTART WITH 100;
ALTER TABLE transport_passes         ALTER COLUMN id RESTART WITH 100;
ALTER TABLE buildings                ALTER COLUMN id RESTART WITH 100;
ALTER TABLE campus_facilities        ALTER COLUMN id RESTART WITH 100;
ALTER TABLE maintenance_requests     ALTER COLUMN id RESTART WITH 100;
ALTER TABLE room_allocations         ALTER COLUMN id RESTART WITH 100;
ALTER TABLE theory_timetables        ALTER COLUMN id RESTART WITH 100;
ALTER TABLE salary_structures        ALTER COLUMN id RESTART WITH 100;
ALTER TABLE payrolls                 ALTER COLUMN id RESTART WITH 100;
ALTER TABLE department_budgets       ALTER COLUMN id RESTART WITH 100;
ALTER TABLE expenses                 ALTER COLUMN id RESTART WITH 100;
ALTER TABLE fee_installments         ALTER COLUMN id RESTART WITH 100;
ALTER TABLE fee_concessions          ALTER COLUMN id RESTART WITH 100;
ALTER TABLE refunds                  ALTER COLUMN id RESTART WITH 100;
ALTER TABLE grade_scales             ALTER COLUMN id RESTART WITH 100;
ALTER TABLE question_bank            ALTER COLUMN id RESTART WITH 100;
ALTER TABLE question_papers          ALTER COLUMN id RESTART WITH 100;
ALTER TABLE mess_menus               ALTER COLUMN id RESTART WITH 100;
ALTER TABLE hostel_fees              ALTER COLUMN id RESTART WITH 100;
ALTER TABLE visitor_logs             ALTER COLUMN id RESTART WITH 100;
ALTER TABLE placement_offers         ALTER COLUMN id RESTART WITH 100;
ALTER TABLE internships              ALTER COLUMN id RESTART WITH 100;
ALTER TABLE placement_statistics     ALTER COLUMN id RESTART WITH 100;
ALTER TABLE faculty_workloads        ALTER COLUMN id RESTART WITH 100;
ALTER TABLE faculty_appraisals       ALTER COLUMN id RESTART WITH 100;
ALTER TABLE leave_types              ALTER COLUMN id RESTART WITH 100;
ALTER TABLE committee_memberships    ALTER COLUMN id RESTART WITH 100;
ALTER TABLE notifications            ALTER COLUMN id RESTART WITH 100;
ALTER TABLE notification_preferences ALTER COLUMN id RESTART WITH 100;
ALTER TABLE message_threads          ALTER COLUMN id RESTART WITH 100;
ALTER TABLE messages                 ALTER COLUMN id RESTART WITH 100;
ALTER TABLE grievance_comments       ALTER COLUMN id RESTART WITH 100;
ALTER TABLE feedback_forms           ALTER COLUMN id RESTART WITH 100;
ALTER TABLE feedback_responses       ALTER COLUMN id RESTART WITH 100;
ALTER TABLE book_reservations        ALTER COLUMN id RESTART WITH 100;
ALTER TABLE digital_resources        ALTER COLUMN id RESTART WITH 100;
ALTER TABLE library_fines            ALTER COLUMN id RESTART WITH 100;
ALTER TABLE course_feedback          ALTER COLUMN id RESTART WITH 100;
ALTER TABLE exam_seating_plans       ALTER COLUMN id RESTART WITH 100;
ALTER TABLE revaluation_requests     ALTER COLUMN id RESTART WITH 100;
ALTER TABLE staff                    ALTER COLUMN id RESTART WITH 100;
ALTER TABLE companies                ALTER COLUMN id RESTART WITH 100;
ALTER TABLE placement_drives         ALTER COLUMN id RESTART WITH 100;
ALTER TABLE exam_schedules           ALTER COLUMN id RESTART WITH 100;
ALTER TABLE hostels                  ALTER COLUMN id RESTART WITH 100;
ALTER TABLE grievances               ALTER COLUMN id RESTART WITH 100;
ALTER TABLE books                    ALTER COLUMN id RESTART WITH 100;
ALTER TABLE library_members          ALTER COLUMN id RESTART WITH 100;
ALTER TABLE book_issues              ALTER COLUMN id RESTART WITH 100;
