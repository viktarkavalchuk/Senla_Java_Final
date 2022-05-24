package com.senla.course.announcementPlatform.dao;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.utils.configuration.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.List;

@Component
public class AnnouncementDao extends HibernateAbstractDao<Announcement> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public List getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Announcement> announcements = (List<Announcement>)session.createQuery("From Announcement ").list();
        session.close();
        return announcements;
    }

    @Override
    public Announcement getById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Announcement where id = :paramName");
        query.setParameter("paramName", id);
        Announcement announcementById = (Announcement) query.getSingleResult();
        session.close();
        return announcementById;
    }

    @Override
    public void update(Announcement entity) {
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
    public void delete(Announcement entity) {
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
    public void create(Announcement entity) {
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
    public List<Announcement> getVipAnnouncements() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Announcement> announcement = (List<Announcement>) session.createQuery("from Announcement where vip = true and sold = false").list();
        session.close();
        return announcement;
    }
    public List<Announcement> getNotVipAnnouncements() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Announcement> announcement = (List<Announcement>) session.createQuery("from Announcement where vip = false and sold = false").list();
        session.close();
        return announcement;
    }
    public List<Announcement> getClosedAnnouncements(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Announcement> announcement = session.createQuery("from Announcement where sold = true").list();
        session.close();
        return announcement;
    }
}
