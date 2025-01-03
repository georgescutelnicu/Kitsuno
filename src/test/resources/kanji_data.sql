ALTER TABLE kanji ALTER COLUMN id RESTART WITH 1;

INSERT INTO kanji (character, jlpt, meanings, onyomi_readings, kunyomi_readings, onyomi_vocab, kunyomi_vocab,
                    category, stroke_count)
VALUES
  ('日', 'N5', 'day, sun, Japan, counter for days', '["ニチ", "ジツ"]', '["ひ", "-び", "-か"]',
   '["毎日 【まいにち】every day", "日光 【にっこう】sunlight", "翌日 【よくじつ】next day"]',
   '["日 【ひ】day, days", "記念日 【きねんび】anniversary, memorial day"]', 'Time', 4),
  ('月', 'N5', 'month, moon', '["ゲツ", "ガツ"]', '["つき"]',
   '["月曜 【げつよう】 Monday", "来月 【らいげつ】 next month", "満月 【まんげつ】 full moon"]',
   '["一月 【ひとつき】 one month", "毎月 【まいつき】 every month"]', 'Time', 4),
  ('人', 'N5', 'person', '["ジン", "ニン"]', '["ひと"]',
   '["~人 【じん】 often used for citizenship after a country (American, Chinese, etc..)", "友人 【ゆうじん】friend"]',
   '["人 【ひと】 man, person, people", "いい人 【いいひと】 good-natured person, good person, lover"]', 'People', 2);
