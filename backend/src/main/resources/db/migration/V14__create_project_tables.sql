CREATE TABLE project (
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE zodiac_project (
    zodiac_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    PRIMARY KEY (zodiac_id, project_id),
    FOREIGN KEY (zodiac_id) REFERENCES zodiac(id),
    FOREIGN KEY (project_id) REFERENCES project(id)
);

INSERT INTO project (id, name, description) VALUES
(1, 'Starting a Business', 'A venture that requires courage, planning, and execution. Best for signs that are natural leaders and risk-takers.'),
(2, 'Writing a Book', 'A creative endeavor that requires discipline, imagination, and a unique voice. Best for signs that are introspective and artistic.'),
(3, 'Renovating a Home', 'A practical project that requires attention to detail, patience, and a good sense of aesthetics. Best for signs that are grounded and enjoy creating a comfortable environment.'),
(4, 'Learning a New Skill', 'An intellectual pursuit that requires curiosity, dedication, and a willingness to learn. Best for signs that are adaptable and enjoy mental challenges.'),
(5, 'Volunteering for a Cause', 'A selfless act that requires compassion, empathy, and a desire to make a difference. Best for signs that are idealistic and community-oriented.');

-- Associate all projects with the Dragon for simplicity
INSERT INTO zodiac_project (zodiac_id, project_id) VALUES
((SELECT id FROM zodiac WHERE name = 'Dragon'), 1),
((SELECT id FROM zodiac WHERE name = 'Dragon'), 2),
((SELECT id FROM zodiac WHERE name = 'Dragon'), 3),
((SELECT id FROM zodiac WHERE name = 'Dragon'), 4),
((SELECT id FROM zodiac WHERE name = 'Dragon'), 5);

-- Associate some projects with the Monkey
INSERT INTO zodiac_project (zodiac_id, project_id) VALUES
((SELECT id FROM zodiac WHERE name = 'Monkey'), 1),
((SELECT id FROM zodiac WHERE name = 'Monkey'), 4);
