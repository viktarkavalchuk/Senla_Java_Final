package com.senla.course.security.dao;

import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import com.senla.course.security.model.Role;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDao {


    public List getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Role> roles = (List<Role>) session.createQuery("From Role ").list();
        session.close();
        return roles;
    }

    public Role getById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Role where id = :paramName");
        query.setParameter("paramName", id);
        Role roleById = (Role) query.getSingleResult();
        session.close();
        return roleById;
    }
}
