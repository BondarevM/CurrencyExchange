package by.mikhail.currencyexchange.dao;

import by.mikhail.currencyexchange.entity.ExchangeRate;
import by.mikhail.currencyexchange.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao implements Dao<String, ExchangeRate> {
    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();
    private final static ExchangeRateDao INSTANCE = new ExchangeRateDao();

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) throws SQLException {
        List<ExchangeRate> all = INSTANCE.findAll();
        System.out.println(all);

    }

    private ExchangeRateDao() {
    }

    private final String FIND_ALL = """
            SELECT *
            FROM  ExchangeRates;
            """;

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        List<ExchangeRate> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(buildExchangeRate(resultSet));
            }
        }
        return result;
    }



    @Override
    public Optional<ExchangeRate> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public void update(ExchangeRate entity) {

    }

    private ExchangeRate buildExchangeRate(ResultSet resultSet) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(resultSet.getInt("ID"));
        exchangeRate.setBaseCurrency(currencyDao.findById(resultSet.getInt("BaseCurrencyId")).get());
        exchangeRate.setTagretCurrency(currencyDao.findById(resultSet.getInt("TargetCurrencyId")).get());


//        exchangeRate.setBaseCurrency(CurrencyDao.getInstance().buildCurrency(resultSet));
//        exchangeRate.setTagretCurrency(CurrencyDao.getInstance().buildCurrency(resultSet));

        exchangeRate.setRate(resultSet.getBigDecimal("Rate"));
        return exchangeRate;
    }
}
