package com.pallavikaushik.Model;

public class MonthlyData {

    private String stockName;
    private String dummyName;
    private float monthlyHigh;
    private float monthlyLow;
    private boolean isHighBroken;
    private boolean isLowBroken;

    public MonthlyData(String stockName, String dummyName, float monthlyHigh, float monthlyLow, boolean isHighBroken, boolean isLowBroken) {
        this.stockName = stockName;
        this.dummyName = dummyName;
        this.monthlyHigh = monthlyHigh;
        this.monthlyLow = monthlyLow;
        this.isHighBroken = isHighBroken;
        this.isLowBroken = isLowBroken;
    }

    public MonthlyData() {
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getDummyName() {
        return dummyName;
    }

    public void setDummyName(String dummyName) {
        this.dummyName = dummyName;
    }

    public float getMonthlyHigh() {
        return monthlyHigh;
    }

    public void setMonthlyHigh(float monthlyHigh) {
        this.monthlyHigh = monthlyHigh;
    }

    public float getMonthlyLow() {
        return monthlyLow;
    }

    public void setMonthlyLow(float monthlyLow) {
        this.monthlyLow = monthlyLow;
    }

    public boolean isHighBroken() {
        return isHighBroken;
    }

    public void setHighBroken(boolean highBroken) {
        isHighBroken = highBroken;
    }

    public boolean isLowBroken() {
        return isLowBroken;
    }

    public void setLowBroken(boolean lowBroken) {
        isLowBroken = lowBroken;
    }
}
