package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection CONNECTION;

    public UserDaoJDBCImpl() {
        CONNECTION = Util.getConnection();
    }

    public void createUsersTable() {
        try (PreparedStatement statement = CONNECTION.prepareStatement("CREATE TABLE IF NOT EXISTS Users"
                + " (id BIGINT AUTO_INCREMENT, name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, "
                + "age TINYINT NOT NULL, PRIMARY KEY (id));")) {
            statement.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = CONNECTION.prepareStatement("DROP TABLE IF EXISTS Users;")) {
            statement.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = CONNECTION.prepareStatement("INSERT INTO Users(name, lastname, age) VALUES (?, ?, ?);")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = CONNECTION.prepareStatement("DELETE FROM Users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (PreparedStatement statement = CONNECTION.prepareStatement("SELECT * FROM Users;")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
                CONNECTION.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement = CONNECTION.prepareStatement("TRUNCATE TABLE Users;")) {
            statement.executeUpdate();
            CONNECTION.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
