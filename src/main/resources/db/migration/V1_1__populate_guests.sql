INSERT INTO guest (id, first_name, last_name, flight_distance, flight_hours)
VALUES (1, 'Igor', 'Golikov', 1000, 3),
       (2, 'Ivan', 'Petrov', 1500, 5),
       (3, 'Sergey', 'Pronin', 27000, 70),
       (4, 'Fedor', 'Ivanov', 1000, 3),
       (5, 'Marina', 'Pozdniakova', 3900, 10)
ON CONFLICT (id) DO NOTHING;