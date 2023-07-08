package com.example.retea_gui.repository.database;

import com.example.retea_gui.domain.Entity;
import com.example.retea_gui.domain.Friendship;
import com.example.retea_gui.domain.Request;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.domain.validators.UserValidator;
import com.example.retea_gui.repository.InterfaceRequestRepository;
import com.example.retea_gui.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RequestDBRepository <ID, E extends Entity<ID>> implements InterfaceRequestRepository<Integer, Request> {

    private final JDBCUtils jdbcUtils = new JDBCUtils();

    @Override
    public Request saveRequest(Request entity) {
        String query = "INSERT INTO requests(sent_by_user, received_by_user, sent_at, status) VALUES (?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, id);
            statement.setInt(1, entity.getSentByUser());
            statement.setInt(2, entity.getReceivedByUser());
            statement.setString(3, entity.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            statement.setString(4,entity.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public Iterable<Request> findUserRequests(Integer id) {
        //Set<User> set = new HashSet<>();
        List<Request> set = new ArrayList<>();

        String query = "SELECT * from requests";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("sent_by_user");
                Integer id2 = resultSet.getInt("received_by_user");
                String date = resultSet.getString("sent_at");
                String status = resultSet.getString("status");

                Request request = new Request(id1, id2,
                        LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), status);

                if(id2 == id || id1 == id) set.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }


    @Override
    public void updateRequest(Integer id1, Integer id2, String message) {
        //String query = "INSERT INTO requests(sent_by_user, received_by_user, sent_at, status) VALUES (?,?,?,?)";
        String query = "UPDATE requests SET status = ? WHERE sent_by_user = ? and received_by_user = ?";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, id);
            statement.setString(1, message);
            statement.setInt(2, id1);
            statement.setInt(3, id2);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void delete(Integer id1, Integer id2) {
        //String query = "INSERT INTO requests(sent_by_user, received_by_user, sent_at, status) VALUES (?,?,?,?)";
        String query = "DELETE FROM requests WHERE sent_by_user = ? and received_by_user = ?";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, id);
            statement.setInt(1, id1);
            statement.setInt(2, id2);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
