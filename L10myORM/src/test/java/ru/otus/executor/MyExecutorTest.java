package ru.otus.executor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.UserDataSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyExecutorTest {
    private MyExecutor myExecutor;
    private String tableName = "USERS";
    private final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS %s(ID BIGINT(20) NOT NULL" +
            " AUTO_INCREMENT, NAME VARCHAR(255), AGE INT(3))";
    private final String URL = "jdbc:h2:~/%s";
    private final String DB = "myorm";

    @BeforeEach
    public void init() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        try {
            Connection connection = DriverManager.getConnection(String.format(URL, DB), "sa", "");
            myExecutor = new MyExecutor(connection);
            myExecutor.setTableName(tableName);
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(String.format(SQL_CREATE, tableName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveLoadTest() {
        UserDataSet userDataSet = new UserDataSet("Donald", 72);
        System.out.println("До сохранения в БД: " + userDataSet);
        myExecutor.save(userDataSet);
        System.out.println("После сохранения в БД: " + userDataSet);
        long expectedId = userDataSet.getId();
        UserDataSet actual = myExecutor.load(expectedId, UserDataSet.class);
        System.out.println("Загружен из БД: " + actual);
        Assertions.assertEquals(userDataSet, actual);
    }

    @AfterEach
    public void deInit() throws IOException {
        try {
            myExecutor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dir = System.getProperty("user.home");
        Files.deleteIfExists(Paths.get(dir + "\\" + DB + ".mv.db"));
    }
}
