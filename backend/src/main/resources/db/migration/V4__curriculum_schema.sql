-- V4__curriculum_schema.sql
-- Curriculum module: syllabi, course outcomes, experiments, experiment-outcome mappings.

CREATE TABLE syllabi (
    id               BIGSERIAL PRIMARY KEY,
    course_id        BIGINT       NOT NULL REFERENCES courses(id),
    academic_year_id BIGINT       NOT NULL REFERENCES academic_years(id),
    content          TEXT,
    version          INTEGER      NOT NULL DEFAULT 1,
    approved         BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(100),
    updated_by       VARCHAR(100),
    UNIQUE (course_id, academic_year_id)
);

CREATE TABLE course_outcomes (
    id          BIGSERIAL PRIMARY KEY,
    course_id   BIGINT       NOT NULL REFERENCES courses(id),
    code        VARCHAR(10)  NOT NULL,
    description TEXT         NOT NULL,
    bloom_level VARCHAR(20)  NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100),
    UNIQUE (course_id, code)
);

CREATE TABLE experiments (
    id               BIGSERIAL PRIMARY KEY,
    course_id        BIGINT       NOT NULL REFERENCES courses(id),
    name             VARCHAR(200) NOT NULL,
    description      TEXT,
    objective        TEXT,
    procedure        TEXT,
    pre_requirements TEXT,
    sequence_order   INTEGER      NOT NULL DEFAULT 0,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by       VARCHAR(100),
    updated_by       VARCHAR(100)
);

CREATE TABLE experiment_outcome_mappings (
    experiment_id     BIGINT      NOT NULL REFERENCES experiments(id),
    course_outcome_id BIGINT      NOT NULL REFERENCES course_outcomes(id),
    mapping_level     VARCHAR(10) NOT NULL,
    created_at        TIMESTAMP   NOT NULL DEFAULT NOW(),
    PRIMARY KEY (experiment_id, course_outcome_id)
);

CREATE INDEX idx_syllabi_course     ON syllabi(course_id);
CREATE INDEX idx_outcomes_course    ON course_outcomes(course_id);
CREATE INDEX idx_experiments_course ON experiments(course_id);
