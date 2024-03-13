package by.mikhail.currencyexchange.service;

import by.mikhail.currencyexchange.dao.ExchangeRateDao;
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
        List<ExchangeRateDto> all = INSTANCE.findAll();
        System.out.println(all        );

    }

}
