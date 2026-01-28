package database;


import database.jdbc.DatabaseConnection;
import database.jdbc.models.Animal;
import database.jdbc.models.Place;
import database.jdbc.models.Workman;
import database.jdbc.models.Zoo;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {

    // ==================== ANIMAL CRUD ====================

    /**
     * Create - Insert new animal
     */
    @SneakyThrows
    public static void createAnimal(Animal animal) {
        String sql = "INSERT INTO animal (id, \"name\", age, \"type\", sex, place) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, animal.getId());
        ps.setString(2, animal.getName());
        ps.setInt(3, animal.getAge());
        ps.setInt(4, animal.getType());
        ps.setInt(5, animal.getSex());
        ps.setInt(6, animal.getPlace());
        ps.executeUpdate(); //executeUpdate() - для INSERT, UPDATE, DELETE (изменяют данные)
    }

    /**
     * Read - Get animal by ID
     */
    @SneakyThrows
    public static Animal getAnimalById(int id) {
        String sql = "SELECT * FROM animal WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery(); //executeQuery() - для SELECT (читают данные)

        if (rs.next()) {
            /*
            rs.next() - метод ResultSet
            → Перемещает курсор на следующую строку (начальная позиция - курсор над строкой)
            → Возвращает true, если строка есть - возвращаем найденное животное
            → Возвращает false, если строк больше нет - животное не найдено, вернём null
             */
            return Animal.builder() //Начинает построение объекта Animal через Builder pattern
                    .id(rs.getInt("id")) //- читает значение из ResultSet
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .type(rs.getInt("type"))
                    .sex(rs.getInt("sex"))
                    .place(rs.getInt("place"))
                    .build();
        }
        return null;
    }

    /**
     * Read - Get all animals
     */
    @SneakyThrows
    public static List<Animal> getAllAnimals() {
        return getAnimalData("SELECT * FROM animal");
    }

    /**
     * Read - Get animals by custom query
     */
    @SneakyThrows
    public static List<Animal> getAnimalData(String query) {
        List<Animal> animals = new ArrayList<>();
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {  // - цикл, выполняется пока условие true
            animals.add(Animal.builder() //animals.add(...) - читает по очереди id, name и тд и добавляет животное в список
                    .id(rs.getInt("id")) //- читает значение из ResultSet
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .type(rs.getInt("type"))
                    .sex(rs.getInt("sex"))
                    .place(rs.getInt("place"))
                    .build());
        }
        return animals;
    }

    /**
     * Update - Update existing animal
     */
    @SneakyThrows
    public static void updateAnimal(Animal animal) {
        String sql = "UPDATE animal SET \"name\" = ?, age = ?, \"type\" = ?, sex = ?, place = ? WHERE id  = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, animal.getName());
        ps.setInt(2, animal.getAge());
        ps.setInt(3, animal.getType());
        ps.setInt(4, animal.getSex());
        ps.setInt(5, animal.getPlace());
        ps.setInt(6, animal.getId());
        ps.executeUpdate(); //executeUpdate() - для INSERT, UPDATE, DELETE (изменяют данные)
    }

    /**
     * Delete - Delete animal by ID
     */
    @SneakyThrows
    public static void deleteAnimal(int id) {
        String sql = "DELETE FROM animal WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * Count animals
     */
    @SneakyThrows
    public static int getAnimalCount() {
        String sql = "SELECT COUNT (*) as total FROM animal";
        Connection connection = DatabaseConnection.createConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt("total");
    }

    // ==================== PLACES CRUD ====================

    /**
     * Create - Insert new place
     */
    @SneakyThrows
    public static void createPlace(Place place) {
        String sql = "INSERT INTO place (id, \"row\", place_num, \"name\") VALUES (?, ?, ?, ?)";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, place.getId());
        ps.setInt(2, place.getRow());
        ps.setInt(3, place.getPlace_num());
        ps.setString(4, place.getName());
        ps.executeUpdate(); //executeUpdate() - для INSERT, UPDATE, DELETE (изменяют данные)
    }

    /**
     * Read - Get place by ID
     */
    @SneakyThrows
    public static Place getPlaceById(int id) {
        String sql = "SELECT * FROM place WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery(); //executeQuery() - для SELECT (читают данные)

        if (rs.next()) {
            /*
            rs.next() - метод ResultSet
            → Перемещает курсор на следующую строку (начальная позиция - курсор над строкой)
            → Возвращает true, если строка есть - возвращаем найденное животное
            → Возвращает false, если строк больше нет - животное не найдено, вернём null
             */
            return Place.builder() //Начинает построение объекта Animal через Builder pattern
                    .id(rs.getInt("id")) //- читает значение из ResultSet
                    .row(rs.getInt("row"))
                    .place_num(rs.getInt("place_num"))
                    .name(rs.getString("name"))
                    .build();
        }
        return null;
    }

    /**
     * Read - Get all places
     */
    @SneakyThrows
    public static List<Place> getAllPlaces() {
        return getPlaceData("SELECT * FROM place");
    }

    /**
     * Read - Get places by custom query
     */
    @SneakyThrows
    public static List<Place> getPlaceData(String query) {
        List<Place> places = new ArrayList<>();
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {  // - цикл, выполняется пока условие true
            places.add(Place.builder()
                    .id(rs.getInt("id")) //- читает значение из ResultSet
                    .row(rs.getInt("row"))
                    .place_num(rs.getInt("place_num"))
                    .name(rs.getString("name"))
                    .build());
        }
        return places;
    }

    /**
     * Update - Update existing place
     */
    @SneakyThrows
    public static void updatePlace(Place place) {
        String sql = "UPDATE place SET \"row\" = ?, place_num = ?, \"name\" = ? WHERE id  = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, place.getRow());
        ps.setInt(2, place.getPlace_num());
        ps.setString(3, place.getName());
        ps.setInt(4, place.getId());
        ps.executeUpdate(); //executeUpdate() - для INSERT, UPDATE, DELETE (изменяют данные)
    }

    /**
     * Delete - Delete place by ID
     */
    @SneakyThrows
    public static void deletePlace(int id) {
        String sql = "DELETE FROM place WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * Count places
     */
    @SneakyThrows
    public static int getPlacesCount() {
        String sql = "SELECT COUNT (*) as total FROM place";
        Connection connection = DatabaseConnection.createConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt("total");
    }

    // ==================== WORKMAN CRUD ====================

    /**
     * Create - Insert new workman
     */
    @SneakyThrows
    public static void createWorkman(Workman workman) {
        String sql = "INSERT INTO workman (id, \"name\", age, \"position\") VALUES (?, ?, ?, ?)";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, workman.getId());
        ps.setString(2, workman.getName());
        ps.setInt(3, workman.getAge());
        ps.setInt(4, workman.getPosition());
        ps.executeUpdate(); //executeUpdate() - для INSERT, UPDATE, DELETE (изменяют данные)
    }

    /**
     * Read - Get workman by ID
     */
    @SneakyThrows
    public static Workman getWorkmanById(int id) {
        String sql = "SELECT * FROM workman WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return Workman.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .position(rs.getInt("position"))
                    .build();
        }
        return null;
    }

    /**
     * Read - Get all workmen
     */
    @SneakyThrows
    public static List<Workman> getAllWorkmen() {
        String sql = "SELECT * FROM workman";
        List<Workman> workmenList = new ArrayList<>();
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            workmenList.add(Workman.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .position(rs.getInt("position"))
                    .build());
        }
        return workmenList;
    }

    /**
     * Update - Update existing workman
     */
    @SneakyThrows
    public static void updateWorkman(Workman workman) {
        // ВАЖНО: "name", "position" в кавычках
        String sql = "UPDATE workman SET \"name\" = ?, age = ?, \"position\" = ? WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, workman.getName());
        ps.setInt(2, workman.getAge());
        ps.setInt(3, workman.getPosition());
        ps.setInt(4, workman.getId());
        ps.executeUpdate();
    }

    /**
     * Delete - Delete workman by ID
     */
    @SneakyThrows
    public static void deleteWorkman(int id) {
        String sql = "DELETE FROM workman WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * Count workmen
     */
    @SneakyThrows
    public static int getWorkmanCount() {
        String sql = "SELECT COUNT(*) FROM workman";
        Connection connection = DatabaseConnection.createConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }

    // ==================== ZOO CRUD ====================

    /**
     * Create - Insert new zoo
     */
    @SneakyThrows
    public static void createZoo(Zoo zoo) {
        // ВАЖНО: "name" в кавычках
        String sql = "INSERT INTO zoo (id, \"name\") VALUES (?, ?)";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, zoo.getId());
        ps.setString(2, zoo.getName());
        ps.executeUpdate();
    }

    /**
     * Read - Get zoo by ID
     */
    @SneakyThrows
    public static Zoo getZooById(int id) {
        String sql = "SELECT * FROM zoo WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return Zoo.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .build();
        }
        return null;
    }

    /**
     * Read - Get all zoos
     */
    @SneakyThrows
    public static List<Zoo> getAllZoos() {
        String sql = "SELECT * FROM zoo";
        List<Zoo> zooList = new ArrayList<>();
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            zooList.add(Zoo.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .build());
        }
        return zooList;
    }

    /**
     * Read - Get zoo names only
     */
    @SneakyThrows
    public static List<String> getZooNames() {
        String sql = "SELECT \"name\" FROM zoo";
        List<String> names = new ArrayList<>();
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            names.add(rs.getString("name"));
        }
        return names;
    }

    /**
     * Update - Update existing zoo
     */
    @SneakyThrows
    public static void updateZoo(Zoo zoo) {
        // ВАЖНО: "name" в кавычках
        String sql = "UPDATE zoo SET \"name\" = ? WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, zoo.getName());
        ps.setInt(2, zoo.getId());
        ps.executeUpdate();
    }

    /**
     * Delete - Delete zoo by ID
     */
    @SneakyThrows
    public static void deleteZoo(int id) {
        String sql = "DELETE FROM zoo WHERE id = ?";
        Connection connection = DatabaseConnection.createConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    /**
     * Count zoos
     */
    @SneakyThrows
    public static int getZooCount() {
        String sql = "SELECT COUNT(*) FROM zoo";
        Connection connection = DatabaseConnection.createConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get count of rows in any table
     */
    @SneakyThrows
    public static int getRowCountByTable(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        Connection connection = DatabaseConnection.createConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int count = rs.getInt(1);
        System.out.printf("Table public.%s has exactly %s rows%n", tableName, count);
        //→ Первый %s заменится на tableName
        //→ Второй %s заменится на count
        return count;
    }
}
