package org.example.model;

import java.math.BigDecimal;

public class ExchangeRate {
    private int id;
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal rate;

    public ExchangeRate(int id, String baseCurrencyId, String targetCurrencyId, BigDecimal rate) {
        this.id = id;
        this.baseCurrencyCode = baseCurrencyId;
        this.targetCurrencyCode = targetCurrencyId;
        this.rate = rate;
    }

    public ExchangeRate() {
    }

    public int getId() {
        return id;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return targetCurrencyCode;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
