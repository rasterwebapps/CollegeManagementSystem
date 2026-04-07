-- V5__faculty_schema.sql
-- Faculty module: profiles, lab expertise, lab assignments.

CREATE TABLE faculty_profiles (
    id                BIGSERIAL PRIMARY KEY,
    keycloak_user_id  VARCHAR(100) NOT NULL UNIQUE,
    employee_id       VARCHAR(50)  NOT NULL UNIQUE,
    department_id     BIGINT       NOT NULL REFERENCES departments(id),
    designation       VARCHAR(100),
    qualification     VARCHAR(200),
    specialization    VARCHAR(200),
    joining_date      DATE,
    phone             VARCHAR(20),
    active            BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by        VARCHAR(100),
    updated_by        VARCHAR(100)
);

CREATE TABLE faculty_lab_expertise (
    id                BIGSERIAL PRIMARY KEY,
    faculty_id        BIGINT      NOT NULL REFERENCES faculty_profiles(id),
    lab_type_id       BIGINT      NOT NULL REFERENCES lab_types(id),
    proficiency_level VARCHAR(20) NOT NULL,
    certified         BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at        TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP   NOT NULL DEFAULT NOW(),
    created_by        VARCHAR(100),
    updated_by        VARCHAR(100),
    UNIQUE (faculty_id, lab_type_id)
);

CREATE TABLE faculty_lab_assignments (
    id               BIGSERIAL PRIMARY KEY,
    faculty_id       BIGINT    NOT NULL REFERENCES faculty_profiles(id),
    lab_id           BIGINT    NOT NULL REFERENCES labs(id),
    course_id        BIGINT    NOT NULL REFERENCES courses(id),
    academic_year_id BIGINT    NOT NULL REFERENCES academic_years(id),
    semester_id      BIGINT    NOT NULL REFERENCES semesters(id),
    created_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(100),
    updated_by       VARCHAR(100)
);

CREATE INDEX idx_faculty_department  ON faculty_profiles(department_id);
CREATE INDEX idx_fac_expertise_fac   ON faculty_lab_expertise(faculty_id);
CREATE INDEX idx_fac_assignment_fac  ON faculty_lab_assignments(faculty_id);
CREATE INDEX idx_fac_assignment_lab  ON faculty_lab_assignments(lab_id);
