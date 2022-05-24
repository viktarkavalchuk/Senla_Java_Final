package com.senla.course.announcementPlatform.dao;

import com.senla.course.announcementPlatform.model.Comment;
import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class CommentDao extends HibernateAbstractDao<Comment> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public List getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Comment> comments = (List<Comment>)session.createQuery("From Comment").list();
        session.close();
        return comments;
    }

    @Override
    public Comment getById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Comment where id = :paramName");
        query.setParameter("paramName", id);
        Comment commentById = (Comment) query.getSingleResult();
        session.close();
        return commentById;
    }

    @Override
    public void update(Comment entity) {
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
    public void delete(Comment entity) {
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
    public void create(Comment entity) {
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
