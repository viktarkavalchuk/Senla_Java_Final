package com.senla.course.announcementPlatform.dao;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.Comment;
import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class CommentDao extends HibernateAbstractDao<Comment> {

    private static final Logger logger = LogManager.getLogger();

    public CommentDao() {
    }

    @Override
    public List getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Comment> comments = (List<Comment>)session.createQuery("From Comment").list();
        session.close();
        return comments;
    }

    public List<Comment> getByAnnouncement(Announcement announcement) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Comment where announcement = :paramName");
        query.setParameter("paramName", announcement);
        List<Comment> commentByAnnouncement = (List<Comment>) query.getResultList();
        session.close();
        return commentByAnnouncement;
    }

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
