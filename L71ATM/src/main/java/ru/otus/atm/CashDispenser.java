package ru.otus.atm;

import ru.otus.banknotes.IBankNote;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CashDispenser implements IAutomatedTellerMachineForClient<IBankNote>,
        IAutomatedTellerMachineForService<IMoneyCell<IBankNote>> {
    private List<IMoneyCell<IBankNote>> moneyCells;
    private IBankNoteValidator<IBankNote> bankNoteValidator;
    private BankNoteCalculator bankNoteCalculator = new BankNoteCalculator();
    private int maximumNumberOfMoneyCells = 9;

    protected void sortCells() {
        moneyCells.sort(Comparator
                .comparingInt(IMoneyCell<IBankNote>::getPar)
                .reversed());
    }

    protected int totalAmountOfMoney() {
        return moneyCells.stream()
                .mapToInt(e -> e.totalAmountOfMoney())
                .sum();
    }

    protected void initMoneyCells() {
        moneyCells = new ArrayList<>(this.maximumNumberOfMoneyCells);
    }

    protected class BankNoteCalculator {
        private List<Integer> amountOfBanknotes = new ArrayList<>(maximumNumberOfMoneyCells);

        private int residualAmount(int sum) {
            int residualAmount = sum;
            for (int i = 0; i < moneyCells.size(); i++) {
                IMoneyCell<IBankNote> cell = moneyCells.get(i);
                int amountOfBanknotesBySum = cell.getAmountOfBanknotesBySum(residualAmount);
                amountOfBanknotes.add(amountOfBanknotesBySum);
                residualAmount -= amountOfBanknotesBySum * cell.getPar();
                if (residualAmount <= 0) break;
            }
            return residualAmount;
        }

        private void checkSum(int sum) throws ATMException {
            if (sum <= 0)
                throw new ATMException("Сумма должна быть больше нуля");
        }

        public List<Integer> countNumberBankNotes(int sum) throws ATMException {
            checkSum(sum);
            amountOfBanknotes.clear();
            int balance = residualAmount(sum);
            if (balance != 0) {
                amountOfBanknotes.clear();
                throw new ATMException("Запрошенную сумму нельзя выдать");
            }
            return new ArrayList<>(amountOfBanknotes);
        }

    }

    public CashDispenser(IBankNoteValidator<IBankNote> bankNoteValidator) {
        Objects.requireNonNull(bankNoteValidator);
        this.bankNoteValidator = bankNoteValidator;
        initMoneyCells();
    }

    public CashDispenser(IBankNoteValidator<IBankNote> bankNoteValidator, int maximumNumberOfMoneyCells) {
        Objects.requireNonNull(bankNoteValidator);
        this.bankNoteValidator = bankNoteValidator;
        this.maximumNumberOfMoneyCells = maximumNumberOfMoneyCells;
        initMoneyCells();
    }

    @Override
    public boolean addBankNote(IBankNote bankNote) {
        List<IMoneyCell<IBankNote>> moneyCells = this.moneyCells.parallelStream()
                .filter(e -> (e.getPar() == bankNote.getPar() && e.isNotFull()))
                .collect(Collectors.toList());
        return moneyCells.size() > 0 && moneyCells.get(0).addBankNote(bankNote);
    }

    @Override
    public IBankNote[] giveMoney(int sum) throws ATMException {
        sortCells();
        ArrayList<IBankNote> list = new ArrayList<>();
        List<Integer> countNumberBankNotes = bankNoteCalculator.countNumberBankNotes(sum);
        int size = countNumberBankNotes.size();
        int length = 0;
        int count = 0;
        for (int i = 0; i < size; i++) {
            count = countNumberBankNotes.get(i);
            list.addAll(moneyCells
                    .get(i)
                    .getBankNotes(count));
            length += count;
        }
        return (IBankNote[]) list.toArray(new IBankNote[length]);
    }

    @Override
    public int balanceOfMoney() {
        return totalAmountOfMoney();
    }

    @Override
    public int getMaximumNumberOfMoneyCells() {
        return maximumNumberOfMoneyCells;
    }

    @Override
    public boolean addMoneyCell(IMoneyCell<IBankNote> cell) {
        Objects.requireNonNull(cell);
        moneyCells.add(cell);
        return true;
    }

    @Override
    public boolean addMoneyCells(List<IMoneyCell<IBankNote>> cells) {
        Objects.requireNonNull(cells);
        moneyCells.addAll(cells);
        return true;
    }
}
