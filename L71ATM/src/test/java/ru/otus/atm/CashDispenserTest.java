package ru.otus.atm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.banknotes.IBankNote;
import ru.otus.utils.BankNoteValidatorBuilder;
import ru.otus.utils.MoneyCellBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CashDispenserTest {
    private CashDispenser cashDispenser;
    private final int[] amountOfBanknotes = {20, 10, 10, 5, 4, 5, 5, 10, 15};
    private final int[] expectedPositive = {1, 1, 1, 1, 0, 0, 1, 1, 12}; // 63_175
    private final int[] expectedNegative = {1, 1, 1, 1, 0, 0, 10, 1, 20}; // 93_175
    private List<IMoneyCell<IBankNote>> moneyCells;

    private int sum(int[] amounts, int[] denominations) {
        int sum = 0;
        for (int i = 0; i < amounts.length; i++) {
            sum += amounts[i] * denominations[i];
        }
        return sum;
    }

    private int[] toPrimitive(Integer[] integers) {
        int[] res = new int[integers.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = integers[i].intValue();
        }
        return res;
    }

    private Map<Integer, Integer> fill(int[] denominations) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < denominations.length; i++) {
            map.put(denominations[i], 0);
        }
        return map;
    }

    private int[] numberOfEntries(IBankNote[] bankNotes, int[] denominations) {
        Map<Integer, Integer> map = fill(denominations);
        Integer key;
        for (int i = 0; i < bankNotes.length; i++) {
            key = bankNotes[i].getPar();
            map.put(key, map.get(key) + 1);
        }
        return toPrimitive(map.values().toArray(new Integer[denominations.length]));
    }

    @BeforeEach
    public void init() {
        cashDispenser = new CashDispenser(BankNoteValidatorBuilder.generateValidator());
        moneyCells = MoneyCellBuilder.generateMoneyCells(
                BankNoteValidatorBuilder.DENOMINATIONS_OF_BANKNOTES, amountOfBanknotes);
        cashDispenser.addMoneyCells(moneyCells);
    }

    @Test
    void totalAmountOfMoneyTest() {
        Assertions.assertEquals(sum(amountOfBanknotes, BankNoteValidatorBuilder.DENOMINATIONS_OF_BANKNOTES),
                cashDispenser.totalAmountOfMoney());
    }

    @Test
    void giveMoneyPositiveTest() throws ATMException {
        IBankNote[] bankNotes = cashDispenser.giveMoney(sum(expectedPositive,
                BankNoteValidatorBuilder.DENOMINATIONS_OF_BANKNOTES));
        int[] actual = numberOfEntries(bankNotes, BankNoteValidatorBuilder.DENOMINATIONS_OF_BANKNOTES);
        Assertions.assertArrayEquals(expectedPositive, actual);
    }

    @Test
    void giveMoneyNegativeTest() {
        Assertions.assertThrows(ATMException.class, () -> cashDispenser.giveMoney(sum(expectedNegative,
                BankNoteValidatorBuilder.DENOMINATIONS_OF_BANKNOTES)));
    }
}
