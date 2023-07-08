package com.example.retea_gui.repository;

import com.example.retea_gui.domain.Entity;
import com.example.retea_gui.domain.validators.ValidationException;

import java.util.Map;

/**
 * operatiile CRUD ale repository-ului
 * @param <ID> - tipul E trebuie sa aiba un atribut de tipul ID
 * @param <E> -  tipul entitatilor salvate in repository
 */
public interface Repository<ID, E extends Entity<ID>> {
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

    /**
     *
     * @param entity
     *          entitatea de adaugat
     * @return null- daca exista entitate cu acest id
     *         altfel returneaza entitatea (id-ul deja exista)
     * @throws ValidationException
     *            daca entitatea nu este valida
     * @throws IllegalArgumentException
     *            daca entitatea este null
     */
    E save(E entity);


    E update(E entity);


    /**
     *  sterge entitatea cu id-ul specificat
     * @param id
     *      id-ul entitatii de sters
     * @return entitatea stearsa sau null daca nu exista entitate cu acest id
     * @throws IllegalArgumentException
     *        daca id-ul este null
     */
    E delete(ID id);
    Map<ID, E> map();

}
