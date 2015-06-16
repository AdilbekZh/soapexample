package com.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by HP on 16.06.2015.
 */
public class HibernateUtils {

    private final static Logger logger = LoggerFactory.getLogger(HibernateUtils.class);

    private static final SessionFactory sessionFactory;

    static {
        try {

            Configuration conf = new Configuration();
            conf.configure("hibernate.cfg.xml");
            sessionFactory = conf.buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("buildSessionFactory exception {}", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}