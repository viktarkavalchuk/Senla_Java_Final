package com.senla.course.security.repository;


import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import com.senla.course.security.model.UserSecurity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class MyRepository {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public UserSecurity getByUserName(String userName) {
        SessionFactory session = HibernateUtil.getSessionFactory();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserSecurity> criteriaQuery = cb.createQuery(UserSecurity.class);
        Root<UserSecurity> root = criteriaQuery.from(UserSecurity.class);
        criteriaQuery.select(root);

        List<UserSecurity> users = HibernateUtil.getSession().createQuery(criteriaQuery).getResultList();
        Integer id = null;

        for (UserSecurity user: users) {
            if (user.getLogin().equalsIgnoreCase(userName)){
                id = user.getId();
                break;
            }
        }

        return users.get(id-1);
    }
}