package ru.otus.atm;

public interface IBankNoteValidator<T> {
    public int[] getValidDenominationsOfBanknotes();

    public String getCurrencyName();

    public boolean checkBankNote(T bankNote);
}
