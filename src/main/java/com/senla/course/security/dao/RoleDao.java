package com.senla.course.security.dao;

import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import com.senla.course.security.model.Role;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao {

    @Autowired
    private RoleDao role;

    public List getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Role> roles = (List<Role>)session.createQuery("From Role ").list();
        session.close();
        return roles;
    }

    public Role getById(Integer id) {
        List<Role> roles = role.getAll();
        Role roleById = null;
        for (Role role: roles) {
            if (role.getId() == id){
                roleById = role;
            }
        }
        return roleById;
    }
}
