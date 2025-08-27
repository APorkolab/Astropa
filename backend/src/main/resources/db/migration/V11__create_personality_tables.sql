CREATE TABLE personality (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE zodiac_personality (
    id BIGINT PRIMARY KEY,
    zodiac_id BIGINT NOT NULL,
    personality_id BIGINT NOT NULL,
    FOREIGN KEY (zodiac_id) REFERENCES zodiac(id),
    FOREIGN KEY (personality_id) REFERENCES personality(id)
);
