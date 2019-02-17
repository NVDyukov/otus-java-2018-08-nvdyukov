package ru.otus.services;

import org.hibernate.SessionFactory;
import ru.otus.dao.Dao;
import ru.otus.dao.DataSetDao;
import ru.otus.exceptions.DBServiceException;
import ru.otus.model.DataSet;
import ru.otus.util.HibernateSessionFactoryUtil;

public class DBServiceHibernateImpl implements DBService {
    private SessionFactory sessionFactory;


    public DBServiceHibernateImpl() {
        sessionFactory = HibernateSessionFactoryUtil.createSessionFactory();
    }

    @Override
    public <T extends DataSet> void save(T user) {
        try (Dao dao = new DataSetDao(sessionFactory.openSession(), user.getClass())) {
            dao.persist(user);
        } catch (Exception e) {
            throw new DBServiceException(e);
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try (Dao dao = new DataSetDao(sessionFactory.openSession(), clazz)) {
            return (T) dao.findById(id);
        } catch (Exception e) {
            throw new DBServiceException(e);
        }
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }

}
