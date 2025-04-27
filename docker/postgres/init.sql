CREATE SCHEMA IF NOT EXISTS streamtest AUTHORIZATION postgres;

SET search_path TO streamtest;

CREATE TABLE IF NOT EXISTS roles (
    role_id varchar(255) NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (role_id),
    CONSTRAINT roles_name_key UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS users (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    username varchar(50) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_username_key UNIQUE (username)
);

INSERT INTO roles (role_id, name) 
VALUES 
  ('4f8ba3ad-934d-4af8-9fc9-e48a74b1253c', 'ADMIN'),
  ('4d57aa8e-61d4-401c-8578-e6422281788b', 'USER');

INSERT INTO users (id, username, password, role) 
VALUES 
  ('a1b2c3d4-e5f6-4321-8765-1a2b3c4d5e6f', 'admin', '$2a$10$uEWdxWC0CVD8.upgw0ojOOI6/lmbFSh60LS3ZM5OYBRz1uxrD4gk2', '4f8ba3ad-934d-4af8-9fc9-e48a74b1253c'),
  ('b2c3d4e5-f6a1-5432-8765-2b3c4d5e6f1a', 'user', '$2a$10$uEWdxWC0CVD8.upgw0ojOOI6/lmbFSh60LS3ZM5OYBRz1uxrD4gk2', '4d57aa8e-61d4-401c-8578-e6422281788b');