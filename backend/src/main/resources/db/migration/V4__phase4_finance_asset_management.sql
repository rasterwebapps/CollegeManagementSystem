-- V4__phase4_finance_asset_management.sql
-- Phase 4: Finance, Lab Inventory, and Infrastructure

-- =============================================
-- 4.1 Finance
-- =============================================

CREATE TABLE fee_structures (
    id BIGSERIAL PRIMARY KEY,
    program_id BIGINT NOT NULL REFERENCES programs(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    fee_type VARCHAR(50) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'INR',
    effective_from DATE NOT NULL,
    effective_to DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE lab_fees (
    id BIGSERIAL PRIMARY KEY,
    lab_id BIGINT NOT NULL REFERENCES labs(id),
    course_id BIGINT NOT NULL REFERENCES courses(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    amount DECIMAL(12,2) NOT NULL,
    description TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE scholarships (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student_profiles(id),
    name VARCHAR(200) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    scholarship_type VARCHAR(50) NOT NULL,
    awarded_date DATE NOT NULL,
    expiry_date DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student_profiles(id),
    fee_structure_id BIGINT REFERENCES fee_structures(id),
    amount DECIMAL(12,2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    transaction_reference VARCHAR(100),
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =============================================
-- 4.2 Lab Inventory
-- =============================================

CREATE TABLE equipments (
    id BIGSERIAL PRIMARY KEY,
    lab_id BIGINT NOT NULL REFERENCES labs(id),
    name VARCHAR(200) NOT NULL,
    model_number VARCHAR(100),
    serial_number VARCHAR(100) UNIQUE,
    purchase_date DATE,
    purchase_cost DECIMAL(12,2),
    warranty_expiry DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'OPERATIONAL',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE consumable_stocks (
    id BIGSERIAL PRIMARY KEY,
    lab_id BIGINT NOT NULL REFERENCES labs(id),
    item_name VARCHAR(200) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    unit VARCHAR(30) NOT NULL,
    minimum_threshold INT NOT NULL DEFAULT 0,
    unit_cost DECIMAL(12,2),
    last_restocked DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'IN_STOCK',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE maintenance_schedules (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL REFERENCES equipments(id),
    maintenance_type VARCHAR(50) NOT NULL,
    scheduled_date DATE NOT NULL,
    completed_date DATE,
    performed_by VARCHAR(255),
    cost DECIMAL(12,2),
    notes TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =============================================
-- 4.3 Infrastructure
-- =============================================

CREATE TABLE assets (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT REFERENCES equipments(id),
    asset_code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    category VARCHAR(100) NOT NULL,
    purchase_date DATE,
    purchase_cost DECIMAL(12,2),
    current_value DECIMAL(12,2),
    useful_life_years INT,
    depreciation_method VARCHAR(50) DEFAULT 'STRAIGHT_LINE',
    location VARCHAR(200),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE depreciations (
    id BIGSERIAL PRIMARY KEY,
    asset_id BIGINT NOT NULL REFERENCES assets(id),
    fiscal_year VARCHAR(20) NOT NULL,
    opening_value DECIMAL(12,2) NOT NULL,
    depreciation_amount DECIMAL(12,2) NOT NULL,
    closing_value DECIMAL(12,2) NOT NULL,
    calculated_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE room_resources (
    id BIGSERIAL PRIMARY KEY,
    room_number VARCHAR(50) NOT NULL,
    building VARCHAR(100) NOT NULL,
    floor INT,
    room_type VARCHAR(50) NOT NULL,
    capacity INT,
    lab_id BIGINT REFERENCES labs(id),
    status VARCHAR(30) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
