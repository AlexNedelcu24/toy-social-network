package com.example.retea_gui.service;

import com.example.retea_gui.domain.Entity;
import com.example.retea_gui.domain.Friendship;
import com.example.retea_gui.domain.Message;
import com.example.retea_gui.domain.Request;
import com.example.retea_gui.domain.validators.ValidationException;

import java.util.List;

/**
 *
 * @param <ID> tipul E trebuie sa aiba un atribut de tipul ID
 * @param <E> tipul entitatilor salvate in repository
 */
public interface Service<ID,E extends Entity,F extends Friendship> {
    /**
     *
     * @param id
     *          id-ul entitatii de returnat
     * @return entitatea cu ID-ul specificat
     *          null daca nu exista entitate cu acest id
     * @throws IllegalArgumentException
     *              daca id-ul este null
     */
    E findOne(ID id);

    E findLog(String username, String password);


    /**
     * @return toate entitatile
     */
    Iterable<E> findAll();

    Iterable<E> findByName(String name);

    Iterable<F> findAllFriends();

    /**
     *
     * @param nume
     *         numele user-ului de adaugat
     * @param prenume
     *         prenumele user-ului de adaugat
     * @param varsta
     *         varsta user-ului de adaugat
     * @return null- daca datele nu sunt valide
     * @throws ValidationException
     * @throws IllegalArgumentException
     *          daca entitatea data este null
     */
    E add(String nume, String prenume, int varsta, String username, String password);

    E update(Integer id, String nume, String prenume, int varsta, String username, String password);

    /**
     * sterge un user
     * @param id
     * id-ul entitatii de adaugat
     * @return
     * entitatea stearsa sau null daca nu exista entitate cu acest id
     * @throws IllegalArgumentException
     * daca id-ul este null
     */

    E remove(ID id);


    /**
     * creeaza prietenie
     * @param id1
     * id-ul primului prieten
     * @param id2
     * id-ul celui de-al doilea prieten
     * @throws IllegalArgumentException
     * daca nu exista un user cu id-ul specificat
     */
    void addFriendship(ID id1, ID id2);

    /**
     * arata prietenii unui user
     * @param id
     * id-ul user-ului caruia dorim sa ii vedem prietenii
     * @return lista de prieteni
     */
    List<E> showFriends(ID id);

    /**
     * sterge o prietenie
     * @param id1
     * id-ul primului prieten
     * @param id2
     * id-ul celui de-al doilea prieten
     * @throws IllegalArgumentException
     * daca nu exista user cu id-ul specificat
     */
    void removeFriendship(ID id1, ID id2);

    /**
     * afla numarul de componente conexe
     * @return numarul de comunitati din retea
     */
    int comunitati();

    /**
     * afla userii din cea mai sociabila comunitate
     * @return lista de useri ce apartin comunitatii
     */
    List<E> comunitateSociabila();

    /**
     * introduce user-ul in lista de prietenii
     * @param id
     * id-ul user-ului de adaugat
     */
    void createFriendsList(ID id);

    void addRequest(ID id1, ID id2);

    Iterable<Request> findRequests(ID id);

    void upRequest(ID id1, ID id2, String message);

    void deleteRequest(ID id1, ID id2);

    Iterable<Message> findAllMessages(ID id1, ID id2);

    void addMessage(ID id1, ID id2, String text);
}
