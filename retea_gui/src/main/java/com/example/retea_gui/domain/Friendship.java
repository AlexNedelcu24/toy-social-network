package com.example.retea_gui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.retea_gui.utils.Utils.DATE_TIME_FORMATTER;

public class Friendship extends Entity<Integer>{
    private User user1;
    private User user2;
    private LocalDateTime date;

    public Friendship(User user1, User user2, LocalDateTime date){
        this.user1 = user1;
        this.user2 = user2;
        this.date = date;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "user1=" + user1 +
                ", user2=" + user2 +
                ", date=" + date.format(DATE_TIME_FORMATTER) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(user1, that.user1) && Objects.equals(user2, that.user2) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2, date);
    }
}
