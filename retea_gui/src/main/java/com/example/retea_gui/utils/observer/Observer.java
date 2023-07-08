package com.example.retea_gui.utils.observer;


import com.example.retea_gui.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}