package com.example.retea_gui.repository.database;

import com.example.retea_gui.domain.Friendship;
import com.example.retea_gui.domain.User;
import com.example.retea_gui.domain.validators.UserValidator;
import com.example.retea_gui.repository.InterfaceFriendshipRepository;
import com.example.retea_gui.repository.Repository;
//import com.example.retea_gui.repository.memory.FriendshipRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FriendshipDBRepository implements InterfaceFriendshipRepository {
//public class FriendshipDBRepository extends FriendshipRepository {
    private final JDBCUtils jdbcUtils = new JDBCUtils();
    private Map<Integer, List<User>> userMap = new TreeMap<>();

    @Override
    public List<User> showFriends(Integer id){

        List<Friendship> f = this.showAll();

        List<User> l = userMap.get(id);
        if(l == null){
            l = new ArrayList<>();
        }
        return l;
    }

    public List<Friendship> showAll() {
        List<Friendship> list = new ArrayList<>();
        String query = "SELECT * FROM friendship";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("id_user1");
                Integer id2 = resultSet.getInt("id_user2");
                String friendsFrom = resultSet.getString("friendsfrom");
                Repository<Integer, User> repo = new UserDBRepository<>(new UserValidator());
                list.add(new Friendship(repo.map().get(id1), repo.map().get(id2),
                        LocalDateTime.parse(friendsFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Friendship f:list){
            User user1 = f.getUser1();
            User user2 = f.getUser2();
            LocalDateTime date = f.getDate();
            List<User> l = userMap.get(user1.getId());
            if (l == null) {
                l = new ArrayList<User>();
                userMap.put(user1.getId(), l);
            }
            l.add(user2);
        }

        return list;
    }

    @Override
    public void add(User user1, User user2, LocalDateTime date) {
        String query = "INSERT INTO friendship(id_user1, id_user2, friendsfrom) VALUES(?,?,?)";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, user1.getId());
            statement.setInt(2, user2.getId());
            statement.setString(3, date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<User> l = userMap.get(user1.getId());
        if (l == null) {
            l = new ArrayList<User>();
            userMap.put(user1.getId(), l);
        }
        l.add(user2);
    }

    @Override
    public void createFriendsList(Integer id) {
        List<User> l = new ArrayList<User>();
        userMap.put(id, l);
    }

    @Override
    public void remove2(Integer id1, User user2) {
        String query = "DELETE FROM friendship WHERE id_user1 = ? AND id_user2 = ?";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id1);
            statement.setInt(2, user2.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        List<User> l = userMap.get(id1);
        for(int i=0; i<l.size(); i++){
            if(l.get(i).getId() == user2.getId()){
                l.remove(i);
                break;
            }
        }

    }

    @Override
    public void removeFriendList(Integer id){
        userMap.remove(id);
    }

    @Override
    public void update(User user1, User user2){

        List<User> friends = this.showFriends(user2.getId());
        for(int i=0; i<friends.size(); i++){
            List<User> n = userMap.get(friends.get(i).getId());

            for(int j=0; j<n.size(); j++){
                if(n.get(i).getId() == user2.getId()){
                    n.set(i, user1);
                }
            }
            userMap.put(friends.get(i).getId(), n);
        }

    }

    public static void dfs(Integer k, Map<Integer, List<User>> m, List<Boolean> ver){
        ver.set(k, true);
        List<User> vecini = m.get(k);
        for(int i=0; i<vecini.size(); i++){
            if(!ver.get(vecini.get(i).getId())){
                dfs(vecini.get(i).getId(), m, ver);
            }
        }
    }

    @Override
    public int numarComunitati(){
        List<Boolean> ver = new ArrayList<>();
        for(int i=0; i<200; i++)
            ver.add(false);

        int nr = 0;
        for (Map.Entry<Integer, List<User>> entry : userMap.entrySet()){
            if(!ver.get(entry.getKey())){
                nr++;
                dfs(entry.getKey(), userMap, ver);
            }
        }

        return nr;

    }


    private static int lenMax=0;
    private static List<Integer> Longestlist= new ArrayList<>();
    public static void dfs2(Integer k, Map<Integer, List<User>> m, List<Boolean> ver, List<Integer> l, int len){
        len++;
        l.add(k);

        ver.set(k, true);

        List<User> vecini = m.get(k);
        for(int i=0; i<vecini.size(); i++){
            if(!ver.get(vecini.get(i).getId())){
                dfs2(vecini.get(i).getId(), m, ver,l, len);
                ver.set(vecini.get(i).getId(), false);
                l.remove(l.size()-1);
            }
        }

        if(lenMax < len){
            lenMax = len;
            Longestlist.clear();
            Longestlist.addAll(l);

        }

        len--;
    }

    @Override
    public List<Integer> CeaMaiSociabilaComunitate(){
        Longestlist.clear();
        lenMax=0;
        List<Integer> l = new ArrayList<>();
        List<Boolean> ver = new ArrayList<>();
        for(int i=0; i<200; i++)
            ver.add(false);


        for (Map.Entry<Integer, List<User>> entry : userMap.entrySet()){

            dfs2(entry.getKey(), userMap, ver, l ,0);
            ver.set(entry.getKey(), false);

            l.remove(l.size()-1);
        }


        return Longestlist;
    }
}
