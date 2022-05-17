package com.senla.course.announcementPlatform.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao <E extends Serializable>{
    public List<E> getAll();
    public E getById(Integer id);
    public void update(E entity);
    public void delete(E entity);
    public void create(E entity);
}
