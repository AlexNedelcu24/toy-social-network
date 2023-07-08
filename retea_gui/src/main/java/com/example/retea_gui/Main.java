package com.example.retea_gui;

import com.example.retea_gui.domain.Friendship;
import com.example.retea_gui.domain.Message;
import com.example.retea_gui.domain.Request;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.domain.validators.UserValidator;
import com.example.retea_gui.domain.validators.Validator;
import com.example.retea_gui.repository.InterfaceFriendshipRepository;
import com.example.retea_gui.repository.InterfaceMessageRepository;
import com.example.retea_gui.repository.InterfaceRequestRepository;
import com.example.retea_gui.repository.Repository;
import com.example.retea_gui.repository.database.FriendshipDBRepository;
import com.example.retea_gui.repository.database.MessageDBRepository;
import com.example.retea_gui.repository.database.RequestDBRepository;
import com.example.retea_gui.repository.database.UserDBRepository;
import com.example.retea_gui.service.Service;
import com.example.retea_gui.service.user.UserService;
import com.example.retea_gui.ui.Console;


public class Main {
    public static void main(String[] args) {
        Validator<User> val = new UserValidator();
        //Repository<Integer, User> repo = new InMemoryRepository<>(val);
        //Repository<Integer, User> repo =  new UserFile("datafile/users.csv" ,val);
        Repository<Integer, User> repo = new UserDBRepository<>(val);
        //FriendshipRepository friendshipRepository = new FriendsFile("datafile/friends.csv");
        InterfaceFriendshipRepository friendshipDBRepository = new FriendshipDBRepository();
        InterfaceRequestRepository<Integer, Request> requestDBRepository = new RequestDBRepository<>();
        InterfaceMessageRepository<Integer, Message> messageDBRepository = new MessageDBRepository<>();
        Service<Integer, User, Friendship> ser = new UserService(repo, friendshipDBRepository, requestDBRepository, messageDBRepository);
        Console ui = new Console(ser);
        ui.run();


    }
}