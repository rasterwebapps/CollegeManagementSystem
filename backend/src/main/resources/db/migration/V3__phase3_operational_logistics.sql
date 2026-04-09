-- V3__phase3_operational_logistics.sql
-- Phase 3: Scheduling, Student Management, and Attendance

-- =============================================
-- 3.1 Lab Scheduling
-- =============================================

CREATE TABLE lab_timetables (
    id BIGSERIAL PRIMARY KEY,
    lab_id BIGINT NOT NULL REFERENCES labs(id),
    course_id BIGINT NOT NULL REFERENCES courses(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    faculty_id BIGINT NOT NULL REFERENCES faculty_profiles(id),
    day_of_week VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    batch_group VARCHAR(10),
    status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =============================================
-- 3.2 Student Management
-- =============================================

CREATE TABLE student_profiles (
    id BIGSERIAL PRIMARY KEY,
    keycloak_id VARCHAR(255) NOT NULL UNIQUE,
    enrollment_number VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    program_id BIGINT NOT NULL REFERENCES programs(id),
    department_id BIGINT NOT NULL REFERENCES departments(id),
    current_semester INT NOT NULL DEFAULT 1,
    admission_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE admissions (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student_profiles(id),
    application_number VARCHAR(50) NOT NULL UNIQUE,
    program_id BIGINT NOT NULL REFERENCES programs(id),
    admission_date DATE NOT NULL,
    admission_type VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE student_enrollments (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student_profiles(id),
    course_id BIGINT NOT NULL REFERENCES courses(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    lab_batch_group VARCHAR(10),
    enrollment_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ENROLLED',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =============================================
-- 3.3 Attendance
-- =============================================

CREATE TABLE lab_attendances (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student_profiles(id),
    experiment_id BIGINT NOT NULL REFERENCES experiments(id),
    lab_timetable_id BIGINT NOT NULL REFERENCES lab_timetables(id),
    attendance_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE attendance_alerts (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student_profiles(id),
    course_id BIGINT NOT NULL REFERENCES courses(id),
    alert_type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    threshold_percentage DECIMAL(5,2),
    current_percentage DECIMAL(5,2),
    resolved BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
