package by.mikhail.currencyexchange.dao;

import by.mikhail.currencyexchange.entity.Currency;
import by.mikhail.currencyexchange.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<String, Currency> {
    private static final String FIND_ALL = """
            SELECT ID, Code, FullName, Sign
            from Currencies
            """;
    private static final String ADD_CURRENCY = """
            INSERT INTO Currencies
            VALUES (null,?,?,?);
            """;
    private static final String FIND_BY_CODE = FIND_ALL + """
                WHERE Code = ?
            """;
    private static final String FIND_BY_ID = FIND_ALL + """
                WHERE id = ?
            """;
    private static final CurrencyDao INSTANCE = new CurrencyDao();

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    private CurrencyDao() {
    }

    @Override
    public List<Currency> findAll() throws SQLException {
        List<Currency> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                result.add(buildCurrency(resultSet));
            }
            return result;
        }
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_BY_CODE)) {
            prepareStatement.setString(1, code);
            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                Currency currency = buildCurrency(resultSet);
                return Optional.ofNullable(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Optional<Currency> findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                Currency currency = buildCurrency(resultSet);
                return Optional.ofNullable(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public Currency buildCurrency(ResultSet resultSet) throws SQLException {
        Currency currency = new Currency();
        currency.setId(resultSet.getInt("ID"));
        currency.setCode(resultSet.getString("Code"));
        currency.setName(resultSet.getString("FullName"));
        currency.setSign(resultSet.getString("Sign"));
        return currency;
    }

    @Override
    public void add(Currency entity) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(ADD_CURRENCY)) {
            prepareStatement.setString(1, entity.getCode());
            prepareStatement.setString(2, entity.getName());
            prepareStatement.setString(3, entity.getSign());
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Currency currency = new Currency();
        currency.setCode("RUB");
        currency.setName("Rubble");
        currency.setSign("P");
        INSTANCE.add(currency);
    }
}
