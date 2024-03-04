package by.mikhail.currencyexchange.dao;

import by.mikhail.currencyexchange.entity.Currency;
import by.mikhail.currencyexchange.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao implements Dao<Integer, Currency> {
    private static final CurrencyDao INSTANCE = new CurrencyDao();

    private CurrencyDao() {
    }

    public static void main(String[] args) throws SQLException {
        List<Currency> all = INSTANCE.findAll();
        System.out.println(all);
        for (Currency c : all){
            System.out.println(c);
        }
    }


    public static CurrencyDao getInstance(){
        return INSTANCE;
    }

    private static final String FIND_ALL = """
            SELECT ID, Code, FullName, Sign
            from Currencies;
            """;

    @Override
    public List<Currency> findAll() {
        List<Currency> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(buildCurrency(resultSet));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
    public Optional<Currency> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void update(Currency entity) {

    }
}
