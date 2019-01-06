package ru.otus.atm;

import ru.otus.banknotes.IBankNote;

import java.util.Arrays;
import java.util.Objects;

public class BankNoteValidator implements IBankNoteValidator<IBankNote> {
    private int[] validDenominationsOfBanknotes;
    private String currencyName = "Российский рубль";

    protected void setValidDenominationsOfBanknotes(int[] validDenominationsOfBanknotes) {
        this.validDenominationsOfBanknotes = validDenominationsOfBanknotes;
        Arrays.sort(this.validDenominationsOfBanknotes);
    }

    public BankNoteValidator(int[] validDenominationsOfBanknotes) {
        Objects.requireNonNull(validDenominationsOfBanknotes);
        setValidDenominationsOfBanknotes(validDenominationsOfBanknotes);
    }

    public BankNoteValidator(int[] validDenominationsOfBanknotes, String currencyName) {
        Objects.requireNonNull(validDenominationsOfBanknotes);
        setValidDenominationsOfBanknotes(validDenominationsOfBanknotes);
        this.currencyName = currencyName;
    }

    @Override
    public boolean checkBankNote(IBankNote bankNote) {
        Objects.requireNonNull(bankNote);
        return bankNote.getCurrencyName().equals(currencyName)
                && Arrays.binarySearch(validDenominationsOfBanknotes, bankNote.getPar()) > -1;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public int[] getValidDenominationsOfBanknotes() {
        return Arrays.copyOf(validDenominationsOfBanknotes, validDenominationsOfBanknotes.length);
    }
}
