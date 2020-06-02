INSERT INTO passenger (id, first_name, last_name, flight_distance, flight_hours)
VALUES (1, 'Igor', 'Golikov', 1000, 3),
       (2, 'Ivan', 'Petrov', 1500, 5)
ON CONFLICT (id) DO NOTHING;