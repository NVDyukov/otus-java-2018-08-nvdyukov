package ru.otus.banknotes;

import java.util.Objects;

public class BankNote implements IBankNote {
    private int par;
    private int yearOfCreation;
    private String series;
    private int number;
    private String currencyName;

    public BankNote(int par, int yearOfCreation, String series, int number, String currencyName) {
        if (par <= 0) throw new IllegalArgumentException();
        this.par = par;
        this.yearOfCreation = yearOfCreation;
        this.series = series;
        this.number = number;
        this.currencyName = currencyName;
    }

    @Override
    public int getPar() {
        return par;
    }

    @Override
    public int getYearOfCreation() {
        return yearOfCreation;
    }

    @Override
    public String getSeries() {
        return series;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public String getCurrencyName() {
        return currencyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankNote bankNote = (BankNote) o;
        return par == bankNote.par &&
                yearOfCreation == bankNote.yearOfCreation &&
                number == bankNote.number &&
                Objects.equals(series, bankNote.series) &&
                Objects.equals(currencyName, bankNote.currencyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(par, yearOfCreation, series, number, currencyName);
    }

    @Override
    public String toString() {
        return "BankNote{" +
                "par=" + par +
                ", yearOfCreation=" + yearOfCreation +
                ", series='" + series + '\'' +
                ", number=" + number +
                ", currencyName='" + currencyName + '\'' +
                '}';
    }
}
