package ru.otus.atm;

import java.util.List;
import java.util.Optional;

public interface IMoneyCell<T> {
    public int getPar();

    public String getCurrencyName();

    public int getCurrentAmountOfBankNotes();

    public int getMaxAmountOfBankNotes();

    public boolean addBankNote(T bankNote);

    public Optional<T> getBankNote();

    public List<T> getBankNotes(int quantity);

    public boolean isNotFull();

    public int totalAmountOfMoney();

    public int getAmountOfBanknotesBySum(int sum);

}
