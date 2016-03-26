CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR (128),
    middle_name VARCHAR (128),
    last_name VARCHAR (128),
    birth_date date,
    creation_time TIMESTAMP NOT NULL
);