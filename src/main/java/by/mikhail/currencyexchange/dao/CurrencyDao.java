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

    private static final String FIND_BY_CODE = FIND_ALL + """
    WHERE Code = ?
""";
    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private CurrencyDao() {
    }

    public static void main(String[] args) throws SQLException {
        Optional<Currency> usd = INSTANCE.findByCode("asd");
        System.out.println(usd);
        if (usd.equals(Optional.empty())){
            System.out.println("kek");
        }
    }


    public static CurrencyDao getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Currency> findAll() {
        List<Currency> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                result.add(buildCurrency(resultSet));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Currency> findByCode(String code) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_BY_CODE)) {
            prepareStatement.setString(1, code);

            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()){
                Currency currency = buildCurrency(resultSet);
                return Optional.ofNullable(currency);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }



    private Currency buildCurrency(ResultSet resultSet) throws SQLException {
        Currency currency = new Currency();
        currency.setId(resultSet.getInt("ID"));
        currency.setCode(resultSet.getString("Code"));
        currency.setFullName(resultSet.getString("FullName"));
        currency.setSign(resultSet.getString("Sign"));


        return currency;

    }


    @Override
    public void update(Currency entity) {

    }
}
