package com.androiddev.pjohnson.rxsample.networking.responses;

import java.util.HashMap;

/**
 * Created by pjohnson on 12/12/16.
 */
public class ExchangeResponse {

    private String base;
    private String date;
    private HashMap<String, Double> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }
}
