package com.example.retea_gui.utils.observer;


import com.example.retea_gui.utils.events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);

    void removeObserver(Observer<E> e);

    void notifyObservers(E t);
}
