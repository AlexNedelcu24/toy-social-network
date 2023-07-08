package com.example.retea_gui.domain;

import java.util.Objects;

public class User extends Entity<Integer> {
    private String nume;
    private String prenume;
    private int varsta;
    private String username;

    private String password;

    //private List<User> friends;

    public User(String nume, String prenume, int varsta, String username, String password){
        //super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.username = username;
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
    public String getPrenume() {
        return prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }
    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }



    //public List<User> getFriends(){
    //    return friends;
    //}

    public String toString(){
        // return getId() + " " + nume + " " + prenume + " " + varsta + " friends: " + friends;
        return getId() + " " + nume + " " + prenume + " " + varsta + " " + username + " " + password ;
    }

    @Override
    public int hashCode(){
        return Objects.hash(nume, prenume, varsta, username, password);
        // return Objects.hash(nume, prenume, varsta, friends);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User)) return false;

        User that = (User) o;

        /*return Integer.compare(ut.id, id) == 0 // abs(x-y)<epsilon
                && Objects.equals(nume, ut.nume) && Objects.equals(prenume, ut.prenume)
                && Integer.compare(ut.varsta, varsta) == 0;*/

        return getNume().equals(that.getNume()) &&
                getPrenume().equals(that.getPrenume()) &&
                Integer.compare(that.varsta, varsta) == 0 &&
                getUsername().equals(that.getUsername()) &&
                getPassword().equals(that.getPassword());
        //   getFriends().equals(that.getFriends());

    }
}
