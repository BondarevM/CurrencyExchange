package by.mikhail.currencyexchange.service;

import by.mikhail.currencyexchange.dao.ExchangeRateDao;
import by.mikhail.currencyexchange.dto.CurrencyDto;
import by.mikhail.currencyexchange.dto.ExchangeRateDto;
import by.mikhail.currencyexchange.entity.ExchangeRate;

import java.sql.SQLException;
import java.util.List;

public class ExchangeRateService {
    private static final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();

    private ExchangeRateService() {
    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }

    public List<ExchangeRateDto> findAll() throws SQLException {
        return exchangeRateDao.findAll().stream().map(exchangeRate -> new ExchangeRateDto(
                exchangeRate.getId(), exchangeRate.getBaseCurrency(), exchangeRate.getTagretCurrency(), exchangeRate.getRate()
        )).toList();
    }

    public static void main(String[] args) throws SQLException {
//        List<ExchangeRateDto> all = INSTANCE.findAll();
//        System.out.println(all);

        boolean b = INSTANCE.foundExchangeRate("USD", "RUB");
        System.out.println(b);

    }

    public boolean foundExchangeRate(String baseCurrency, String targetCurrency) throws SQLException {
        List<String> basedCurrencyCodes = exchangeRateDao.findAll().stream().map(exchangeRate ->
                new String(exchangeRate.getBaseCurrency().getCode())).toList();

        List<String> targetCurrencyCodes = exchangeRateDao.findAll().stream().map(exchangeRate ->
                new String(exchangeRate.getTagretCurrency().getCode())).toList();

        for (String firstCode : basedCurrencyCodes) {
            for (String secondCode : targetCurrencyCodes) {
                if (firstCode.equals(baseCurrency) && secondCode.equals(targetCurrency)) {
                    return true;
                }
            }
        }
        return false;
    }


}
