-- V3__lab_setup_schema.sql
-- Lab setup module: lab types, labs, lab staff assignments.

CREATE TABLE lab_types (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL UNIQUE,
    description TEXT,
    created_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by  VARCHAR(100),
    updated_by  VARCHAR(100)
);

CREATE TABLE labs (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(200)  NOT NULL,
    lab_type_id   BIGINT        NOT NULL REFERENCES lab_types(id),
    department_id BIGINT        NOT NULL REFERENCES departments(id),
    building      VARCHAR(100),
    floor         VARCHAR(20),
    room_number   VARCHAR(20),
    capacity      INTEGER       NOT NULL DEFAULT 0,
    description   TEXT,
    status        VARCHAR(30)   NOT NULL DEFAULT 'ACTIVE',
    active        BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP     NOT NULL DEFAULT NOW(),
    created_by    VARCHAR(100),
    updated_by    VARCHAR(100)
);

CREATE TABLE lab_staff_assignments (
    id                BIGSERIAL PRIMARY KEY,
    lab_id            BIGINT       NOT NULL REFERENCES labs(id),
    user_keycloak_id  VARCHAR(100) NOT NULL,
    role              VARCHAR(20)  NOT NULL,
    assigned_date     DATE         NOT NULL DEFAULT CURRENT_DATE,
    created_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
    created_by        VARCHAR(100),
    updated_by        VARCHAR(100)
);

CREATE INDEX idx_labs_type       ON labs(lab_type_id);
CREATE INDEX idx_labs_department ON labs(department_id);
CREATE INDEX idx_lab_staff_lab   ON lab_staff_assignments(lab_id);
