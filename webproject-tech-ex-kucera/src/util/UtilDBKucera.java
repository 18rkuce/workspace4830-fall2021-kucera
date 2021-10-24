/**
 */
package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import datamodel.AppointmentKucera;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @since JavaSE-1.8
 */
public class UtilDBKucera {
   static SessionFactory sessionFactory = null;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory != null) {
         return sessionFactory;
      }
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
   }

   public static List<AppointmentKucera> listAppointments() {
      List<AppointmentKucera> resultList = new ArrayList<AppointmentKucera>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;  // each process needs transaction and commit the changes in DB.

      try {
         tx = session.beginTransaction();
         List<?> appointments = session.createQuery("FROM AppointmentKucera").list();
         for (Iterator<?> iterator = appointments.iterator(); iterator.hasNext();) {
            AppointmentKucera appointment = (AppointmentKucera) iterator.next();
            resultList.add(appointment);
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static List<AppointmentKucera> listAppointments(String keyword) {
      List<AppointmentKucera> resultList = new ArrayList<AppointmentKucera>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         System.out.println((AppointmentKucera)session.get(AppointmentKucera.class, 1)); // use "get" to fetch data
        // Query q = session.createQuery("FROM Employee");
         List<?> appointments = session.createQuery("FROM AppointmentKucera").list();
         for (Iterator<?> iterator = appointments.iterator(); iterator.hasNext();) {
            AppointmentKucera appointment = (AppointmentKucera) iterator.next();
            if (appointment.getDate().startsWith(keyword)) {
               resultList.add(appointment);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static void createAppointments(String DATE, String TIME, String LOCATION, String DESCRIPTION) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(new AppointmentKucera(DATE, TIME, LOCATION, DESCRIPTION));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
}
