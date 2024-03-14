package by.mikhail.currencyexchange.servlet;

import by.mikhail.currencyexchange.dto.ExchangeRateDto;
import by.mikhail.currencyexchange.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());


        try (PrintWriter writer = resp.getWriter()) {
            List<ExchangeRateDto> exchangeRates = exchangeRateService.findAll();
            String exchangeRatesJson = objectMapper.writeValueAsString(exchangeRates);
            writer.write(exchangeRatesJson);
        } catch (SQLException e) {
            resp.sendError(500, "Something is wrong with the database");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        if (baseCurrencyCode == null || targetCurrencyCode == null || rate == null) {
            resp.sendError(400, "A required form field is missing");
            return;
        }

        try {
            if (exchangeRateService.checkExchangeRateExists(baseCurrencyCode + targetCurrencyCode)) {
                resp.sendError(409, "The exchange rate for these currencies already exists");
                return;
            }
        } catch (SQLException e) {
            resp.sendError(500, "Something is wrong with the database");
            return;
        }

        if (!exchangeRateService.checkCodePairExists(baseCurrencyCode + targetCurrencyCode)) {
            resp.sendError(404, "One (or both) currencies from the currency pair does not exist in the database");
            return;
        }

        exchangeRateService.update(baseCurrencyCode, targetCurrencyCode, rate);
        ExchangeRateDto exchangeRate = exchangeRateService.findByCode(baseCurrencyCode + targetCurrencyCode);
        String exchangeRateJson = objectMapper.writeValueAsString(exchangeRate);

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(exchangeRateJson);
        }


    }
}
