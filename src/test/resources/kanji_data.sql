ALTER TABLE kanji ALTER COLUMN id RESTART WITH 1;

INSERT INTO kanji (character, stroke_count, grade, meanings, onyomi_readings, kunyomi_readings, stroke_order_svg, vocab)
VALUES
('日', 4, 1, 'day, sun, Japan, counter for days', ARRAY['ニチ','ジツ'], ARRAY['ひ','-び','-か'], '<svg>...</svg>',
   ARRAY['毎日 【まいにち】every day','日光 【にっこう】sunlight','翌日 【よくじつ】next day']),
('月', 4, 1, 'month, moon', ARRAY['ゲツ','ガツ'], ARRAY['つき'], '<svg>...</svg>',
   ARRAY['月曜 【げつよう】Monday','来月 【らいげつ】next month','満月 【まんげつ】full moon']),
('人', 2, 2, 'person', ARRAY['ジン','ニン'], ARRAY['ひと'], '<svg>...</svg>',
   ARRAY['友人 【ゆうじん】friend','人 【ひと】man, person, people']);