
DROP TABLE IF EXISTS sound CASCADE;
DROP TABLE IF EXISTS emotions CASCADE;
DROP TABLE IF EXISTS actions CASCADE;
DROP TABLE IF EXISTS flock CASCADE;
DROP TABLE IF EXISTS stuff CASCADE;
DROP TABLE IF EXISTS animals CASCADE;
DROP TABLE IF EXISTS time_of_day CASCADE;
DROP TABLE IF EXISTS illuminations CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS animals_flock CASCADE;
DROP TYPE IF EXISTS object_type CASCADE;
DROP TYPE IF EXISTS sex CASCADE;
DROP TYPE IF EXISTS action_enum CASCADE;
DROP TYPE IF EXISTS role;


CREATE TYPE sex AS ENUM ('М', 'Ж');
CREATE TYPE action_enum AS ENUM ('вести стаю к водопою', 'курить сижки');
CREATE TYPE role AS ENUM ('Вожак', 'Не вожак');

CREATE TABLE locations (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100),
                           coordinates POINT
);

CREATE TABLE illuminations (
                               id SERIAL PRIMARY KEY,
                               type VARCHAR(50),
                               illumination_power INTEGER,
                               source VARCHAR(50)
);


CREATE TABLE time_of_day (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(50),
                             illumination_id INTEGER REFERENCES illuminations(id),
                             start_time TIME NOT NULL,
                             end_time TIME NOT NULL
);

CREATE TABLE emotions (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(50),
                          spawn_time_id INTEGER REFERENCES time_of_day(id) NOT NULL
);

CREATE TABLE animals (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(50),
                         sex sex NOT NULL ,
                         emotion_id INTEGER REFERENCES emotions(id),
                         current_location_id INTEGER REFERENCES locations(id) NOT NULL

);

CREATE TABLE stuff (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100),
                       property VARCHAR(50) NOT NULL,
                       material VARCHAR(50),
                       current_location_id INTEGER REFERENCES locations(id) NOT NULL
);


CREATE TABLE flock (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50)
);

CREATE TABLE animals_flock(
                              id SERIAL PRIMARY KEY,
                              flock_id INTEGER REFERENCES flock(id) NOT NULL,
                              animal_id INTEGER REFERENCES animals(id) NOT NULL ,
                              role role NOT NULL
);

CREATE TABLE actions (
                         id SERIAL PRIMARY KEY,
                         action_val action_enum NOT NULL ,
                         stuff_id INTEGER REFERENCES stuff(id),
                         subject_id INTEGER REFERENCES animals(id) NOT NULL
);

CREATE TABLE sound (
                       id SERIAL PRIMARY KEY,
                       property VARCHAR(50),
                       source VARCHAR(50),
                       spawn_time_id INTEGER REFERENCES time_of_day(id) NOT NULL
);


INSERT INTO locations (name, coordinates) VALUES ('Водопой', POINT(0,0));
INSERT INTO illuminations (type, illumination_power, source) VALUES ('слабый', 10, 'заря');
INSERT INTO time_of_day (name, illumination_id, start_time, end_time) VALUES ('нарождающийся день', 1, '20:00','22:00');
INSERT INTO emotions (name, spawn_time_id) VALUES ('спокойствие', 1);




INSERT INTO animals (name, sex, emotion_id,current_location_id) VALUES ('Смотрящий', 'М', 1,1);
INSERT INTO flock(name) VALUES ('Воронины');
INSERT INTO animals_flock(flock_id, animal_id, role) VALUES (1,1,'Вожак');


INSERT INTO stuff (name, property, material, current_location_id)
VALUES ('Новый Камень', 'странный/необычайный', 'камень', 1);


INSERT INTO actions (action_val, stuff_id, subject_id) VALUES ('вести стаю к водопою', 1, 1);



INSERT INTO sound (property, source, spawn_time_id) VALUES ('необычайный', 'собака', 1);




