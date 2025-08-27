CREATE TABLE compatibility (
    id BIGINT PRIMARY KEY,
    zodiac1_id BIGINT NOT NULL,
    zodiac2_id BIGINT NOT NULL,
    compatibility_type VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY (zodiac1_id) REFERENCES zodiac(id),
    FOREIGN KEY (zodiac2_id) REFERENCES zodiac(id)
);
