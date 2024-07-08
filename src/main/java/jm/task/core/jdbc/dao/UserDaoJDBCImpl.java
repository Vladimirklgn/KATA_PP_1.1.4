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
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USERS"
                + "(ID BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,"
                + "NAME VARCHAR(50) NOT NULL,"
                + "LASTNAME VARCHAR(50) NOT NULL,"
                + "AGE TINYINT NOT NULL)";
        try (Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE)) {
            preparedStatement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String DROP_TABLE = "DROP TABLE IF EXISTS USERS";
        try (Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DROP_TABLE)) {
            preparedStatement.execute(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String INSERT_USER = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User " + name + " has been saved");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String DELETE_USER = "DELETE FROM USERS WHERE id = ?";
        try (Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS")) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("NAME");
                String lastName = rs.getString("LASTNAME");
                byte age = rs.getByte("AGE");

                users.add(new User(name, lastName, age));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String DELETE = "DELETE FROM USERS";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.execute(DELETE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
