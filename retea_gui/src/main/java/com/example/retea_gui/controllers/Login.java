package com.example.retea_gui.controllers;

import com.example.retea_gui.MainGui;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.service.user.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login extends PrincipalController {

    public Login(){

    }

    @FXML
    private Button login;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void userLogin(ActionEvent event) throws IOException{
        checkLogin();
    }

    public void userSignup(ActionEvent event) throws IOException{
        MainGui m = new MainGui();
        m.changeScene("signup.fxml");
    }

    private void checkLogin() throws IOException{
        MainGui m = new MainGui();

        if(username.getText().isEmpty() || password.getText().isEmpty()){
            wrongLogin.setText("Please enter your data.");
        }
        else {
            try {
                User user = userService.findLog(username.getText().toString(), password.getText().toString());

                IDfinal = user.getId();

                wrongLogin.setText("Succes!");

                m.changeScene("afterLogin.fxml");

            } catch (IllegalArgumentException e) {
                wrongLogin.setText("Wrong username or password!");
            }
        }


    }


}
