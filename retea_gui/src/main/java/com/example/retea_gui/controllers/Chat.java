package com.example.retea_gui.controllers;

import com.example.retea_gui.MainGui;
import com.example.retea_gui.domain.Message;
import com.example.retea_gui.domain.Request;
import com.example.retea_gui.utils.events.ChangeEventType;
import com.example.retea_gui.utils.events.UserEntityChangeEvent;
import com.example.retea_gui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Chat extends PrincipalController implements Observer<UserEntityChangeEvent>, Initializable {

    private final ObservableList<Message> usersModel = FXCollections.observableArrayList();

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox chatMessages;
    @FXML
    private TextField messageText;
    @FXML
    private Button sendButton;
    @FXML
    private VBox messageContainer;

    @FXML
    private Label label1;
    @FXML
    private Label label2;

    private URL loc;
    private ResourceBundle res;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loc = location;
        res = resources;

        this.setService();

        label1.setText(userService.findOne(IDfriend).getNume());
        label2.setText(userService.findOne(IDfriend).getPrenume());

        scrollPane.setContent(messageContainer);
    }

    @Override
    public void update(UserEntityChangeEvent studentEntityChangeEvent) {
        initModel(loc,res);
    }

    public void setService() {
        super.userService.addObserver( this);
        initModel(loc,res);
    }

    private void initModel(URL url, ResourceBundle resourceBundle) {
        messageContainer.getChildren().clear();

        Iterable<Message> users = userService.findAllMessages(IDfinal, IDfriend);

        List<Message> us = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());

        usersModel.clear();
        usersModel.setAll(us);

        for (Message message : usersModel) {
            if (message.getSent() == IDfinal && message.getReceived() == IDfriend) {

                // mesaj trimis de userul curent
                HBox messageBox = new HBox();
                Label messageLabel = new Label(message.getText());
                messageBox.getChildren().add(messageLabel);
                messageBox.setAlignment(Pos.CENTER_LEFT);
                messageLabel.setBackground(new Background(new BackgroundFill(Color.SKYBLUE,new CornerRadii(70), Insets.EMPTY)));
                messageLabel.setFont(new Font(20));
                messageContainer.getChildren().add(messageBox);
            } else if (message.getSent() == IDfriend && message.getReceived() == IDfinal) {

                // mesaj primit de userul curent
                HBox messageBox = new HBox();
                Label messageLabel = new Label(message.getText());
                messageBox.getChildren().add(messageLabel);
                messageBox.setAlignment(Pos.CENTER_RIGHT);
                messageLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,new CornerRadii(70), Insets.EMPTY)));
                messageLabel.setFont(new Font(20));
                messageContainer.getChildren().add(messageBox);
            }
        }


    }

    @FXML
    private void onSend(ActionEvent actionEvent)  throws IOException {
        Message message = new Message(IDfinal,IDfriend,LocalDateTime.now(),messageText.getText());

        if(!messageText.getText().isEmpty()){
            userService.addMessage(IDfinal,IDfriend,messageText.getText());

            userService.notifyObservers(new UserEntityChangeEvent(ChangeEventType.UPDATE, message));
        }

    }

    @FXML
    private void onExit(ActionEvent actionEvent)  throws IOException {
        MainGui m = new MainGui();
        m.changeScene("afterLogin.fxml");
    }

    @FXML
    private void onRefresh(ActionEvent actionEvent)  throws IOException {

        scrollPane.setContent(null);

        initialize(loc, res);

    }

    /*private void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(!messageText.getText().isEmpty()) {
                    onReceiveMessage(messageText.getText());
                }
            }
        };
        timer.schedule(task, 0, 10000);
    }

    @FXML
    private void onReceiveMessage(Message message) {
        updateChatWindow(message);
    }

    private void updateChatWindow(Message message) {
        HBox messageBox = new HBox();
        Label messageLabel = new Label(message.getText());
        if (message.getSent() == IDfinal && message.getReceived() == IDfriend) {
            // mesaj trimis de userul curent
            messageBox.getChildren().add(messageLabel);
            messageBox.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, new CornerRadii(70), Insets.EMPTY)));
        } else if (message.getSent() == IDfriend && message.getReceived() == IDfinal) {
            // mesaj primit de userul curent
            messageBox.getChildren().add(messageLabel);
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(70), Insets.EMPTY)));
        }
        messageLabel.setFont(new Font(20));
        messageContainer.getChildren().add(messageBox);
    }*/


}
