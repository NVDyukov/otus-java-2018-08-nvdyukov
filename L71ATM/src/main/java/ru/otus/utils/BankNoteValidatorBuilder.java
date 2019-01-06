package ru.otus.utils;

import ru.otus.atm.BankNoteValidator;
import ru.otus.atm.IBankNoteValidator;

public final class BankNoteValidatorBuilder {
    public static final int[] DENOMINATIONS_OF_BANKNOTES = {5, 10, 50, 100, 200, 500, 1_000, 2_000, 5_000};

    private BankNoteValidatorBuilder() {
    }

    public static IBankNoteValidator generateValidator() {
        return new BankNoteValidator(DENOMINATIONS_OF_BANKNOTES);
    }
}
