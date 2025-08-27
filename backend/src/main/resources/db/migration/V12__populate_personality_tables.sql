-- Personalities
INSERT INTO personality (id, name, description) VALUES (1, 'Quick-witted', 'Rats are quick-witted, resourceful, and smart but lack courage.');
INSERT INTO personality (id, name, description) VALUES (2, 'Diligent', 'Oxen are known for diligence, dependability, strength and determination.');
INSERT INTO personality (id, name, description) VALUES (3, 'Brave', 'Tigers are courageous and active people who love a good challenge and adventure in life.');
INSERT INTO personality (id, name, description) VALUES (4, 'Gentle', 'Rabbits tend to be gentle, quiet, elegant, and alert as well as quick, skillful, kind, patient, and very responsible.');
INSERT INTO personality (id, name, description) VALUES (5, 'Confident', 'Dragons are strong and independent figures, but they yearn for support and love.');
INSERT INTO personality (id, name, description) VALUES (6, 'Enigmatic', 'Snakes are enigmatic, intuitive, introspective, refined and collected.');
INSERT INTO personality (id, name, description) VALUES (7, 'Animated', 'Horses are active, energetic and need to be the center of attention.');
INSERT INTO personality (id, name, description) VALUES (8, 'Calm', 'Goats are gentle, mild-mannered, stable, sympathetic, amicable, and brimming with a strong sense of kindheartedness and justice.');
INSERT INTO personality (id, name, description) VALUES (9, 'Sharp', 'Monkeys are sharp, smart, curious, and mischievous.');
INSERT INTO personality (id, name, description) VALUES (10, 'Observant', 'Roosters are very observant. They are hardworking, resourceful, courageous, and talented.');
INSERT INTO personality (id, name, description) VALUES (11, 'Lovely', 'Dogs are loyal, honest, and just.');
INSERT INTO personality (id, name, description) VALUES (12, 'Compassionate', 'Pigs are diligent, compassionate, and generous.');

-- Zodiac-Personality relationships
-- Rat
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (1, 1, 1);
-- Ox
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (2, 2, 2);
-- Tiger
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (3, 3, 3);
-- Rabbit
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (4, 4, 4);
-- Dragon
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (5, 5, 5);
-- Snake
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (6, 6, 6);
-- Horse
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (7, 7, 7);
-- Goat
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (8, 8, 8);
-- Monkey
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (9, 9, 9);
-- Rooster
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (10, 10, 10);
-- Dog
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (11, 11, 11);
-- Pig
INSERT INTO zodiac_personality (id, zodiac_id, personality_id) VALUES (12, 12, 12);
