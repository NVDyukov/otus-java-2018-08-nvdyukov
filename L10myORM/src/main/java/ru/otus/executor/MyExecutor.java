package ru.otus.executor;

import org.ehcache.Cache;
import ru.otus.exceptions.ORMException;
import ru.otus.model.DataSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

import static ru.otus.reflect.ClassesHelper.*;

public class MyExecutor implements AutoCloseable {
    private final String SQL_SELECT_BY_ID = "SELECT %s FROM %s WHERE ID = %d";
    private final String SQL_INSERT = "INSERT INTO %s(%s) VALUES (%s)";
    private final Connection connection;
    private final String DELIMITER = ",";
    private CacheBuilder cacheBuilder;
    private Cache<Class, Map> cache;

    private String getNames(Map<String, Field> valueFields) {
        return valueFields.keySet()
                .stream()
                .collect(Collectors.joining(DELIMITER));
    }

    private Map<String, Field> getStringFieldMap(Class<?> clazz) {
        Map<String, Field> map;
        if (cache.containsKey(clazz)) {
            map = cache.get(clazz);
        } else {
            map = getClassesFields(clazz, filterField());
            cache.put(clazz, map);
        }
        return map;
    }


    private Optional<Map.Entry<String, Field>> getStringFieldEntry(Map<String, Field> classesFields) {
        return classesFields.entrySet()
                .parallelStream()
                .filter((entry) -> isAnnotationIdPresent(entry.getValue()))
                .findFirst();
    }

    Map<String, Object> columnValues(Set<String> columnNames, ResultSet resultSet) {
        HashMap<String, Object> columnValues = new HashMap<>();
        for (String name : columnNames) {
            try {
                columnValues.put(name, resultSet.getObject(name));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return columnValues;
    }

    public MyExecutor(Connection connection) {
        Objects.requireNonNull(connection);
        this.connection = connection;
        this.cacheBuilder = new CacheBuilder();
        this.cacheBuilder.setEntries(2);
        this.cache = cacheBuilder.build("reflection", Class.class, HashMap.class);
    }

    public void executeQuery(String query, ResultSetHandler resultSetHandler) {
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            resultSetHandler.process(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createQuery(String query, ResultSetHandler resultSetHandler) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
            resultSetHandler.process(stmt.getGeneratedKeys());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuery(String query) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSet> void save(T user) {
        Class<?> clazz = user.getClass();
        String tableName = clazz.getSimpleName();
        Map<String, Field> classesFields = getStringFieldMap(clazz);
        Optional<Map.Entry<String, Field>> idField = getStringFieldEntry(classesFields);
        classesFields = classesFields.entrySet()
                .parallelStream()
                .filter((entry) -> !isAnnotationIdPresent(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        String names = getNames(classesFields);
        String values = convertToMapValueFields(user, classesFields).values()
                .stream()
                .collect(Collectors.joining(DELIMITER));
        String insert = String.format(SQL_INSERT, tableName, names, values);
        createQuery(insert, e ->
        {
            try {
                e.next();
                if (idField.isPresent()) {
                    Field value = idField.get().getValue();
                    value.setAccessible(true);
                    value.set(user, e.getLong(idField.get().getKey()));
                }
            } catch (SQLException | IllegalAccessException ex) {
                throw new ORMException(ex);
            }
        });
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        String tableName = clazz.getSimpleName();
        Map<String, Field> classesFields = getStringFieldMap(clazz);
        String names = getNames(classesFields);
        String select = String.format(SQL_SELECT_BY_ID, names, tableName, id);
        var ref = new Object() {
            Map<String, Object> map = null;
        };
        executeQuery(select, e -> {
            try {
                if (e.next()) {
                    ref.map = columnValues(classesFields.keySet(), e);
                } else throw new ORMException("Не удалось найти запись");
            } catch (SQLException ex) {
                throw new ORMException(ex);
            }
        });
        T object;
        try {
            object = initObject(clazz, classesFields, ref.map);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | InstantiationException ex) {
            throw new ORMException(ex);
        }
        return object;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
        cacheBuilder.close();
    }
}
