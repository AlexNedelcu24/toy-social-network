package com.example.retea_gui.repository.database;

import com.example.retea_gui.domain.*;
import com.example.retea_gui.domain.validators.UserValidator;
import com.example.retea_gui.repository.InterfaceMessageRepository;
import com.example.retea_gui.repository.InterfaceRequestRepository;
import com.example.retea_gui.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MessageDBRepository <ID, E extends Entity<ID>> implements InterfaceMessageRepository<Integer, Message> {

    private final JDBCUtils jdbcUtils = new JDBCUtils();


    @Override
    public Message saveMessage(Message entity) {
        String query = "INSERT INTO messages(sent, received, data, text) VALUES (?,?,?,?)";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, id);
            statement.setInt(1, entity.getSent());
            statement.setInt(2, entity.getReceived());
            statement.setString(3, entity.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            statement.setString(4,entity.getText());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public Iterable<Message> findMessages(Integer id_sent, Integer id_recv) {
        List<Message> set = new ArrayList<>();

        String query = "SELECT * from messages";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("sent");
                Integer id2 = resultSet.getInt("received");
                String date = resultSet.getString("data");
                String text = resultSet.getString("text");

                Message message = new Message(id1, id2,
                        LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")), text);

                if(  (id1 == id_sent && id2 == id_recv)  ||  (id2 == id_sent && id1 == id_recv)  ) set.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MessageDateComparator comparator = new MessageDateComparator();
        Collections.sort(set, comparator);

        return set;
    }

    public static class MessageDateComparator implements Comparator<Message> {
        @Override
        public int compare(Message m1, Message m2) {
            LocalDateTime date1 = m1.getDate();
            LocalDateTime date2 = m2.getDate();
            return date1.compareTo(date2);
        }
    }
}
