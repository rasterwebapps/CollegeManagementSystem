-- V2__administration_schema.sql
-- Administration module: departments, programs, courses, academic years, semesters, calendar events.

CREATE TABLE departments (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200)  NOT NULL,
    code        VARCHAR(20)   NOT NULL UNIQUE,
    head_name   VARCHAR(200),
    description TEXT,
    active      BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100)
);

CREATE TABLE programs (
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(200)  NOT NULL,
    code           VARCHAR(20)   NOT NULL UNIQUE,
    department_id  BIGINT        NOT NULL REFERENCES departments(id),
    degree_type    VARCHAR(20)   NOT NULL,
    duration_years INTEGER       NOT NULL,
    total_credits  INTEGER,
    active         BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by     VARCHAR(100),
    updated_by     VARCHAR(100)
);

CREATE TABLE courses (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(200)  NOT NULL,
    code            VARCHAR(20)   NOT NULL UNIQUE,
    program_id      BIGINT        NOT NULL REFERENCES programs(id),
    department_id   BIGINT        NOT NULL REFERENCES departments(id),
    credits         INTEGER       NOT NULL,
    theory_hours    INTEGER       NOT NULL DEFAULT 0,
    practical_hours INTEGER       NOT NULL DEFAULT 0,
    course_type     VARCHAR(30)   NOT NULL,
    semester_number INTEGER       NOT NULL,
    active          BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by      VARCHAR(100),
    updated_by      VARCHAR(100)
);

CREATE TABLE academic_years (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50)  NOT NULL UNIQUE,
    start_date DATE         NOT NULL,
    end_date   DATE         NOT NULL,
    current    BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by VARCHAR(100),
    updated_by VARCHAR(100)
);

CREATE TABLE semesters (
    id               BIGSERIAL PRIMARY KEY,
    academic_year_id BIGINT      NOT NULL REFERENCES academic_years(id),
    name             VARCHAR(50) NOT NULL,
    number           INTEGER     NOT NULL,
    start_date       DATE        NOT NULL,
    end_date         DATE        NOT NULL,
    created_at       TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP   NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(100),
    updated_by       VARCHAR(100)
);

CREATE TABLE calendar_events (
    id               BIGSERIAL PRIMARY KEY,
    academic_year_id BIGINT       NOT NULL REFERENCES academic_years(id),
    title            VARCHAR(200) NOT NULL,
    event_type       VARCHAR(30)  NOT NULL,
    start_date       DATE         NOT NULL,
    end_date         DATE         NOT NULL,
    description      TEXT,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(100),
    updated_by       VARCHAR(100)
);

CREATE INDEX idx_programs_department ON programs(department_id);
CREATE INDEX idx_courses_program    ON courses(program_id);
CREATE INDEX idx_courses_department ON courses(department_id);
CREATE INDEX idx_semesters_year     ON semesters(academic_year_id);
CREATE INDEX idx_events_year        ON calendar_events(academic_year_id);
