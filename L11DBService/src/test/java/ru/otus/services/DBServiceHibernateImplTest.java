package ru.otus.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.AddressDataSet;
import ru.otus.model.PhoneDataSet;
import ru.otus.model.UserDataSet;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DBServiceHibernateImplTest {
    private DBService dbService;

    @BeforeEach
    void setUp() {
        dbService = new DBServiceHibernateImpl();
    }

    @Test
    public void saveAndLoad() {
        AddressDataSet address = new AddressDataSet("Street");
        UserDataSet user = new UserDataSet("Donald", 72, address);
        Set<PhoneDataSet> phones = Set.of(
                new PhoneDataSet("89506005643"),
                new PhoneDataSet("89506005644")
        );
        user.addPhones(phones);
        dbService.save(user);
        UserDataSet loadUser = dbService.load(user.getId(), UserDataSet.class);
        assertAll("User",
                () -> assertEquals(user, loadUser),
                () -> assertIterableEquals(user.getPhones(), loadUser.getPhones()),
                () -> assertEquals(user.getAddress(), loadUser.getAddress())
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        dbService.close();
    }
}
