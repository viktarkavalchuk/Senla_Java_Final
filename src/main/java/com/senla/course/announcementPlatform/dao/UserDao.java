package com.senla.course.announcementPlatform.dao;

import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao extends HibernateAbstractDao<User> {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    UserDao userDao;

    @Override
    public List getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = (List<User>)session.createQuery("From User").list();
        session.close();
        return users;
    }

    @Override
    public User getById(Integer id) {
        List<User> users = userDao.getAll();
        return users.get(id-1);
    }

    @Override
    public void update(User entity) {
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            session.close();
        } catch (Exception e) {
            tx.rollback();
            logger.error("Updating error " + e);
        }
    }

    @Override
    public void delete(User entity) {
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
            session.close();
        } catch (Exception e) {
            tx.rollback();
            logger.error("Deletion error " + e);
        }
    }

    @Override
    public void create(User entity) {
        Transaction tx = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            session.close();
        } catch (Exception e) {
            tx.rollback();
            logger.error("Creation error" + e);
        }
        finally {
            session.close();
        }
    }
}
