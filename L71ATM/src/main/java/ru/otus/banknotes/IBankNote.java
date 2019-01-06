package ru.otus.banknotes;

public interface IBankNote {
    /**
     * Номинал банкноты
     *
     * @return
     */
    public int getPar();

    /**
     * Год выпуска
     *
     * @return
     */
    public int getYearOfCreation();

    /**
     * Серия
     *
     * @return
     */
    public String getSeries();

    /**
     * Номер
     *
     * @return
     */
    public int getNumber();

    /**
     * Название валюты
     *
     * @return
     */
    public String getCurrencyName();

}
