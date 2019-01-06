package ru.otus.atm;

import ru.otus.banknotes.IBankNote;

import java.util.List;

public interface IAutomatedTellerMachineForService<T> {
    public int getMaximumNumberOfMoneyCells();

    public boolean addMoneyCell(IMoneyCell<IBankNote> cell);

    public boolean addMoneyCells(List<IMoneyCell<IBankNote>> cells);
}
