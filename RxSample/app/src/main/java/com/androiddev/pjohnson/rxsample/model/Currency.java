package com.androiddev.pjohnson.rxsample.model;

/**
 * Created by pjohnson on 12/12/16.
 */

public class Currency {

    private String currency;
    private Double Value;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }
}
