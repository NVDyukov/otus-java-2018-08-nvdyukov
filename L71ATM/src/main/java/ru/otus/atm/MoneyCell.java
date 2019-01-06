package ru.otus.atm;

import ru.otus.banknotes.IBankNote;

import java.util.*;

public class MoneyCell implements IMoneyCell<IBankNote> {
    private String id = UUID.randomUUID().toString();
    private int maxAmountOfBankNotes = 500;
    private int par;
    private String currencyName;
    private ArrayDeque<IBankNote> bankNotes;

    protected void setBankNotes() {
        this.bankNotes = new ArrayDeque<>(this.maxAmountOfBankNotes);
    }

    public MoneyCell(int par, String currencyName) {
        if (par <= 0) throw new IllegalArgumentException();
        this.par = par;
        this.currencyName = currencyName;
        setBankNotes();
    }

    public MoneyCell(int maxAmountOfBankNotes, int par, String currencyName) {
        this.maxAmountOfBankNotes = maxAmountOfBankNotes;
        this.par = par;
        this.currencyName = currencyName;
        setBankNotes();
    }

    @Override
    public int getPar() {
        return par;
    }

    @Override
    public String getCurrencyName() {
        return currencyName;
    }

    @Override
    public int getCurrentAmountOfBankNotes() {
        return bankNotes.size();
    }

    @Override
    public int getMaxAmountOfBankNotes() {
        return maxAmountOfBankNotes;
    }

    @Override
    public boolean addBankNote(IBankNote bankNote) {
        Objects.requireNonNull(bankNote);
        return isNotFull() && bankNotes.add(bankNote);
    }

    @Override
    public Optional<IBankNote> getBankNote() {
        return Optional.of(bankNotes.pollLast());
    }

    @Override
    public List<IBankNote> getBankNotes(int quantity) {
        int size = bankNotes.size();
        if (quantity > size)
            throw new IndexOutOfBoundsException(String.format("Количество банкнот %d меньше, чем quantity %d", size, quantity));
        ArrayList<IBankNote> list = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            list.add(bankNotes.pollLast());
        }
        return list;
    }

    @Override
    public boolean isNotFull() {
        return getCurrentAmountOfBankNotes() < maxAmountOfBankNotes;
    }

    @Override
    public int totalAmountOfMoney() {
        return par * getCurrentAmountOfBankNotes();
    }

    @Override
    public int getAmountOfBanknotesBySum(int sum) {
        int amount = sum / par;
        int size = getCurrentAmountOfBankNotes();
        return size >= amount ? amount : size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyCell moneyCell = (MoneyCell) o;
        return Objects.equals(id, moneyCell.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
