package com.androiddev.pjohnson.rxsample.networking.responses;

import java.util.List;

/**
 * Created by pjohnson on 12/12/16.
 */

public class ExchangeResponse {

    private String base;
    private String date;
    private List<String> rates;

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

    public List<String> getRates() {
        return rates;
    }

    public void setRates(List<String> rates) {
        this.rates = rates;
    }
}
