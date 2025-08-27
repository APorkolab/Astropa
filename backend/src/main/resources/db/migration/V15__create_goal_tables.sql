CREATE TABLE goal (
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE zodiac_goal (
    zodiac_id BIGINT NOT NULL,
    goal_id BIGINT NOT NULL,
    PRIMARY KEY (zodiac_id, goal_id),
    FOREIGN KEY (zodiac_id) REFERENCES zodiac(id),
    FOREIGN KEY (goal_id) REFERENCES goal(id)
);

INSERT INTO goal (id, name, description) VALUES
(1, 'Achieve Financial Independence', 'For those who value security and freedom. Best for signs that are diligent, practical, and good with money.'),
(2, 'Find True Love', 'A deep desire for a meaningful connection. Best for signs that are romantic, loyal, and emotionally intelligent.'),
(3, 'Travel the World', 'A thirst for adventure and new experiences. Best for signs that are curious, adaptable, and open-minded.'),
(4, 'Make a Difference', 'A need to contribute to something bigger than oneself. Best for signs that are compassionate, idealistic, and have a strong sense of justice.'),
(5, 'Master a Creative Art', 'A passion for self-expression and creativity. Best for signs that are artistic, imaginative, and patient.');

-- Associate all goals with the Rooster for simplicity
INSERT INTO zodiac_goal (zodiac_id, goal_id) VALUES
((SELECT id FROM zodiac WHERE name = 'Rooster'), 1),
((SELECT id FROM zodiac WHERE name = 'Rooster'), 2),
((SELECT id FROM zodiac WHERE name = 'Rooster'), 3),
((SELECT id FROM zodiac WHERE name = 'Rooster'), 4),
((SELECT id FROM zodiac WHERE name = 'Rooster'), 5);

-- Associate some goals with the Dog
INSERT INTO zodiac_goal (zodiac_id, goal_id) VALUES
((SELECT id FROM zodiac WHERE name = 'Dog'), 2),
((SELECT id FROM zodiac WHERE name = 'Dog'), 4);
