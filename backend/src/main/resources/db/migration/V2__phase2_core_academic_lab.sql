-- V2__phase2_core_academic_lab.sql
-- Phase 2: Core Academic & Lab Mapping schema

-- =============================================
-- 2.1 Administration
-- =============================================

CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE programs (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    department_id BIGINT NOT NULL REFERENCES departments(id),
    duration_years INT NOT NULL,
    degree_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    program_id BIGINT NOT NULL REFERENCES programs(id),
    credits INT NOT NULL,
    course_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE academic_years (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE semesters (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    semester_number INT NOT NULL,
    academic_year_id BIGINT NOT NULL REFERENCES academic_years(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (academic_year_id, semester_number)
);

CREATE TABLE calendar_events (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    event_date DATE NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    academic_year_id BIGINT REFERENCES academic_years(id),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =============================================
-- 2.2 Lab Setup
-- =============================================

CREATE TABLE lab_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE labs (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    lab_type_id BIGINT NOT NULL REFERENCES lab_types(id),
    department_id BIGINT NOT NULL REFERENCES departments(id),
    location VARCHAR(200),
    capacity INT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE lab_staff_assignments (
    id BIGSERIAL PRIMARY KEY,
    lab_id BIGINT NOT NULL REFERENCES labs(id),
    staff_keycloak_id VARCHAR(255) NOT NULL,
    staff_role VARCHAR(50) NOT NULL,
    assigned_date DATE NOT NULL,
    end_date DATE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (lab_id, staff_keycloak_id, staff_role)
);

-- =============================================
-- 2.3 Academic & Curriculum
-- =============================================

CREATE TABLE syllabi (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL REFERENCES courses(id),
    academic_year_id BIGINT NOT NULL REFERENCES academic_years(id),
    content TEXT,
    objectives TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (course_id, academic_year_id)
);

CREATE TABLE course_outcomes (
    id BIGSERIAL PRIMARY KEY,
    syllabus_id BIGINT NOT NULL REFERENCES syllabi(id),
    code VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    bloom_level VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE experiments (
    id BIGSERIAL PRIMARY KEY,
    syllabus_id BIGINT NOT NULL REFERENCES syllabi(id),
    experiment_number INT NOT NULL,
    title VARCHAR(300) NOT NULL,
    description TEXT,
    lab_type_id BIGINT REFERENCES lab_types(id),
    duration_hours INT NOT NULL DEFAULT 2,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (syllabus_id, experiment_number)
);

CREATE TABLE experiment_outcome_mappings (
    id BIGSERIAL PRIMARY KEY,
    experiment_id BIGINT NOT NULL REFERENCES experiments(id),
    course_outcome_id BIGINT NOT NULL REFERENCES course_outcomes(id),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (experiment_id, course_outcome_id)
);

-- =============================================
-- 2.4 Faculty
-- =============================================

CREATE TABLE faculty_profiles (
    id BIGSERIAL PRIMARY KEY,
    keycloak_id VARCHAR(255) NOT NULL UNIQUE,
    employee_code VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    department_id BIGINT NOT NULL REFERENCES departments(id),
    designation VARCHAR(100) NOT NULL,
    joining_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE faculty_lab_expertise (
    id BIGSERIAL PRIMARY KEY,
    faculty_id BIGINT NOT NULL REFERENCES faculty_profiles(id),
    lab_type_id BIGINT NOT NULL REFERENCES lab_types(id),
    proficiency_level VARCHAR(50) NOT NULL,
    certified_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (faculty_id, lab_type_id)
);

CREATE TABLE faculty_lab_assignments (
    id BIGSERIAL PRIMARY KEY,
    faculty_id BIGINT NOT NULL REFERENCES faculty_profiles(id),
    lab_id BIGINT NOT NULL REFERENCES labs(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    assigned_date DATE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (faculty_id, lab_id, semester_id)
);
