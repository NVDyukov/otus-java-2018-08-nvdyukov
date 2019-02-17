package ru.otus.services;

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

public class DBServiceImplTest {
    private DBService dbService;
    private String tableName = UserDataSet.class.getSimpleName();
    private final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS %s(ID BIGINT(20) NOT NULL" +
            " AUTO_INCREMENT, NAME VARCHAR(255), AGE INT(3))";
    private final String URL = "jdbc:h2:~/myorm";
    private static final String DB = "myorm";
    private final String USER = "sa";
    private final String PASSWORD = "";
    private static String path = System.getProperty("user.home") + "\\" + DB + ".mv.db";


    @BeforeEach
    void setUp() throws SQLException, IOException {
        Files.deleteIfExists(Paths.get(path));
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {
            stmt.execute(String.format(SQL_CREATE, tableName));
        }
        dbService = new DBServiceImpl(URL, USER, PASSWORD);
    }

    @Test
    void saveAndLoad() {
        UserDataSet userDataSet1 = new UserDataSet("Donald", 72);
        System.out.println("До сохранения в БД: " + userDataSet1);
        dbService.save(userDataSet1);
        System.out.println("После сохранения в БД: " + userDataSet1);
        long expectedId = userDataSet1.getId();
        UserDataSet actual = dbService.load(expectedId, UserDataSet.class);
        System.out.println("Загружен из БД: " + actual);
        Assertions.assertEquals(userDataSet1, actual);
    }

    @AfterEach
    void tearDown() throws Exception {
        dbService.close();
        Files.deleteIfExists(Paths.get(path));
    }
}
