package ru.otus;

import ru.otus.atm.ATMException;
import ru.otus.atm.CashDispenser;
import ru.otus.atm.IMoneyCell;
import ru.otus.banknotes.IBankNote;
import ru.otus.utils.BankNoteValidatorBuilder;
import ru.otus.utils.MoneyCellBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int[] amountOfBanknotes = {10, 10, 10, 10, 10, 10, 10, 10, 10};

    public static void main(String[] args) {
        CashDispenser cashDispenser = init();
        int q = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            printInit();
            q = scanner.nextInt();
            switch (q) {
                case 1:
                    System.out.println("Введите сумму: ");
                    int sum = scanner.nextInt();
                    try {
                        IBankNote[] bankNotes = cashDispenser.giveMoney(sum);
                        System.out.println("Выдача денег...");
                        Arrays.stream(bankNotes)
                                .forEach(e -> System.out.println(e));
                        System.out.println();
                    } catch (ATMException e) {
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.println("Cумма остатка денежных средств = " + cashDispenser.balanceOfMoney());
            }
        } while (q != 3);

    }

    public static CashDispenser init() {
        CashDispenser cashDispenser = new CashDispenser(BankNoteValidatorBuilder.generateValidator());
        List<IMoneyCell<IBankNote>> moneyCells = MoneyCellBuilder.generateMoneyCells(
                BankNoteValidatorBuilder.DENOMINATIONS_OF_BANKNOTES, amountOfBanknotes);
        cashDispenser.addMoneyCells(moneyCells);
        return cashDispenser;
    }

    public static void printInit() {
        System.out.println("Выберите действие: ");
        System.out.println("1: Получить сумму");
        System.out.println("2: Узнать сумму остатка денежных средств");
        System.out.println("3: Завершить работу");
    }
}
