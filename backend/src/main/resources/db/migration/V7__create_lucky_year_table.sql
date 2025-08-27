CREATE TABLE lucky_year (
    id BIGINT PRIMARY KEY,
    zodiac_id BIGINT NOT NULL,
    lucky_year INT NOT NULL,
    is_lucky BOOLEAN NOT NULL,
    description TEXT,
    FOREIGN KEY (zodiac_id) REFERENCES zodiac(id)
);
