package com.senla.course.announcementPlatform.dao;

import com.senla.course.announcementPlatform.model.Rating;
import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RatingDao extends HibernateAbstractDao<Rating> {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    RatingDao ratingDao;

    @Override
    public List getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Rating> ratings = (List<Rating>)session.createQuery("From Rating").list();
        session.close();
        return ratings;
    }

    @Override
    public Rating getById(Integer id) {
        List<Rating> ratings = ratingDao.getAll();
        Rating ratingById = null;
        for (Rating rating: ratings){
            if (rating.getId() == id) {
                ratingById = rating;
            }
        }
        return ratingById;
    }

    @Override
    public void update(Rating entity) {
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
    public void delete(Rating entity) {
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
    public void create(Rating entity) {
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
