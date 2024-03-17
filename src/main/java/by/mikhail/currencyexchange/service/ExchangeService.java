package by.mikhail.currencyexchange.service;

import by.mikhail.currencyexchange.dao.CurrencyDao;
import by.mikhail.currencyexchange.dao.ExchangeRateDao;
import by.mikhail.currencyexchange.entity.Currency;
import by.mikhail.currencyexchange.entity.ExchangeRate;
import by.mikhail.currencyexchange.entity.ExchangeResponse;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeService {
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private static final ExchangeService INSTANCE = new ExchangeService();

    private ExchangeService() {
    }

    public static ExchangeService getInstance() {
        return INSTANCE;
    }

    public Optional<ExchangeResponse> makeConversion(String baseCurrencyCode, String targetCurrencyCode, String amount) throws SQLException {
        Optional<Currency> baseCurrencyOptional = currencyDao.findByCode(baseCurrencyCode);
        Optional<Currency> targetCurrencyOptional = currencyDao.findByCode(targetCurrencyCode);

        if (!baseCurrencyOptional.isPresent() || !targetCurrencyOptional.isPresent()) {
            return Optional.empty();
        }
        Currency baseCurrency = baseCurrencyOptional.get();
        Currency targetCurrency = targetCurrencyOptional.get();
        BigDecimal amountValue = new BigDecimal(amount);
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(baseCurrencyCode + targetCurrencyCode);
        Optional<ExchangeRate> convertExchangeRate = exchangeRateDao.findByCode(targetCurrencyCode + baseCurrencyCode);

        if (!exchangeRate.equals(Optional.empty())) {
            ExchangeResponse exchangeResponse = new ExchangeResponse();
            exchangeResponse.setBaseCurrency(baseCurrency);
            exchangeResponse.setTargetCurrency(targetCurrency);
            exchangeResponse.setRate(exchangeRate.get().getRate());
            exchangeResponse.setAmount(amountValue);
            exchangeResponse.setConvertedAmount(amountValue.multiply(exchangeRate.get().getRate()));
            return Optional.ofNullable(exchangeResponse);
        } else {
            return makeConversionByReverseRate(baseCurrencyCode, targetCurrencyCode, amount);
        }
    }

    private Optional<ExchangeResponse> makeConversionByReverseRate(String baseCurrencyCode, String targetCurrencyCode, String amount) throws SQLException {
        Currency baseCurrency = currencyDao.findByCode(baseCurrencyCode).get();
        Currency targetCurrency = currencyDao.findByCode(targetCurrencyCode).get();
        BigDecimal amountValue = new BigDecimal(amount);
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(targetCurrencyCode + baseCurrencyCode);

        if (!exchangeRate.equals(Optional.empty())) {
            BigDecimal rate = (new BigDecimal("1")).divide(exchangeRate.get().getRate());
            ExchangeResponse exchangeResponse = new ExchangeResponse();
            exchangeResponse.setBaseCurrency(baseCurrency);
            exchangeResponse.setTargetCurrency(targetCurrency);
            exchangeResponse.setRate(rate);
            exchangeResponse.setAmount(amountValue);
            exchangeResponse.setConvertedAmount(amountValue.multiply(rate));
            return Optional.ofNullable(exchangeResponse);
        } else {
            return exchangeUsingUsdCurrency(baseCurrencyCode, targetCurrencyCode, amount);
        }

    }

    private Optional<ExchangeResponse> exchangeUsingUsdCurrency(String baseCurrencyCode, String targetCurrencyCode, String amount) throws SQLException {
        if ((exchangeRateDao.findByCode("USD" + baseCurrencyCode).isPresent()) && (exchangeRateDao.findByCode("USD" + targetCurrencyCode).isPresent())) {
            BigDecimal rateFromBaseCurrencyToUsd = makeConversionByReverseRate(baseCurrencyCode, "USD", amount).get().getRate();
            BigDecimal rateFromUsdToTargetCurrency = makeConversion("USD", targetCurrencyCode, amount).get().getRate();
            BigDecimal resultRate = rateFromBaseCurrencyToUsd.multiply(rateFromUsdToTargetCurrency);

            ExchangeResponse result = new ExchangeResponse();
            result.setBaseCurrency(currencyDao.findByCode(baseCurrencyCode).get());
            result.setTargetCurrency(currencyDao.findByCode(targetCurrencyCode).get());
            result.setRate(resultRate);
            result.setAmount(new BigDecimal(amount));
            result.setConvertedAmount(new BigDecimal(amount).multiply(resultRate));

            return Optional.ofNullable(result);
        } else {
            return Optional.empty();
        }
    }
}
