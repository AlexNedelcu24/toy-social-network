package com.example.retea_gui.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.retea_gui.utils.Utils.DATE_TIME_FORMATTER;

public class Message extends Entity<Integer> {

    private Integer sent;
    private Integer received;
    private LocalDateTime date;

    private String text;

    public Message(Integer sentByUser, Integer receivedByUser, LocalDateTime date, String text){
        this.sent = sentByUser;
        this.received = receivedByUser;
        this.date = date;
        this.text = text;

    }

    public Integer getSent() {
        return sent;
    }

    public Integer getReceived() {
        return received;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getText() { return text;}

    public void setSent(Integer sentByUser) {
        this.sent = sentByUser;
    }

    public void setReceived(Integer receivedByUser) {
        this.received = receivedByUser;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setText(String status) { this.text = status;}

    @Override
    public String toString() {
        return "Message{" +
                "sentBy=" + sent +
                ", receivedBy=" + received +
                ", date=" + date.format(DATE_TIME_FORMATTER) +
                ", text=" + text +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message that = (Message) o;
        return Objects.equals(sent, that.sent) && Objects.equals(received, that.received) && Objects.equals(date, that.date) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sent, received, date, text);
    }
}