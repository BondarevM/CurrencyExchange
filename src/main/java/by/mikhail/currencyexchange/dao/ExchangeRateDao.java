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
        Optional<ExchangeRate> rate = INSTANCE.findByCode("EURRUB");
        System.out.println(rate);

    }

    private ExchangeRateDao() {
    }

    private final String FIND_ALL = """
            SELECT *
            FROM  ExchangeRates;
            """;

    private final String FIND_BY_CODES = """
            SELECT e.ID, BaseCurrencyId, TargetCurrencyId, Rate
            FROM ExchangeRates e
                     JOIN Currencies C on C.ID = e.BaseCurrencyId
            WHERE BaseCurrencyId = (SELECT id
                                    FROM Currencies
                                    WHERE Code = ?) AND TargetCurrencyId = (SELECT id
                                                                                FROM Currencies
                                                                                WHERE Code = ?);
                        """;
    private final String ADD_EXCHANGE_RATE = """
                        INSERT INTO ExchangeRates
                        VALUES (null,?,?,?);
            """;
    private final String UPDATE_EXCHANGE_RATE = """
            UPDATE ExchangeRates
            SET Rate = ? 
            WHERE ID = ?;
            """;

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        List<ExchangeRate> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                result.add(buildExchangeRate(resultSet));
            }
        }
        return result;
    }

    @Override
    public Optional<ExchangeRate> findByCode(String code) {
        String baseCurrency = code.substring(0, 3);
        String targetCurrency = code.substring(3);

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_BY_CODES)) {
            prepareStatement.setString(1, baseCurrency);
            prepareStatement.setString(2, targetCurrency);
            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                ExchangeRate exchangeRate = buildExchangeRate(resultSet);
                return Optional.ofNullable(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void add(ExchangeRate entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(ADD_EXCHANGE_RATE)) {
            prepareStatement.setInt(1, entity.getBaseCurrency().getId());
            prepareStatement.setInt(2, entity.getTagretCurrency().getId());
            prepareStatement.setBigDecimal(3, entity.getRate());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(ExchangeRate entity) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_EXCHANGE_RATE)) {
            prepareStatement.setBigDecimal(1, entity.getRate());
            prepareStatement.setInt(2, entity.getId());
            prepareStatement.executeUpdate();
        }
    }

    private ExchangeRate buildExchangeRate(ResultSet resultSet) throws SQLException {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(resultSet.getInt("ID"));
        exchangeRate.setBaseCurrency(currencyDao.findById(resultSet.getInt("BaseCurrencyId")).get());
        exchangeRate.setTagretCurrency(currencyDao.findById(resultSet.getInt("TargetCurrencyId")).get());
        exchangeRate.setRate(resultSet.getBigDecimal("Rate"));
        return exchangeRate;
    }
}
