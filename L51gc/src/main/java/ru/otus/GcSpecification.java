package ru.otus;

public class GcSpecification {
    private String gcName;
    private volatile long countOfGc = 0;
    private volatile long amountOfTime = 0;

    public GcSpecification(String gcName) {
        this.gcName = gcName;
    }

    public String getGcName() {
        return gcName;
    }

    public void setGcName(String gcName) {
        this.gcName = gcName;
    }

    public long getCountOfGc() {
        return countOfGc;
    }

    public void setCountOfGc(long countOfGc) {
        this.countOfGc = countOfGc;
    }

    public long getAmountOfTime() {
        return amountOfTime;
    }

    public void setAmountOfTime(long amountOfTime) {
        this.amountOfTime = amountOfTime;
    }

    public void toZero() {
        amountOfTime = 0;
        countOfGc = 0;
    }

    @Override
    public String toString() {
        return "Сборщик мусора: " + gcName +
                ", кол-во итераций сборок мусора: " + countOfGc +
                ", общее время сборки мусора: " + amountOfTime + " ms";
    }
}
