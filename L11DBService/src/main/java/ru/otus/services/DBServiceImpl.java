package ru.otus.services;

import ru.otus.exceptions.DBServiceException;
import ru.otus.executor.MyExecutor;
import ru.otus.model.DataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {
    private MyExecutor myExecutor;
    private final String DRIVER = "org.h2.Driver";

    public DBServiceImpl(String url, String user, String password) {
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(url, user, password);
            myExecutor = new MyExecutor(connection);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new DBServiceException(ex);
        }
    }

    @Override
    public <T extends DataSet> void save(T user) {
        myExecutor.save(user);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return myExecutor.load(id, clazz);
    }

    @Override
    public void close() throws Exception {
        myExecutor.close();
    }
}
