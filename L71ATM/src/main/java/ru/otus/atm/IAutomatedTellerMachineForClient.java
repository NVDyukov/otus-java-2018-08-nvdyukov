package ru.otus.atm;

public interface IAutomatedTellerMachineForClient<T> {
    public boolean addBankNote(T bankNote);

    public T[] giveMoney(int sum) throws ATMException;

    public int balanceOfMoney();
}
