package com.example.retea_gui;

import com.example.retea_gui.controllers.AfterLogin;
import com.example.retea_gui.controllers.Login;
import com.example.retea_gui.controllers.PrincipalController;
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
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGui extends Application {

    private static Stage stg;
    private Validator<User> val = new UserValidator();
    private Repository<Integer, User> repo = new UserDBRepository<>(val);
    private InterfaceFriendshipRepository friendshipDBRepository = new FriendshipDBRepository();

    private InterfaceRequestRepository<Integer, Request> requestDBRepository = new RequestDBRepository<>();

    private InterfaceMessageRepository<Integer, Message> messageDBRepository = new MessageDBRepository<>();
    private UserService ser = new UserService(repo, friendshipDBRepository, requestDBRepository, messageDBRepository);

    String scene = "login.fxml";

    @Override
    public void start(Stage primaryStage) throws IOException {

        stg = primaryStage;
        primaryStage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(MainGui.class.getResource( scene));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(scene);


        PrincipalController Controller = fxmlLoader.getController();


        Controller.setService(ser);

        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException{
        stg.close();
        //scene = fxml;
        this.start(stg);

        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}