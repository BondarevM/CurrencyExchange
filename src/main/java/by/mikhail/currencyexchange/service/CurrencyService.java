package by.mikhail.currencyexchange.service;

import by.mikhail.currencyexchange.dao.CurrencyDao;
import by.mikhail.currencyexchange.dto.CurrencyDto;
import by.mikhail.currencyexchange.entity.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyService {
    private static final CurrencyService INSTANCE  = new CurrencyService();

    private CurrencyService() {
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();
    public List<CurrencyDto> findAll(){
        return currencyDao.findAll().stream().map(currency -> new CurrencyDto(
                currency.getId(),currency.getCode(),currency.getFullName(),currency.getSign()
        )).toList();
    }
    public CurrencyDto findByCode(String code){
        return currencyDao.findByCode(code).stream().map(currency -> new CurrencyDto(
                currency.getId(), currency.getCode(), currency.getFullName(), currency.getSign())).findFirst().get();
    }

    public static void main(String[] args) {
        CurrencyDto usd = INSTANCE.findByCode("USD");
        System.out.println(usd);

    }

}
