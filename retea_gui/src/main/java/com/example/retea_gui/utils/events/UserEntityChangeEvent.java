package com.example.retea_gui.utils.events;


import com.example.retea_gui.domain.User;

public class UserEntityChangeEvent<E> implements Event {
    private final ChangeEventType type;
    private final E data;
    private E oldData;

    public UserEntityChangeEvent(ChangeEventType type, E data) {
        this.type = type;
        this.data = data;
    }

    public UserEntityChangeEvent(ChangeEventType type, E data, E oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public E getData() {
        return data;
    }

    public E getOldData() {
        return oldData;
    }
}