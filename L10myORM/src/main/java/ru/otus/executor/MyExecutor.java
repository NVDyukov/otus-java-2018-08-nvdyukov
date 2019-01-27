package ru.otus.executor;

import ru.otus.exceptions.ORMException;
import ru.otus.model.DataSet;
import ru.otus.reflect.ClassesHelper;
import ru.otus.reflect.types.ValidClassType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyExecutor implements AutoCloseable {
    private final String SQL_COLUMNS = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='%s' AND " +
            "TABLE_SCHEMA = 'PUBLIC' AND NOT DATA_TYPE = -5";
    private final String SQL_SELECT_BY_ID = "SELECT * FROM %s WHERE ID = %d";
    private final String SQL_INSERT = "INSERT INTO %s(%s) VALUES (%s)";
    private final Connection connection;
    private final String DELIMITER = ",";
    private String tableName = "USERS";

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private Predicate<Field> getFieldPredicate(List<String> columnNames) {
        return e -> {
            int modifiers = e.getModifiers();
            return !Modifier.isTransient(modifiers) && !Modifier.isStatic(modifiers)
                    && ValidClassType.isContainsType(e.getType())
                    && columnNames.stream()
                    .anyMatch(e.getName().toUpperCase()::equals);
        };
    }

    List<String> columnNames(ResultSetMetaData metaData) {
        int columnCount = 0;
        ArrayList<String> names = new ArrayList<>();
        try {
            columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                names.add(metaData.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    List<String> columnNames(String table) {
        ArrayList<String> columnNames = new ArrayList<>();
        executeQuery(String.format(SQL_COLUMNS, table), e -> {
            try {
                while (e.next()) {
                    columnNames.add(e.getString("COLUMN_NAME"));
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });
        return columnNames;
    }

    Map<String, Object> columnValues(List<String> columnNames, ResultSet resultSet) {
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
        Class<? extends DataSet> clazz = user.getClass();
        //clazz.getSimpleName();
        List<String> columnNames = columnNames(tableName);
        Map<String, String> valueFields = ClassesHelper.getClassesFields(user, clazz, getFieldPredicate(columnNames));
        String names = valueFields.keySet()
                .stream()
                .collect(Collectors.joining(DELIMITER));
        String values = valueFields.values()
                .stream()
                .collect(Collectors.joining(DELIMITER));
        String insert = String.format(SQL_INSERT, tableName, names, values);
        createQuery(insert, e -> {
            try {
                e.next();
                user.setId(e.getLong("ID"));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        //clazz.getSimpleName();
        String select = String.format(SQL_SELECT_BY_ID, tableName, id);
        AtomicReference<T> object = new AtomicReference<>();
        executeQuery(select, e -> {
            try {
                if (e.next()) {
                    List<String> names = columnNames(e.getMetaData());
                    Map<String, Object> values = columnValues(names, e);
                    object.set(ClassesHelper.initObject(clazz, getFieldPredicate(names), values));
                } else throw new ORMException("В таблице нет строки с ID = " + String.valueOf(id));

            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException | InstantiationException | ORMException e1) {
                e1.printStackTrace();
            }
        });
        return object.get();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}
