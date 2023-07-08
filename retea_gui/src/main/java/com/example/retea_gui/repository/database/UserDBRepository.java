package com.example.retea_gui.repository.database;

import com.example.retea_gui.domain.Entity;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.domain.validators.Validator;
import com.example.retea_gui.repository.Repository;
//import com.example.retea_gui.repository.memory.InMemoryRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//public class UserDBRepository<ID, E extends Entity<ID>> extends InMemoryRepository<Integer, User> {
public class UserDBRepository<ID, E extends Entity<ID>> implements Repository<Integer, User> {

    private final JDBCUtils jdbcUtils = new JDBCUtils();
    private Validator<User> validator;

    public UserDBRepository(Validator<User> validator) {
        this.validator = validator;
    }

    @Override
    public User findOne(Integer id) {
        String query = "SELECT * from users";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                Integer varsta = resultSet.getInt("varsta");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                if (Objects.equals(id, id1)) {
                    User user = new User(first_name, last_name, varsta, username, password);
                    user.setId(id);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findLog(String username, String password) {
        String query = "SELECT * from users";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                Integer varsta = resultSet.getInt("varsta");
                String username1 = resultSet.getString("username");
                String password1 = resultSet.getString("password");
                if (Objects.equals(username, username1) && Objects.equals(password,password1)) {
                    User user = new User(first_name, last_name, varsta, username, password);
                    user.setId(id1);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        //Set<User> set = new HashSet<>();
        List<User> set = new ArrayList<>();

        String query = "SELECT * from users";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                Integer varsta = resultSet.getInt("varsta");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(first_name, last_name, varsta, username, password);
                user.setId(id);
                set.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    @Override
    public Iterable<User> findByName(String name) {
        //Set<User> set = new HashSet<>();
        List<User> set = new ArrayList<>();

        String query = "SELECT * from users";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                Integer varsta = resultSet.getInt("varsta");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(first_name, last_name, varsta, username, password);
                user.setId(id);
                if(first_name.equals(name)) set.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    @Override
    public Map<Integer, User> map() {
        Map<Integer, User> map = new HashMap<>();
        String query = "SELECT * from users";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                Integer varsta = resultSet.getInt("varsta");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(first_name, last_name, varsta, username, password);
                user.setId(id);
                map.put(id, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public User save(User entity) {
        validator.validate(entity);

        String query = "INSERT INTO users(first_name, last_name, varsta, username, password) VALUES (?,?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, id);
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
            statement.setInt(3, entity.getVarsta());
            statement.setString(4,entity.getUsername());
            statement.setString(5,entity.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public User delete(Integer id) {
        String query = "DELETE FROM users WHERE id = ?";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.findOne(id);
    }

    @Override
    public User update(User entity) {
        User curent = this.findOne(entity.getId());

        validator.validate(entity);

        String query = "UPDATE users SET first_name=?,last_name=?,varsta=?,username=?,password=? WHERE id=?";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
            statement.setInt(3, entity.getVarsta());
            statement.setString(4,entity.getUsername());
            statement.setString(5,entity.getPassword());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return curent;
    }
}
