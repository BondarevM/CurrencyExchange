package by.mikhail.currencyexchange.service;

import by.mikhail.currencyexchange.dao.CurrencyDao;
import by.mikhail.currencyexchange.dto.CurrencyDto;
import by.mikhail.currencyexchange.entity.Currency;

import java.sql.SQLException;
import java.util.List;

public class CurrencyService {
    private static final CurrencyService INSTANCE  = new CurrencyService();

    private CurrencyService() {
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    public List<CurrencyDto> findAll() throws SQLException {
        return currencyDao.findAll().stream().map(currency -> new CurrencyDto(
                currency.getId(),currency.getCode(),currency.getFullName(),currency.getSign()
        )).toList();
    }
    public CurrencyDto findByCode(String code){
        return currencyDao.findByCode(code).stream().map(currency -> new CurrencyDto(
                currency.getId(), currency.getCode(), currency.getFullName(), currency.getSign())).findFirst().get();
    }

    public void update(String code, String FullName, String Sign){
        Currency currency = new Currency();
        currency.setCode(code);
        currency.setFullName(FullName);
        currency.setSign(Sign);
        currencyDao.update(currency);
    }
    public List<String> findAllCodes()  {
        List<String> result = null;
        try {
            result = currencyDao.findAll().stream().map(currency -> new String(currency.getCode())).toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    public boolean checkCurrencyExists(String code) throws SQLException {
        List<String> all = currencyDao.findAll().stream().map(currency -> new String(currency.getCode())).toList();
        for (String s : all){
            if (s.equals(code)){
                return true;
            }
        }
        return false;
    }


}
