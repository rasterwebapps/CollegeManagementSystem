-- Phase 5: Assessment & Reporting
-- Tables: practical_exams, continuous_evaluations, gpa_records, accreditation_reports, lab_utilization_kpis, analytics_snapshots

CREATE TABLE practical_exams (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL REFERENCES courses(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    experiment_id BIGINT REFERENCES experiments(id),
    exam_date DATE NOT NULL,
    start_time VARCHAR(10),
    end_time VARCHAR(10),
    lab_id BIGINT REFERENCES labs(id),
    max_marks DECIMAL(5,2) NOT NULL,
    passing_marks DECIMAL(5,2),
    examiner VARCHAR(255),
    status VARCHAR(30) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE continuous_evaluations (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student_profiles(id),
    course_id BIGINT NOT NULL REFERENCES courses(id),
    experiment_id BIGINT REFERENCES experiments(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    evaluation_type VARCHAR(50) NOT NULL,
    marks_obtained DECIMAL(5,2) NOT NULL,
    max_marks DECIMAL(5,2) NOT NULL,
    evaluation_date DATE NOT NULL,
    remarks VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE gpa_records (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student_profiles(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    semester_gpa DECIMAL(4,2) NOT NULL,
    cgpa DECIMAL(4,2) NOT NULL,
    total_credits INTEGER NOT NULL,
    earned_credits INTEGER NOT NULL,
    lab_component_gpa DECIMAL(4,2),
    calculation_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'CALCULATED',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE accreditation_reports (
    id BIGSERIAL PRIMARY KEY,
    department_id BIGINT NOT NULL REFERENCES departments(id),
    report_type VARCHAR(50) NOT NULL,
    academic_year VARCHAR(20) NOT NULL,
    generated_date DATE NOT NULL,
    overall_score DECIMAL(5,2),
    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
    summary TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE lab_utilization_kpis (
    id BIGSERIAL PRIMARY KEY,
    lab_id BIGINT NOT NULL REFERENCES labs(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    total_slots INTEGER NOT NULL DEFAULT 0,
    utilized_slots INTEGER NOT NULL DEFAULT 0,
    utilization_percentage DECIMAL(5,2),
    peak_hours VARCHAR(100),
    avg_occupancy DECIMAL(5,2),
    measurement_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE analytics_snapshots (
    id BIGSERIAL PRIMARY KEY,
    department_id BIGINT NOT NULL REFERENCES departments(id),
    program_id BIGINT REFERENCES programs(id),
    semester_id BIGINT NOT NULL REFERENCES semesters(id),
    snapshot_type VARCHAR(50) NOT NULL,
    metric_name VARCHAR(100) NOT NULL,
    metric_value DECIMAL(12,2) NOT NULL,
    snapshot_date DATE NOT NULL,
    details TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);
