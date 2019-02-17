package ru.otus.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateSessionFactoryUtil {
    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory createSessionFactory() {
        return new Configuration()
                .configure()
                .buildSessionFactory();
    }
}
