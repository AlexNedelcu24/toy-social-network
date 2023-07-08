package com.example.retea_gui.repository;

import com.example.retea_gui.domain.Entity;

public interface InterfaceMessageRepository <ID, E extends Entity<ID>> {

    E saveMessage(E entity);
    Iterable<E> findMessages(Integer id1, Integer id2);
}
