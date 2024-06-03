-- Crear la secuencia para la tabla users
CREATE SEQUENCE user_seq
START WITH 1
INCREMENT BY 1;

-- Crear la tabla users
CREATE TABLE users (
    id NUMBER PRIMARY KEY,
    username VARCHAR2(50) NOT NULL UNIQUE,
    password VARCHAR2(100) NOT NULL
);

-- Crear el trigger para la secuencia en la tabla users
CREATE OR REPLACE TRIGGER user_seq_trigger
BEFORE INSERT ON users
FOR EACH ROW
WHEN (NEW.id IS NULL)
BEGIN
    SELECT user_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

-- Crear la secuencia para la tabla roles
CREATE SEQUENCE role_seq
START WITH 1
INCREMENT BY 1;

-- Crear la tabla roles
CREATE TABLE roles (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL UNIQUE
);

-- Crear el trigger para la secuencia en la tabla roles
CREATE OR REPLACE TRIGGER role_seq_trigger
BEFORE INSERT ON roles
FOR EACH ROW
WHEN (NEW.id IS NULL)
BEGIN
    SELECT role_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
/

-- Crear la tabla intermedia users_roles
CREATE TABLE users_roles (
    user_id NUMBER,
    role_id NUMBER,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Insertar rol USER
INSERT INTO roles (name) VALUES ('USER');

-- Insertar rol ADMIN
INSERT INTO roles (name) VALUES ('ADMIN');

-- Verificar los roles insertados
SELECT * FROM roles;

-- Crear los usuarios
INSERT INTO users (username, password) VALUES ('user', 'password1');
INSERT INTO users (username, password) VALUES ('admin', 'password2');

-- Verificar los usuarios insertados
SELECT * FROM users;

-- Asignar roles a los usuarios
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);

-- Verificar los usuarios insertados con su rol
SELECT * FROM users_roles;

COMMIT;