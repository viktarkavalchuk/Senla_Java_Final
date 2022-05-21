package com.senla.course.announcementPlatform.dao;

import com.senla.course.announcementPlatform.dao.interfaces.Dao;

import java.io.Serializable;
import java.util.List;

public class HibernateAbstractDao<E extends Serializable> implements Dao<E> {
    @Override
    public List<E> getAll() {
        return null;
    }

    @Override
    public E getById(Integer id) {
        return null;
    }

    @Override
    public void update(E entity) {

    }

    @Override
    public void delete(E entity) {

    }

    @Override
    public void create(E entity) {

    }
}
