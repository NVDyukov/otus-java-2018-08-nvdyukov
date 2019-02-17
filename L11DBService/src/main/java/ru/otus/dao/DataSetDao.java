package ru.otus.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.otus.model.DataSet;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class DataSetDao<T extends DataSet, K extends Serializable> implements Dao<T, K> {
    private Session session;
    private Class<T> clazz;

    public DataSetDao(Session session, Class<T> clazz) {
        Objects.requireNonNull(session);
        Objects.requireNonNull(clazz);
        this.session = session;
        this.clazz = clazz;
    }

    private <R> R runInSession(Supplier<R> function) {
        Transaction transaction = session.beginTransaction();
        R result = function.get();
        transaction.commit();
        return result;
    }

    @Override
    public void persist(T entity) {
        runInSession(() -> (K) session.save(entity));
    }

    @Override
    public void update(T entity) {
        runInSession(() -> {
            session.update(entity);
            return null;
        });
    }

    @Override
    public T findById(K id) {
        return runInSession(() -> session.get(clazz, id));
    }

    @Override
    public void delete(T entity) {
        runInSession(() -> {
            session.delete(entity);
            return null;
        });
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> query = session.getCriteriaBuilder().createQuery(clazz);
        query.from(clazz);
        return session.createQuery(query).list();
    }

    @Override
    public void deleteAll() {
        List<T> all = findAll();
        all.forEach(entity -> delete(entity));
    }

    @Override
    public void close() throws Exception {
        session.close();
    }

}
