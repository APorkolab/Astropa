CREATE TABLE career (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE zodiac_career (
    id BIGINT PRIMARY KEY,
    zodiac_id BIGINT NOT NULL,
    career_id BIGINT NOT NULL,
    FOREIGN KEY (zodiac_id) REFERENCES zodiac(id),
    FOREIGN KEY (career_id) REFERENCES career(id)
);
