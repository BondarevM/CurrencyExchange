package by.mikhail.currencyexchange.service;

import by.mikhail.currencyexchange.dao.CurrencyDao;
import by.mikhail.currencyexchange.dao.ExchangeRateDao;
import by.mikhail.currencyexchange.dto.ExchangeRateDto;
import by.mikhail.currencyexchange.entity.ExchangeRate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ExchangeRateService {
    private static final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private static final CurrencyService currencyService = CurrencyService.getInstance();
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

//        boolean b = INSTANCE.foundExchangeRate("RUBGEL");
//        System.out.println(b);
        boolean b = INSTANCE.checkCodePairExists("JPYUSD");
        System.out.println(b);

    }

    public ExchangeRateDto findByCode(String code){
        return exchangeRateDao.findByCode(code).stream().map(exchangeRate -> new ExchangeRateDto(
                exchangeRate.getId(),exchangeRate.getBaseCurrency(),exchangeRate.getTagretCurrency(),exchangeRate.getRate()
        )).findFirst().get();

    }



    public boolean checkExchangeRateExists(String codes) throws SQLException {
        String baseCurrency = codes.substring(0, 3);
        String targetCurrency = codes.substring(3);
        List<String> baseCurrencyCodes = exchangeRateDao.findAll().stream().map(exchangeRate ->
                new String(exchangeRate.getBaseCurrency().getCode())).toList();

        List<String> targetCurrencyCodes = exchangeRateDao.findAll().stream().map(exchangeRate ->
                new String(exchangeRate.getTagretCurrency().getCode())).toList();

        for (int i = 0; i < baseCurrencyCodes.size(); i++) {
            if (baseCurrency.equals(baseCurrencyCodes.get(i)) && targetCurrency.equals(targetCurrencyCodes.get(i))) {
                return true;
            }
        }
        return false;
    }
    public boolean checkCodePairExists(String codes){
        String baseCurrency = codes.substring(0, 3);
        String targetCurrency = codes.substring(3);

        List<String> allCodes = currencyService.findAllCodes();

        for (int i = 0; i < allCodes.size(); i++) {
            if (baseCurrency.equals(allCodes.get(i))){
                for (int j = 0; j < allCodes.size(); j++) {
                    if (targetCurrency.equals(allCodes.get(j))){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void update(String baseCurrencyCode, String targetCurrencyCode, String rate) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(currencyDao.findByCode(baseCurrencyCode).get());
        exchangeRate.setTagretCurrency(currencyDao.findByCode(targetCurrencyCode).get());
        exchangeRate.setRate(new BigDecimal(rate));
        exchangeRateDao.add(exchangeRate);
    }


}
