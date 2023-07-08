package com.example.retea_gui.controllers;

import com.example.retea_gui.MainGui;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.service.user.UserService;
import com.example.retea_gui.utils.events.ChangeEventType;
import com.example.retea_gui.utils.events.UserEntityChangeEvent;
import com.example.retea_gui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AfterLogin extends PrincipalController implements Observer<UserEntityChangeEvent>, Initializable {

    private final ObservableList<User> usersModel = FXCollections.observableArrayList();


    @FXML
    private Button logout;

    @FXML
    private TextField idFI;


    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> numeColumn;
    @FXML
    private TableColumn<User, String> prenumeColumn;
    @FXML
    private TableColumn<User, Integer> varstaColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private Label curentID;

    private URL loc;
    private ResourceBundle res;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loc = location;
        res = resources;
        this.setService();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        varstaColumn.setCellValueFactory(new PropertyValueFactory<>("varsta"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usersTableView.setItems(usersModel);
    }

    @Override
    public void update(UserEntityChangeEvent studentEntityChangeEvent) {
        initModel(loc,res);
    }

    private void initModel(URL url, ResourceBundle resourceBundle) {
        Iterable<User> users = userService.showFriends(IDfinal);
        curentID.setText(Integer.toString(IDfinal));
        List<User> us = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
        usersModel.clear();
        usersModel.setAll(us);
    }

    public void setService() {
        super.userService.addObserver( this);
        initModel(loc,res);
    }

    @FXML
    public void userLogout(ActionEvent event) throws IOException{
        MainGui m = new MainGui();
        m.changeScene("login.fxml");
    }

    @FXML
    private void onSelectedUser(MouseEvent mouseEvent) {
        User user = (User) usersTableView.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void onDeleteSelectedUser(ActionEvent actionEvent) {
        User user = (User) usersTableView.getSelectionModel().getSelectedItem();
        if (user != null) {
            userService.removeFriendship(IDfinal, user.getId());
        }

        userService.notifyObservers(new UserEntityChangeEvent(ChangeEventType.DELETE, user));
    }

    @FXML
    private void onAddNewFriend(ActionEvent actionEvent)  throws IOException{
        MainGui m = new MainGui();
        m.changeScene("addNewFriend.fxml");
    }

    @FXML
    private void onSeeTheRequests(ActionEvent actionEvent) throws IOException{
        MainGui m = new MainGui();
        m.changeScene("seeTheRequests.fxml");
    }

    @FXML
    private void onOpenChat(ActionEvent actionEvent) throws IOException{
        MainGui m = new MainGui();

        User user = (User) usersTableView.getSelectionModel().getSelectedItem();
        IDfriend = user.getId();

        m.changeScene("chat.fxml");


    }
}
