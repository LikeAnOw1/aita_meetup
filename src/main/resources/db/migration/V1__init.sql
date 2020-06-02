CREATE TABLE IF NOT EXISTS passenger
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    flight_distance int DEFAULT 0,
    flight_hours    int DEFAULT 0
);

CREATE TABLE IF NOT EXISTS boarding_pass
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    passenger_id    BIGINT       NOT NULL REFERENCES passenger (id) ON DELETE CASCADE,
    destination     VARCHAR(300) NOT NULL,
    arrival_date    TIMESTAMP    NOT NULL,
    invitation_code VARCHAR(50)  NOT NULL,
    checked_in      BOOLEAN   DEFAULT false,
    checked_in_at   TIMESTAMP DEFAULT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS invitation_code_unique_idx ON boarding_pass (invitation_code);
