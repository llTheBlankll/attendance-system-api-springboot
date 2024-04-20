CREATE TABLE Strand
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- * GRADE LEVELS TABLE
CREATE TABLE GradeLevels
(
    id     SERIAL PRIMARY KEY,
    level  SERIAL PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    strand INT,
    -- The length of a name should be at least 3 characters.
    CONSTRAINT name_length CHECK (LENGTH(name) >= 3),
    FOREIGN KEY (strand) REFERENCES Strand (id)
);

-- * Create enum types for each table.
CREATE TYPE Status AS ENUM ('LATE','ON_TIME', 'OUT', 'ABSENT');

-- CREATE A CAST, The CREATE CAST solution does not seem to work when the enum
-- is used as an argument of a JPA Repository. E.g.
-- Entity findByMyEnum(MyEnum myEnum)
CREATE CAST (CHARACTER VARYING as Status) WITH INOUT AS IMPLICIT;

-- * Creates Subjects Table.
CREATE TABLE Subjects
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(128),
    description TEXT
);
-- * Creates Teachers Table.
CREATE TABLE Teachers
(
    id         SERIAL,
    first_name VARCHAR(32),
    last_name  VARCHAR(32),
    sex        VARCHAR(8),
    PRIMARY KEY (id)
);

-- * SECTIONS TABLE
CREATE TABLE Sections
(
    id           SERIAL PRIMARY KEY,
    teacher      INT          NULL,
    room         VARCHAR(255) NOT NULL,
    strand       INT,
    grade_level  INT          NOT NULL,
    section_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (grade_level) REFERENCES GradeLevels (level) ON DELETE SET NULL,
    FOREIGN KEY (teacher) REFERENCES Teachers (id) ON DELETE SET NULL,
    FOREIGN KEY (strand) REFERENCES Strand (id) ON DELETE SET NULL
);

-- * STUDENTS TABLE
CREATE TABLE Students
(
    lrn            BIGINT PRIMARY KEY,
    first_name     VARCHAR(128) NOT NULL,
    middle_initial CHAR(1),
    last_name      VARCHAR(128) NOT NULL,
    grade_level    INT,
    sex            VARCHAR(6),
    section_id     INT,
    address        TEXT,
    birthdate      DATE         NOT NULL,
    FOREIGN KEY (grade_level) REFERENCES GradeLevels (level) ON DELETE SET NULL,
    FOREIGN KEY (section_id) REFERENCES Sections (id) ON DELETE SET NULL
);
CREATE INDEX students_section_id_idx ON Students (section_id);
CREATE INDEX students_grade_level_idx ON Students (grade_level);

-- * RFID CREDENTIALS
CREATE TABLE rfid_credentials
(
    id         SERIAL PRIMARY KEY,
    lrn        BIGINT NOT NULL PRIMARY KEY,
    hashed_lrn CHAR(32),
    salt       VARCHAR(16),
    CHECK (LENGTH(hashed_lrn) = 32),
    CHECK (LENGTH(salt) = 16),
    FOREIGN KEY (lrn) REFERENCES students (lrn) ON DELETE CASCADE
);
CREATE INDEX rfid_credentials_lrn_idx ON rfid_credentials (lrn);

-- * GUARDIANS TABLE
CREATE TABLE Guardians
(
    id             SERIAL PRIMARY KEY,
    student_lrn    BIGINT,
    full_name      VARCHAR(255) NOT NULL,
    contact_number VARCHAR(32)  NULL,
    CHECK (LENGTH(full_name) >= 2),
    FOREIGN KEY (student_lrn) REFERENCES Students (lrn) ON DELETE CASCADE
);
CREATE INDEX guardian_student_id_idx ON Guardians (student_lrn);
CREATE INDEX guardian_full_name_idx ON Guardians (full_name);

-- * ATTENDANCE TABLE
CREATE TABLE Attendance
(
    id         SERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    status     Status,
    date       DATE DEFAULT CURRENT_DATE,
    time       TIME DEFAULT LOCALTIME,
    time_out   TIME DEFAULT LOCALTIME,
    CONSTRAINT fk_student_lrn FOREIGN KEY (student_id) REFERENCES students (lrn) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE INDEX attendance_student_id_idx ON Attendance (student_id);
CREATE INDEX attendance_date_idx on Attendance (date);

-- * MAKE ATTENDANCE ENUM TYPE CHARACTER VARYING
ALTER TABLE Attendance
ALTER COLUMN status TYPE CHARACTER VARYING;

-- * CREATE STUDENT ID INDEX
CREATE INDEX attendance_student_id_idx ON Attendance (student_id);

-- * CREATE USERS TABLE
CREATE TABLE Users
(
    id    SERIAL PRIMARY KEY,
    username  VARCHAR(64),
    password   CHAR(64),
    email      VARCHAR(128),
    role_id    VARCHAR(48),
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK ( LENGTH(username) >= 3 ),
    CHECK ( LENGTH(email) >= 3 )
);

-- CREATE TRIGGER AND NOTIFY --
CREATE OR REPLACE FUNCTION notify_changes_attendance() RETURNS TRIGGER AS
$$
DECLARE
payload VARCHAR;
    channel TEXT := 'attendance_channel';
BEGIN
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
SELECT json_build_object(
               'new', NEW,
               'old', OLD
       )::text
INTO payload;
PERFORM pg_notify(channel, payload);
RETURN NEW;
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER on_event_attendance
    AFTER INSERT OR UPDATE
                        ON Attendance
                        FOR EACH ROW
                        EXECUTE FUNCTION notify_changes_attendance();

-- ==================== SELECT STATEMENTS =================== --

-- show all sections.
SELECT *
FROM sections;

-- show all grade levels.
select level, name
FROM GradeLevels;

-- Select all data from sections.
SELECT *
FROM sections;