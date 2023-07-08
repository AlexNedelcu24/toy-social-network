package com.example.retea_gui.controllers;

import com.example.retea_gui.domain.Friendship;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.domain.validators.UserValidator;
import com.example.retea_gui.domain.validators.Validator;
import com.example.retea_gui.repository.InterfaceFriendshipRepository;
import com.example.retea_gui.repository.Repository;
import com.example.retea_gui.repository.database.FriendshipDBRepository;
import com.example.retea_gui.repository.database.UserDBRepository;
import com.example.retea_gui.service.Service;
import com.example.retea_gui.service.user.UserService;
import com.example.retea_gui.utils.events.UserEntityChangeEvent;
import com.example.retea_gui.utils.observer.Observer;

public abstract class PrincipalController {

    protected static Integer IDfinal = 0;

    protected static Integer IDfriend = 0;

    protected static UserService userService;

    public void setService(UserService userService) {
        this.userService = userService;
    }



}
