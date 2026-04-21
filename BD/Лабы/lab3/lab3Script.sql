

-- 1 Денормализация и функция с тригером
-- SELECT animals.name, actions.action FROM actions
-- JOIN animals ON actions.animal_id = animals.id;
--
-- ALTER TABLE actions ADD COLUMN animal_name VARCHAR(50);
--
-- CREATE FUNCTION action_animal_name()
-- RETURNS TRIGGER as $$
-- BEGIN
--     SELECT animals.name INTO new.animal_name
--     FROM animals WHERE animals.id = new.animals_id;
--     RETURN new;
-- end;
-- $$ LANGUAGE plpgsql;
--
-- CREATE TRIGGER triggerAnimAct
--     BEFORE INSERT ON actions
--     FOR EACH ROW
--     EXECUTE FUNCTION action_animal_name();
--
-- INSERT INTO actions (action, animal_id) VALUES ('вести стаю к водопою', 2);
--
-- SELECT action.animal_name, actions.action FROM actions;
--
-- INSERT INTO animals (name, sex, emotion_id, current_location_id) VALUES ('JonnyDep','М',1,1);


CREATE OR REPLACE TRIGGER add_to_flock
    AFTER INSERT ON animals_flock
    FOR EACH ROW
    EXECUTE FUNCTION strongest_flock_after_add_to_flock();


DROP FUNCTION strongest_flock();

CREATE OR REPLACE FUNCTION strongest_flock_after_add_to_flock()
RETURNS TRIGGER AS $$
    DECLARE
        strongest_flock RECORD;
        flocks_rec RECORD;
        animal_ids int[];
        animal_id_1 int;
        BEGIN
            SELECT * INTO strongest_flock FROM strongest_flock();
        RAISE NOTICE 'ID: %',strongest_flock.flock_id;
        RAISE NOTICE 'Name: %' ,strongest_flock.flock_name;
        RAISE NOTICE 'Leader id: %'  ,strongest_flock.leader_id;
        RAISE NOTICE 'Animals power: %' ,strongest_flock.animals_power;
        FOR flocks_rec IN SELECT animals_flock.flock_id,role,animals.current_location_id,animals.id FROM animals_flock
        JOIN animals ON animals_flock.animal_id = animals.id
        WHERE flock_id != strongest_flock.flock_id
        LOOP
            IF flocks_rec.role = 'Вожак' and strongest_flock.leader_loc_id = flocks_rec.current_location_id THEN
                SELECT array_agg(animal_id) INTO animal_ids
                FROM animals_flock
                WHERE flock_id = flocks_rec.flock_id;
                DELETE FROM animals_flock WHERE flock_id = flocks_rec.flock_id;
                DELETE FROM flock WHERE id = flocks_rec.flock_id;
                IF animal_ids IS NOT NULL AND array_length(animal_ids, 1) > 0 THEN
                    FOREACH animal_id_1 IN ARRAY animal_ids
                        LOOP
                            DELETE FROM animals WHERE id = animal_id_1;
                        END LOOP;
                END IF;

            end if;
            end loop;
        RETURN new;
    end;
    $$ LANGUAGE plpgsql;









CREATE OR REPLACE FUNCTION strongest_flock()
    RETURNS TABLE(
                     flock_id int,
                     flock_name varchar(100),
                     leader_id int,
                     animals_power int,
                     leader_loc_id int

    ) as $$
        DECLARE
            animals_in_flock int;
            flock_name varchar(100);
            flock_id int;
            scared_count int;
            leader_id int;
            temp_leader_id int;
            animal_record RECORD;
            flock_record RECORD;
            temp_leader_loc_id int;
            leader_loc_id int;
            animals_power int := 0;
            BEGIN
            FOR flock_record IN SELECT flock.id, flock.name FROM flock
                LOOP
                animals_in_flock:= 0;
                scared_count :=0;
                temp_leader_id := 0;
                FOR animal_record IN SELECT animals.current_location_id, animals.emotion_id, animals_flock.animal_id, animals_flock.role FROM animals_flock
                    JOIN animals ON animals_flock.animal_id = animals.id
                    WHERE animals_flock.flock_id = flock_record.id
                    LOOP
                    IF animal_record.emotion_id = 2 THEN
                        scared_count := scared_count +1;
                    end if;
                    animals_in_flock := animals_in_flock + 1;
                    IF animal_record.role ='Вожак' THEN
                        temp_leader_loc_id := animal_record.current_location_id;

                    end if;
                    end loop;
                IF animals_in_flock - scared_count > animals_power THEN
                    animals_power := animals_in_flock - scared_count;
                    flock_name := flock_record.name;
                    flock_id := flock_record.id;
                    leader_id := temp_leader_id;
                    leader_loc_id := temp_leader_loc_id;
                end if;
                end loop;
        RETURN QUERY SELECT flock_id,flock_name,leader_id, animals_power,leader_loc_id;
            RETURN;
        end;


$$ LANGUAGE plpgsql;



INSERT INTO animals (name, sex, emotion_id, current_location_id) VALUES ('РОМб','М',2,1);
 INSERT INTO animals_flock(flock_id, animal_id, role) VALUES (6,24,'Вожак');
INSERT INTO flock(name) VALUES ('Папины дочки');


SELECT strongest_flock();


DELETE FROM animals_flock WHERE animal_id = 10;

-- удаляет все стаи которые слабже в той же локации




