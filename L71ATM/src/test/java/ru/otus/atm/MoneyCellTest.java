package ru.otus.atm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.banknotes.BankNote;
import ru.otus.banknotes.IBankNote;
import ru.otus.utils.BankNoteBuilder;

import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MoneyCellTest {
    private final int PAR = 100;
    private final int QUANTITY = 4;
    private IMoneyCell<IBankNote> cell = new MoneyCell(PAR, "Российский рубль");
    private List<BankNote> bankNotes = BankNoteBuilder.generateBankNotes(PAR, QUANTITY);


    @BeforeAll
    public void init() {
        bankNotes.forEach(b -> cell.addBankNote(b));
    }

    @Test
    public void getBankNotesTest() {
        Collections.reverse(this.bankNotes);
        List<IBankNote> bankNotes = cell.getBankNotes(this.bankNotes.size());
        Assertions.assertEquals(this.bankNotes, bankNotes);
    }
}
