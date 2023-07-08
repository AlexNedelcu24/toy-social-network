package com.example.retea_gui.controllers;

import com.example.retea_gui.MainGui;
import com.example.retea_gui.domain.Request;
import com.example.retea_gui.utils.events.ChangeEventType;
import com.example.retea_gui.utils.events.UserEntityChangeEvent;
import com.example.retea_gui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SeeTheRequests extends PrincipalController implements Observer<UserEntityChangeEvent>, Initializable {

    private final ObservableList<Request> usersModel = FXCollections.observableArrayList();

    @FXML
    private TableView<Request> requestsTableView;

    @FXML
    private TableColumn<Request, Integer> sent_by_userColumn;
    @FXML
    private TableColumn<Request, Integer> received_by_userColumn;
    @FXML
    private TableColumn<Request, String> dateColumn;
    @FXML
    private TableColumn<Request, String> statusColumn;


    private URL loc;
    private ResourceBundle res;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loc = location;
        res = resources;
        this.setService();
        sent_by_userColumn.setCellValueFactory(new PropertyValueFactory<>("sentByUser"));
        received_by_userColumn.setCellValueFactory(new PropertyValueFactory<>("receivedByUser"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        requestsTableView.setItems(usersModel);
    }

    @Override
    public void update(UserEntityChangeEvent studentEntityChangeEvent) {
        initModel(loc,res);
    }

    private void initModel(URL url, ResourceBundle resourceBundle) {
        Iterable<Request> users = userService.findRequests(IDfinal);

        List<Request> us = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());

        usersModel.clear();
        usersModel.setAll(us);
    }

    public void setService() {
        super.userService.addObserver( this);
        initModel(loc,res);
    }

    @FXML
    private void onExit(ActionEvent actionEvent)  throws IOException {
        MainGui m = new MainGui();
        m.changeScene("afterLogin.fxml");
    }

    @FXML
    private void onSelectedUser(MouseEvent mouseEvent) {
        Request request = (Request) requestsTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void onAccept(ActionEvent actionEvent)  throws IOException {
        Request request = (Request) requestsTableView.getSelectionModel().getSelectedItem();

        if (request.getStatus().equals("Pending") && request.getReceivedByUser() == IDfinal ) {
            userService.addFriendship(request.getSentByUser(), request.getReceivedByUser());

            userService.upRequest(request.getSentByUser(), request.getReceivedByUser(), "Accepted");
        }

        userService.notifyObservers(new UserEntityChangeEvent(ChangeEventType.UPDATE, request));
    }


    @FXML
    private void onReject(ActionEvent actionEvent)  throws IOException {
        Request request = (Request) requestsTableView.getSelectionModel().getSelectedItem();

        if (request.getStatus().equals("Pending") && request.getReceivedByUser() == IDfinal ) {

            userService.upRequest(request.getSentByUser(), request.getReceivedByUser(), "Rejected");
        }

        userService.notifyObservers(new UserEntityChangeEvent(ChangeEventType.UPDATE, request));
    }


    @FXML
    private void onDelete(ActionEvent actionEvent)  throws IOException {
        Request request = (Request) requestsTableView.getSelectionModel().getSelectedItem();

        if (request.getSentByUser() == IDfinal ) {

            userService.deleteRequest(request.getSentByUser(), request.getReceivedByUser());
        }

        userService.notifyObservers(new UserEntityChangeEvent(ChangeEventType.UPDATE, request));
    }
}
