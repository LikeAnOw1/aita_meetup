CREATE TABLE IF NOT EXISTS guest
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    first_name      VARCHAR(50)           NOT NULL,
    last_name       VARCHAR(50)           NOT NULL,
    flight_distance int DEFAULT 0,
    flight_hours    int DEFAULT 0
);

CREATE TABLE IF NOT EXISTS boarding_pass
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    guest_id        BIGINT                NOT NULL REFERENCES guest (id) ON DELETE CASCADE,
    full_name       VARCHAR(101)          NOT NULL,
    destination     VARCHAR(300)          NOT NULL,
    arrival_date    TIMESTAMP             NOT NULL,
    invitation_code VARCHAR(256)          NOT NULL,
    checked_in      BOOLEAN   DEFAULT false,
    checked_in_at   TIMESTAMP DEFAULT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS invitation_code_unique_idx ON boarding_pass (invitation_code);
CREATE UNIQUE INDEX IF NOT EXISTS guest_destination_arrival_unique_idx
    ON boarding_pass (guest_id, destination, arrival_date);

INSERT INTO guest (id, first_name, last_name, flight_distance, flight_hours)
VALUES (1, 'Igor', 'Golikov', 1000, 3),
       (2, 'Ivan', 'Petrov', 1500, 5),
       (3, 'Sergey', 'Pronin', 27000, 70),
       (4, 'Fedor', 'Ivanov', 1000, 3),
       (5, 'Marina', 'Pozdniakova', 3900, 10)
ON CONFLICT (id) DO NOTHING;