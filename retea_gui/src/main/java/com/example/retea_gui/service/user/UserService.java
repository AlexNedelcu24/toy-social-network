package com.example.retea_gui.service.user;

import com.example.retea_gui.domain.Friendship;
import com.example.retea_gui.domain.Message;
import com.example.retea_gui.domain.Request;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.repository.InterfaceFriendshipRepository;
import com.example.retea_gui.repository.InterfaceMessageRepository;
import com.example.retea_gui.repository.InterfaceRequestRepository;
import com.example.retea_gui.repository.Repository;
import com.example.retea_gui.service.Service;
import com.example.retea_gui.utils.events.UserEntityChangeEvent;
import com.example.retea_gui.utils.observer.Observable;
import com.example.retea_gui.utils.observer.Observer;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserService implements Service<Integer, User, Friendship> , Observable<UserEntityChangeEvent> {

    private Repository<Integer, User> repository;

    private InterfaceFriendshipRepository friendship;

    private InterfaceRequestRepository<Integer, Request> requests;

    private InterfaceMessageRepository<Integer, Message> messages;

    private final List<Observer<UserEntityChangeEvent>> observers = new ArrayList<>();


    public UserService(Repository<Integer,User> repository, InterfaceFriendshipRepository friendshipRepository, InterfaceRequestRepository<Integer, Request> requests, InterfaceMessageRepository<Integer, Message> messages){
        this.repository = repository;
        this.friendship = friendshipRepository;
        this.requests = requests;
        this.messages = messages;
    }

    @Override
    public void addObserver(Observer<UserEntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<UserEntityChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(UserEntityChangeEvent t) {
        observers.stream().forEach(x -> x.update(t));
    }

    @Override
    public User findOne(Integer id){
        User user = repository.findOne(id);

        if(user == null)
            throw new IllegalArgumentException("Nu exista user cu acest id");

        return user;
    }

    @Override
    public User findLog(String username, String password){
        User user = repository.findLog(username,password);

        if(user == null)
            throw new IllegalArgumentException("Nu exista user logat cu aceste date");

        return user;
    }

    @Override
    public Iterable<User> findAll(){
        return repository.findAll();
    }

    @Override
    public Iterable<User> findByName(String name){
        return repository.findByName(name);
    }

    @Override
    public Iterable<Friendship> findAllFriends(){
        return friendship.showAll();
    }

    @Override
    public User add(String nume, String prenume, int varsta, String username, String password){

        User u = new User(nume, prenume, varsta, username, password);

        if(repository.map().size() == 0){
            u.setId(1);
        }
        else{
            int maxKey = 0;
            for (Map.Entry<Integer, User> entry : repository.map().entrySet()){
                if(entry.getKey() > maxKey)
                    maxKey = entry.getKey();
            }
            u.setId(maxKey+1);

        }

        return repository.save(u);
    }

    @Override
    public void createFriendsList(Integer id){
        friendship.createFriendsList(id);
    }


    @Override
    public User update(Integer id, String nume, String prenume, int varsta, String username, String password){
        User u = new User(nume,prenume,varsta,username,password);
        u.setId(id);

        User user = repository.update(u);

        friendship.update(u, user);

        return user;
    }

    @Override
    public User remove(Integer id){
        List<User> friends = this.showFriends(id);
        for(int i=0; i<friends.size(); i++){
                User friend = friends.get(i);

                friendship.remove2(friend.getId(), (User) repository.map().get(id));
        }
        friendship.removeFriendList(id);

        return repository.delete(id);
    }

    @Override
    public void addFriendship(Integer id1, Integer id2){
        if(repository.map().get(id1) == null)
            throw new IllegalArgumentException("Nu exista user cu primul id");
        if(repository.map().get(id2) == null)
            throw new IllegalArgumentException("Nu exista user cu al doilea id");

        User u = (User) repository.map().get(id1);
        User u2 = (User) repository.map().get(id2);
        friendship.add(u, u2, LocalDateTime.now());
        friendship.add(u2, u, LocalDateTime.now());
    }

    @Override
    public List<User> showFriends(Integer id){
        return friendship.showFriends(id);
    }

    @Override
    public void removeFriendship(Integer id1, Integer id2){
        if(repository.map().get(id1) == null)
            throw new IllegalArgumentException("Nu exista user cu primul id");
        if(repository.map().get(id2) == null)
            throw new IllegalArgumentException("Nu exista user cu al doilea id");

        User u = (User) repository.map().get(id1);
        User u2 = (User) repository.map().get(id2);
        friendship.remove2((int)id1, u2);
        friendship.remove2((int)id2, u);
    }

    @Override
    public int comunitati(){
        return friendship.numarComunitati();
    }

    @Override
    public List<User> comunitateSociabila(){
        List<Integer> id = friendship.CeaMaiSociabilaComunitate();
        List<User> l = new ArrayList<>();

        for(int i=0; i<id.size(); i++){
            l.add(repository.map().get(id.get(i)));
        }

        return l;
    }

    @Override
    public void addRequest(Integer id1, Integer id2){
        if(repository.map().get(id1) == null)
            throw new IllegalArgumentException("Nu exista user cu primul id");
        if(repository.map().get(id2) == null)
            throw new IllegalArgumentException("Nu exista user cu al doilea id");


        Request request = new Request(id1, id2, LocalDateTime.now(), "Pending");
        requests.saveRequest(request);
    }

    @Override
    public Iterable<Request> findRequests(Integer id){
        return requests.findUserRequests(id);
    }


    @Override
    public void upRequest(Integer id1, Integer id2, String message){
        requests.updateRequest(id1, id2, message);
    }

    @Override
    public void deleteRequest(Integer id1, Integer id2){
        requests.delete(id1, id2);
    }

    @Override
    public Iterable<Message> findAllMessages(Integer id1, Integer id2){
        return messages.findMessages(id1, id2);
    }

    @Override
    public void addMessage(Integer id1, Integer id2, String text){
        Message message = new Message(id1, id2, LocalDateTime.now(), text);
        messages.saveMessage(message);
    }
}