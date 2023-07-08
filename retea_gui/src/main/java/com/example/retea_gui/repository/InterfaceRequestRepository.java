package com.example.retea_gui.repository;

import com.example.retea_gui.domain.Entity;

public interface InterfaceRequestRepository <ID, E extends Entity<ID>> {

    E saveRequest(E entity);

    Iterable<E> findUserRequests(Integer id);

    void updateRequest(ID id1, ID id2, String message);

    void delete(ID id1, ID id2);
}
