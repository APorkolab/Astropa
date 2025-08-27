CREATE TABLE life_stage (
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE zodiac_life_stage (
    zodiac_id BIGINT NOT NULL,
    life_stage_id BIGINT NOT NULL,
    PRIMARY KEY (zodiac_id, life_stage_id),
    FOREIGN KEY (zodiac_id) REFERENCES zodiac(id),
    FOREIGN KEY (life_stage_id) REFERENCES life_stage(id)
);

INSERT INTO life_stage (id, name, description) VALUES
(1, 'Childhood (Ages 0-12)', 'A time of innocence, learning, and growth. Personality traits are developing.'),
(2, 'Youth (Ages 13-24)', 'A period of self-discovery, rebellion, and finding one''s place in the world.'),
(3, 'Adulthood (Ages 25-40)', 'A time for building a career, starting a family, and taking on responsibilities.'),
(4, 'Midlife (Ages 41-60)', 'A period of reflection, consolidation, and preparing for the next chapter.'),
(5, 'Old Age (Ages 60+)', 'A time of wisdom, legacy, and enjoying the fruits of one''s labor.');

-- Associate all life stages with the Rat for simplicity
INSERT INTO zodiac_life_stage (zodiac_id, life_stage_id) VALUES
((SELECT id FROM zodiac WHERE name = 'Rat'), 1),
((SELECT id FROM zodiac WHERE name = 'Rat'), 2),
((SELECT id FROM zodiac WHERE name = 'Rat'), 3),
((SELECT id FROM zodiac WHERE name = 'Rat'), 4),
((SELECT id FROM zodiac WHERE name = 'Rat'), 5);

-- Associate some life stages with the Ox
INSERT INTO zodiac_life_stage (zodiac_id, life_stage_id) VALUES
((SELECT id FROM zodiac WHERE name = 'Ox'), 1),
((SELECT id FROM zodiac WHERE name = 'Ox'), 3);
