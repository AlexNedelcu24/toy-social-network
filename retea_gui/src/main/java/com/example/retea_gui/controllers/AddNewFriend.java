package com.example.retea_gui.controllers;

import com.example.retea_gui.MainGui;
import com.example.retea_gui.domain.User;
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
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AddNewFriend extends PrincipalController implements Observer<UserEntityChangeEvent>, Initializable {

    private final ObservableList<User> usersModel = FXCollections.observableArrayList();



    @FXML
    private Button exit;
    @FXML
    private Label requestSent;

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, String> first_nameColumn;
    @FXML
    private TableColumn<User, String> last_nameColumn;
    @FXML
    private TableColumn<User, Integer> ageColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TextField filterName;

    private URL loc;
    private ResourceBundle res;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loc = location;
        res = resources;
        this.setService();
        first_nameColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        last_nameColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("varsta"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usersTableView.setItems(usersModel);

        filterName.textProperty().addListener(f -> handleFilter());
    }

    @Override
    public void update(UserEntityChangeEvent studentEntityChangeEvent) {
        initModel(loc,res);
    }

    private void initModel(URL url, ResourceBundle resourceBundle) {
        Iterable<User> users = userService.findAll();

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == IDfinal) {
                iterator.remove();
            }
        }

        List<User> us = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());
        usersModel.clear();
        usersModel.setAll(us);
    }

    public void setService() {
        super.userService.addObserver( this);
        initModel(loc,res);
    }


    @FXML
    private void onSelectedUser(MouseEvent mouseEvent) {
        User user = (User) usersTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onExit(ActionEvent actionEvent)  throws IOException {
        MainGui m = new MainGui();
        m.changeScene("afterLogin.fxml");
    }

    @FXML
    private void onSendRequest(ActionEvent actionEvent) {
        User user = (User) usersTableView.getSelectionModel().getSelectedItem();

        Boolean a = true;

        Iterable<User> users = userService.showFriends(user.getId());
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User u = iterator.next();
            if (u.getId() == IDfinal) {
                a = false;
            }
        }

        if (user != null && a) {

            try{
                userService.addRequest(IDfinal,user.getId());

                requestSent.setText("Request sent");
            }
            catch(IllegalArgumentException e){
                requestSent.setText("Error");
            }
        }
        else{
            requestSent.setText("Already friends");
        }
    }


    private void handleFilter() {
        Predicate<User> p1 = n -> n.getNume().startsWith(filterName.getText());
        List<User> users = (List<User>) userService.findAll();

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == IDfinal) {
                iterator.remove();
            }
        }

        usersModel.setAll(users
                .stream()
                .filter(p1)
                .collect(Collectors.toList()));
    }
}

