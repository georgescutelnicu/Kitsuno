ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

INSERT INTO users (username, email, password, enabled, join_date, api_key)
VALUES
    ('user1', 'user1@example.com', 'hashedpassword1', true, CURRENT_DATE, 'apikey1'),
    ('user2', 'user2@example.com', 'hashedpassword2', true, CURRENT_DATE, 'apikey2');
