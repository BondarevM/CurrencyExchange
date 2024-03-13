package by.mikhail.currencyexchange.entity;

import java.math.BigDecimal;

public class ExchangeRate {
    private Integer id;
    private Currency baseCurrency;
    private Currency tagretCurrency;
    private BigDecimal rate;

    public ExchangeRate(Integer id, Currency baseCurrency, Currency tagretCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.tagretCurrency = tagretCurrency;
        this.rate = rate;
    }
    public ExchangeRate(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getTagretCurrency() {
        return tagretCurrency;
    }

    public void setTagretCurrency(Currency tagretCurrency) {
        this.tagretCurrency = tagretCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
               "id=" + id +
               ", baseCurrency=" + baseCurrency +
               ", tagretCurrency=" + tagretCurrency +
               ", rate=" + rate +
               '}';
    }
}
