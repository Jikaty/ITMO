
CREATE TYPE object_type AS ENUM ('animal', 'stuff');
CREATE TYPE sex AS ENUM ('М', 'Ж');
CREATE TYPE action_enum AS ENUM ('вести стаю к водопою', 'курить сижки');


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

CREATE TABLE objects (
                         id SERIAL PRIMARY KEY,
                         type object_type NOT NULL
);


CREATE TABLE time_of_day (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(50),
                             illumination_id INTEGER REFERENCES illuminations(id)
);

CREATE TABLE animals (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(50),
                         sex sex NOT NULL ,
                         emotion_id INTEGER REFERENCES emotions(id),
                         current_location_id INTEGER REFERENCES locations(id) NOT NULL ,
                         object_id INTEGER REFERENCES objects(id) NOT NULL
);

CREATE TABLE stuff (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100),
                       property VARCHAR(50) NOT NULL,
                       material VARCHAR(50),
                       current_location_id INTEGER REFERENCES locations(id) NOT NULL ,
                       object_id INTEGER REFERENCES objects(id) NOT NULL
);


CREATE TABLE flock (
                       id SERIAL PRIMARY KEY,
                       quantity INTEGER,
                       leader_id INTEGER REFERENCES animals(id) NOT NULL
);

CREATE TABLE actions (
                         id SERIAL PRIMARY KEY,
                         action_val action_enum NOT NULL ,
                         stuff_id INTEGER REFERENCES stuff(id),
                         subject_id INTEGER REFERENCES animals(id) NOT NULL
);

CREATE TABLE emotions (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(50),
                          subject_id INTEGER REFERENCES animals(id) NOT NULL ,
                          spawn_time_id INTEGER REFERENCES time_of_day(id) NOT NULL
);

CREATE TABLE sound (
                       id SERIAL PRIMARY KEY,
                       property VARCHAR(50),
                       source VARCHAR(50),
                       spawn_time_id INTEGER REFERENCES time_of_day(id) NOT NULL
);


INSERT INTO locations (name, coordinates) VALUES ('Водопой', POINT(0,0));
INSERT INTO illuminations (type, illumination_power, source) VALUES ('слабый', 10, 'заря');
INSERT INTO time_of_day (name, illumination_id) VALUES ('нарождающийся день', 1);


INSERT INTO objects (type) VALUES ('animal');
INSERT INTO animals (name, sex, current_location_id, object_id) VALUES ('Смотрящий', 'М', 1, 1);


INSERT INTO objects (type) VALUES ('stuff');
INSERT INTO stuff (name, property, material, current_location_id, object_id)
VALUES ('Новый Камень', 'странный/необычайный', 'камень', 1, 2);


INSERT INTO actions (action_val, stuff_id, subject_id) VALUES ('вести стаю к водопою', 1, 1);
INSERT INTO emotions (name, subject_id, spawn_time_id) VALUES ('спокойствие', 1, 1);


INSERT INTO sound (property, source, spawn_time_id) VALUES ('необычайный', 'собака', 1);
COMMIT;



