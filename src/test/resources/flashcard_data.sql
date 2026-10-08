ALTER TABLE kanji ALTER COLUMN id RESTART WITH 1;

INSERT INTO kanji (character, stroke_count, grade, meanings, onyomi_readings, kunyomi_readings, stroke_order_svg, vocab)
VALUES
('日', 4, 1, 'day, sun, Japan, counter for days', ARRAY['ニチ','ジツ'], ARRAY['ひ','-び','-か'], '<svg>...</svg>',
   ARRAY['毎日 【まいにち】every day','日光 【にっこう】sunlight','翌日 【よくじつ】next day']),
('月', 4, 1, 'month, moon', ARRAY['ゲツ','ガツ'], ARRAY['つき'], '<svg>...</svg>',
   ARRAY['月曜 【げつよう】Monday','来月 【らいげつ】next month','満月 【まんげつ】full moon']),
('人', 2, 2, 'person', ARRAY['ジン','ニン'], ARRAY['ひと'], '<svg>...</svg>',
   ARRAY['友人 【ゆうじん】friend','人 【ひと】man, person, people']);

ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

INSERT INTO users (username, email, password, enabled, join_date)
VALUES
    ('user1', 'user1@example.com', 'hashedpassword1', true, CURRENT_DATE),
    ('user2', 'user2@example.com', 'hashedpassword2', true, CURRENT_DATE);

ALTER TABLE flashcard ALTER COLUMN id RESTART WITH 1;

INSERT INTO flashcard (vocabulary, notes, kanji_id, user_id)
VALUES
(ARRAY['ひあさ morning sun'], 'Flashcard note for 日', 1, 1),
(ARRAY['ひとびと people'], 'Flashcard note for 人', 2, 1),
(ARRAY['ひさし long time'], 'Flashcard note for 日', 1, 2);