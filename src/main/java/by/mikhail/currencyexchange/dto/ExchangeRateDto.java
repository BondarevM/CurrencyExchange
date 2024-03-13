package by.mikhail.currencyexchange.dto;

import by.mikhail.currencyexchange.entity.Currency;

import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeRateDto {
    private Integer id;
    private Currency baseCurrency;
    private Currency tagretCurrency;
    private BigDecimal rate;

    public ExchangeRateDto(Integer id, Currency baseCurrency, Currency tagretCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.tagretCurrency = tagretCurrency;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTagretCurrency() {
        return tagretCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateDto that = (ExchangeRateDto) o;
        return Objects.equals(id, that.id) && Objects.equals(baseCurrency, that.baseCurrency) && Objects.equals(tagretCurrency, that.tagretCurrency) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, baseCurrency, tagretCurrency, rate);
    }

    @Override
    public String toString() {
        return "ExchangeRateDto{" +
               "id=" + id +
               ", baseCurrency=" + baseCurrency +
               ", tagretCurrency=" + tagretCurrency +
               ", rate=" + rate +
               '}';
    }
}
