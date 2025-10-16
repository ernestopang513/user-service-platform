INSERT IGNORE INTO roles (name) VALUES ('ROLE_USER');
INSERT IGNORE INTO roles (name) VALUES ('ROLE_ADMIN');


INSERT INTO users (username, password, email, full_name, created_at, active)
VALUES ('admin', '$2a$10$eB6GqfWlS9wH0x0rVuvM/.R9vDqwVQJH0yz.ZxvF2ka.YiA7J0b1y',
        'admin@example.com', 'Administrador', NOW(), true)
ON DUPLICATE KEY UPDATE
    password = VALUES(password),
    email = VALUES(email),
    full_name = VALUES(full_name),
    active = VALUES(active),
    created_at = VALUES(created_at);

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_USER';
