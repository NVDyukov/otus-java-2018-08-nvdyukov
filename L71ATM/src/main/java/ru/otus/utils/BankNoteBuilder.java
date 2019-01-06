package ru.otus.utils;

import ru.otus.banknotes.BankNote;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class BankNoteBuilder {
    private static final int MIN = 123_000;
    private static final int MAX = 999_999;
    public static final String CURRENCY_NAME = "Российский рубль";

    private BankNoteBuilder() {
    }

    public static List<BankNote> generateBankNotes(int par, int quantity) {
        ArrayList<BankNote> bankNotes = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            bankNotes.add(new BankNote(par, 1997, rndString(),
                    rnd(MIN, MAX), CURRENCY_NAME));
        }
        return bankNotes;
    }

    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public static String rndString() {
        return UUID.randomUUID().toString()
                .replaceAll("-", "")
                .substring(3, 10);
    }
}
