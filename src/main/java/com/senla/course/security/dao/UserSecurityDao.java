package com.senla.course.security.dao;

import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserSecurityDao {

    private static final Logger logger  = LogManager.getLogger();
    private final JdbcTemplate jdbcTemplate;
    public static Integer idUserLogin;

    public UserSecurityDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getByUserName(String userName) {
        SessionFactory session = HibernateUtil.getSessionFactory();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        List<User> users = HibernateUtil.getSession().createQuery(criteriaQuery).getResultList();
        Integer id = null;

        for (User user: users) {
            if (user.getLogin().equalsIgnoreCase(userName)){
                id = user.getId();
                idUserLogin = (id);
                break;
            }
        }
        return users.get(id-1);
    }
}