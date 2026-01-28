package database;

import database.jdbc.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtils {
    public static void createData() {
        try {
            executeUpdate("""
                    CREATE TABLE places(
                    \tid int4 NOT NULL,
                    \t"row" int2 NULL,
                    \tplace_num int4 NULL,
                    \t"name" varchar NULL
                    );""");
            executeUpdate("ALTER TABLE places ADD CONSTRAINT places_pk PRIMARY KEY (id);");
            executeUpdate("""
                    CREATE TABLE sex(
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tCONSTRAINT sex_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE "types"(
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tCONSTRAINT types_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE animal(
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tage int4 NULL,
                    \t"type" int4 NULL,
                    \tsex int4 NULL,
                    \tplace int4 NULL,
                    \tCONSTRAINT animal_pk PRIMARY KEY (id),
                    \tCONSTRAINT animal_fk FOREIGN KEY ("type") REFERENCES "types"(id),
                    \tCONSTRAINT animal_fk_1 FOREIGN KEY (sex) REFERENCES sex(id),
                    \tCONSTRAINT animal_fk_2 FOREIGN KEY (place) REFERENCES places(id) DEFERRABLE
                    );""");
            executeUpdate("""
                    CREATE TABLE positions(
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tsalary int4 NULL,
                    \tCONSTRAINT positions_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE workman (
                    \tid int4 NOT NULL,
                    \t"name" varchar NOT NULL,
                    \tage int4 NULL,
                    \t"position" int4 NULL,
                    \tCONSTRAINT workman_pk PRIMARY KEY (id),
                    \tCONSTRAINT workman_fk FOREIGN KEY ("position") REFERENCES positions(id)
                    );""");
            executeUpdate("""
                    CREATE TABLE zoo (
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tCONSTRAINT zoo_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE zoo_animal (
                    \tzoo_id int4 NOT NULL,
                    \tanimal_id int4 NOT NULL,
                    \ttime_apperance timestamp(0) NULL,
                    \tworkman int4 NULL,
                    \tCONSTRAINT zoo_animal_fk FOREIGN KEY (zoo_id) REFERENCES zoo(id),
                    \tCONSTRAINT zoo_animal_fk_1 FOREIGN KEY (animal_id) REFERENCES animal(id),
                    \tCONSTRAINT zoo_animal_fk_2 FOREIGN KEY (workman) REFERENCES workman(id)
                    );""");
            executeUpdate("""
                    INSERT INTO places (id, "row", place_num, "name") VALUES(1, 1, 185, 'Загон 1');
                    INSERT INTO places (id, "row", place_num, "name") VALUES(2, 2, 245, 'Загон 2');
                    INSERT INTO places (id, "row", place_num, "name") VALUES(3, 3, 123, 'Загон 3');
                    INSERT INTO places (id, "row", place_num, "name") VALUES(4, 5, 054, 'Загон 4');
                    INSERT INTO places (id, "row", place_num, "name") VALUES(5, 6, 043, 'Загон 5');""");
            executeUpdate("INSERT INTO sex (id, \"name\") VALUES(1, 'Мужской');\n"
                    + "INSERT INTO sex (id, \"name\") VALUES(2, 'Женский');");
            executeUpdate("""
                    INSERT INTO "types" (id, "name") VALUES(1, 'Кошка');
                    INSERT INTO "types" (id, "name") VALUES(2, 'Собака');
                    INSERT INTO "types" (id, "name") VALUES(3, 'Примат');
                    INSERT INTO "types" (id, "name") VALUES(4, 'Птица');
                    INSERT INTO "types" (id, "name") VALUES(5, 'Рыба');""");
            executeUpdate("""
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(1, 'Бусинка', 2, 1, 1, 1);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(2, 'Пчелка', 4, 2, 1, 1);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(3, 'Иваныч', 5, 2, 1, 2);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(4, 'По', 6, 3, 2, 2);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(5, 'Абрикос', 7, 4, 2,3);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(6, 'Кекс', 3, 5, 2, 4);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(7, 'Плюша', 5, 3, 1, 5);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(8, 'Жулик', 7, 2, 1, 3);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(9, 'Пончик', 4, 1, 1, 2);
                    INSERT INTO animal (id, "name", age, "type", sex, place) VALUES(10, 'Котлета', 5, 2, 1, 4);""");
            executeUpdate("""
                    INSERT INTO positions (id, "name", salary) VALUES(1, 'Старший дворник', 25000);
                    INSERT INTO positions (id, "name", salary) VALUES(2, 'Дворник', 20000);
                    INSERT INTO positions (id, "name", salary) VALUES(3, 'Младший дворник', 15000);
                    INSERT INTO positions (id, "name", salary) VALUES(4, 'Кормилец', 45000);""");
            executeUpdate("""
                    INSERT INTO workman (id, "name", age, "position") VALUES(1, 'Петя', 23, 1);
                    INSERT INTO workman (id, "name", age, "position") VALUES(2, 'Вася', 34, 2);
                    INSERT INTO workman (id, "name", age, "position") VALUES(3, 'Коля', 24, 3);
                    INSERT INTO workman (id, "name", age, "position") VALUES(4, 'Александр',22, 4);
                    INSERT INTO workman (id, "name", age, "position") VALUES(5, 'Витя', 32, 3);
                    INSERT INTO workman (id, "name", age, "position") VALUES(6, 'Иван', 54, 2);""");
            executeUpdate("""
                    INSERT INTO zoo (id, "name") VALUES(1, 'Центральный');
                    INSERT INTO zoo (id, "name") VALUES(2, 'Северный');
                    INSERT INTO zoo (id, "name") VALUES(3, 'Западный');""");
            executeUpdate("""
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(1, 1, null, 1);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(2, 2, null, 2);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(3, 3, null, 3);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(3, 4, null, 4);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(2, 5, null, 5);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(2, 6, null, 6);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(3, 7, null, 1);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(1, 8, null, 2);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(1, 9, null, 3);
                    INSERT INTO zoo_animal (zoo_id, animal_id, time_apperance, workman) VALUES(2, 10, null, 4);""");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        Connection connection = DatabaseConnection.createConnection();
        connection
                .createStatement()
                .executeUpdate(sql);
    }
}
