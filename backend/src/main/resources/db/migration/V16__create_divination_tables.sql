CREATE TABLE divination (
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE zodiac_divination (
    zodiac_id BIGINT NOT NULL,
    divination_id BIGINT NOT NULL,
    PRIMARY KEY (zodiac_id, divination_id),
    FOREIGN KEY (zodiac_id) REFERENCES zodiac(id),
    FOREIGN KEY (divination_id) REFERENCES divination(id)
);

INSERT INTO divination (id, name, description) VALUES
(1, 'Tarot Reading', 'A powerful tool for introspection and guidance. Best for signs that are intuitive and open to symbolic interpretation.'),
(2, 'Vedic Astrology', 'An ancient and complex system of astrology that provides deep insights into one''s life path. Best for signs that are spiritual and seek a deeper understanding of the cosmos.'),
(3, 'I Ching', 'An ancient Chinese divination text that provides guidance on how to act in accordance with the flow of the universe. Best for signs that are philosophical and seek harmony.'),
(4, 'Palmistry', 'The art of reading palms to understand one''s character and destiny. Best for signs that are observant and believe in the connection between the physical and the spiritual.'),
(5, 'Numerology', 'The study of the mystical relationship between numbers and life events. Best for signs that are analytical and see patterns in the world.');

-- Associate all divination techniques with the Pig for simplicity
INSERT INTO zodiac_divination (zodiac_id, divination_id) VALUES
((SELECT id FROM zodiac WHERE name = 'Pig'), 1),
((SELECT id FROM zodiac WHERE name = 'Pig'), 2),
((SELECT id FROM zodiac WHERE name = 'Pig'), 3),
((SELECT id FROM zodiac WHERE name = 'Pig'), 4),
((SELECT id FROM zodiac WHERE name = 'Pig'), 5);

-- Associate some divination techniques with the Goat
INSERT INTO zodiac_divination (zodiac_id, divination_id) VALUES
((SELECT id FROM zodiac WHERE name = 'Goat'), 1),
((SELECT id FROM zodiac WHERE name = 'Goat'), 5);
