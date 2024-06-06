INSERT INTO users (username,password) VALUES ('user2','password');
SELECT * FROM users;
INSERT INTO users_roles (user_id, role_id) VALUES (21, 2);
SELECT * FROM users_roles;
COMMIT;
SELECT * FROM roles;

UPDATE roles
SET name = 'ROLE_USER'
WHERE id = 1;

UPDATE roles
SET name = 'ROLE_ADMIN'
WHERE id = 2;

COMMIT;