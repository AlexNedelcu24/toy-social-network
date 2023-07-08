package com.example.retea_gui.repository;

import com.example.retea_gui.domain.Friendship;
import com.example.retea_gui.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface InterfaceFriendshipRepository {

    List<Friendship> showAll();

    List<User> showFriends(Integer id);

    void add(User u ,User user, LocalDateTime date);

    void createFriendsList(Integer id);

    void update(User user1, User user2);

    void remove2(Integer id , User user) throws IllegalArgumentException;

    void removeFriendList(Integer id);

    int numarComunitati();

    List<Integer> CeaMaiSociabilaComunitate();
}
