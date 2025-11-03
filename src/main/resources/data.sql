INSERT IGNORE INTO roles (name) VALUES ('ROLE_USER');
INSERT IGNORE INTO roles (name) VALUES ('ROLE_ADMIN');


INSERT INTO users (username, password, email, full_name, created_at, active)
VALUES ('admin', '$2a$10$/Fgv0c8aS9F2pLzdyxmO5egm451rZwIq5trfKZ9bGqfL6a5TlGIa6',
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
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'
ON DUPLICATE KEY UPDATE user_id = user_id;

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_USER'
ON DUPLICATE KEY UPDATE user_id = user_id;


-- Insertar servicios
INSERT INTO services (name, price, active)
VALUES ('Telefonia', 290.99, true)
ON DUPLICATE KEY UPDATE
    price = VALUES(price),
    active = VALUES(active);

INSERT INTO services (name, price, active)
VALUES ('Internet', 490.99, true)
ON DUPLICATE KEY UPDATE
    price = VALUES(price),
    active = VALUES(active);
