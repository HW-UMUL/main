INSERT INTO role(role) VALUES('ROLE_USER');
INSERT INTO role(role) VALUES('ROLE_MODERATOR');
INSERT INTO role(role) VALUES('ROLE_ADMIN');

SELECT * FROM post;
SELECT * FROM post WHERE title LIKE '%확인%';

SELECT * FROM wiki;
SELECT * FROM role;
SELECT * FROM user;
INSERT INTO user VALUES(1, 'gye@naver.com', '123', 'gye');

SELECT * FROM search_log;
SELECT content, user_id, MAX(date) AS max_date FROM search_log WHERE user_id = 2 GROUP BY content ORDER BY MAX(date) DESC;
SELECT * FROM search_log WHERE user_id = 2 GROUP BY content ORDER BY MAX(date) DESC;
INSERT INTO search_log (content, DATE, user_id) VALUES('이건 2번입니다', NOW(), '2');
SELECT * FROM wiki_log;
SELECT * FROM user;

DELETE FROM search_log WHERE search_log_id = 26 AND  user_id = 2;