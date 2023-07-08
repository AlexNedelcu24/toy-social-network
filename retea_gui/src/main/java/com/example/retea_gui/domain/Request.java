package com.example.retea_gui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.retea_gui.utils.Utils.DATE_TIME_FORMATTER;

public class Request extends Entity<Integer> {

    private Integer sentByUser;
    private Integer receivedByUser;
    private LocalDateTime date;

    private String status;

    public Request(Integer sentByUser, Integer receivedByUser, LocalDateTime date, String status){
        this.sentByUser = sentByUser;
        this.receivedByUser = receivedByUser;
        this.date = date;
        this.status = status;

    }

    public Integer getSentByUser() {
        return sentByUser;
    }

    public Integer getReceivedByUser() {
        return receivedByUser;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getStatus() { return status;}

    public void setSentByUser(Integer sentByUser) {
        this.sentByUser = sentByUser;
    }

    public void setReceivedByUser(Integer receivedByUser) {
        this.receivedByUser = receivedByUser;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus(String status) { this.status = status;}

    @Override
    public String toString() {
        return "Request{" +
                "sentByUser=" + sentByUser +
                ", receivedByUser=" + receivedByUser +
                ", date=" + date.format(DATE_TIME_FORMATTER) +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request that = (Request) o;
        return Objects.equals(sentByUser, that.sentByUser) && Objects.equals(receivedByUser, that.receivedByUser) && Objects.equals(date, that.date) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentByUser, receivedByUser, date, status);
    }
}
