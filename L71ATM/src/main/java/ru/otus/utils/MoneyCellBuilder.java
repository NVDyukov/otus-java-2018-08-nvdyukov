package ru.otus.utils;

import ru.otus.atm.IMoneyCell;
import ru.otus.atm.MoneyCell;
import ru.otus.banknotes.BankNote;
import ru.otus.banknotes.IBankNote;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MoneyCellBuilder {
    private MoneyCellBuilder() {
    }

    public static IMoneyCell<IBankNote> generateMoneyCell(int par, int amountOfBanknotes) {
        List<BankNote> bankNotes = BankNoteBuilder.generateBankNotes(par, amountOfBanknotes);
        MoneyCell moneyCell = new MoneyCell(par, BankNoteBuilder.CURRENCY_NAME);
        bankNotes.stream()
                .forEach(e -> moneyCell.addBankNote(e));
        return moneyCell;
    }

    public static List<IMoneyCell<IBankNote>> generateMoneyCells(int[] pars, int[] amountOfBanknotes) {
        Objects.requireNonNull(pars);
        Objects.requireNonNull(amountOfBanknotes);
        int len = pars.length;
        if (len != amountOfBanknotes.length) throw new RuntimeException();
        List<IMoneyCell<IBankNote>> list = new ArrayList<>(len);
        for (int i = 0; i < pars.length; i++) {
            list.add(generateMoneyCell(pars[i], amountOfBanknotes[i]));
        }
        return list;
    }
}
