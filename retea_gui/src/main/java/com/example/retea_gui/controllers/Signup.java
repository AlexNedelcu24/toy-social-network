package com.example.retea_gui.controllers;

import com.example.retea_gui.MainGui;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.domain.validators.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Signup extends PrincipalController{

    public Signup(){

    }

    @FXML
    private Button signup;
    @FXML
    private Label wrongSignup;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField age;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void userSignup(ActionEvent event) throws IOException {
        checkSignup();
    }

    private void checkSignup() throws IOException{
        MainGui m = new MainGui();
        int id = 0;
        boolean succes = false;

        if(first_name.getText().isEmpty() || last_name.getText().isEmpty() || age.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty()){
            wrongSignup.setText("Please enter your data.");
        }
        else {
            try {
                User user = userService.add(last_name.getText().toString(), first_name.getText().toString(), Integer.parseInt(age.getText().toString()), username.getText().toString(), password.getText().toString());
                succes = true;

                wrongSignup.setText("Succes!");

                //m.changeScene("login.fxml");
                id = user.getId();

            } catch (IllegalArgumentException e) {
                wrongSignup.setText("Wrong data!");
            }
            catch(ValidationException e){
                wrongSignup.setText(e.getMessage());
            }
            if(succes == true)
                userService.createFriendsList(id);
        }
    }

    @FXML
    private void onExit(ActionEvent actionEvent)  throws IOException {
        MainGui m = new MainGui();
        m.changeScene("login.fxml");
    }

}
