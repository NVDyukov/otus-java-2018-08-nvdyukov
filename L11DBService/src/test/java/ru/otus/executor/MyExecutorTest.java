package ru.otus.executor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.UserDataSet;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyExecutorTest {
    private MyExecutor myExecutor;
    private String tableName = UserDataSet.class.getSimpleName();
    private final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS %s(ID BIGINT(20) NOT NULL" +
            " AUTO_INCREMENT, NAME VARCHAR(255), AGE INT(3))";
    private final String URL = "jdbc:h2:~/%s";
    private final String DB = "myorm";

    @BeforeEach
    public void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(String.format(URL, DB), "sa", "");
        myExecutor = new MyExecutor(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(String.format(SQL_CREATE, tableName));
        }
    }

    @Test
    public void saveLoadTest() {
        UserDataSet userDataSet1 = new UserDataSet("Donald", 72);
        System.out.println("До сохранения в БД: " + userDataSet1);
        myExecutor.save(userDataSet1);
        System.out.println("После сохранения в БД: " + userDataSet1);
        long expectedId = userDataSet1.getId();
        UserDataSet actual = myExecutor.load(expectedId, UserDataSet.class);
        System.out.println("Загружен из БД: " + actual);
        Assertions.assertEquals(userDataSet1, actual);
    }

    @AfterEach
    public void deInit() throws Exception {
        myExecutor.close();
        String dir = System.getProperty("user.home");
        Files.deleteIfExists(Paths.get(dir + "\\" + DB + ".mv.db"));
    }
}
