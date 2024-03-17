package by.mikhail.currencyexchange.servlet;

import by.mikhail.currencyexchange.dto.CurrencyDto;
import by.mikhail.currencyexchange.service.CurrencyService;
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

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter writer = resp.getWriter()) {
            List<CurrencyDto> currencies = currencyService.findAll();
            String currenciesJson = objectMapper.writeValueAsString(currencies);
            writer.write(currenciesJson);
        } catch (SQLException e) {
            resp.sendError(500, "Something is wrong with the database");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String name = req.getParameter("name");
        String code = req.getParameter("code");
        String sign = req.getParameter("sign");

        if (name == null || code == null || sign == null) {
            resp.sendError(400, "A required form field is missing");
            return;
        }
        try {
            if (currencyService.checkCurrencyExists(code)) {
                resp.sendError(409, "A currency with this code already exists");
                return;
            }
        } catch (SQLException e) {
            resp.sendError(500, "Something is wrong with the database");
            return;
        }
        currencyService.update(code, name, sign);

        CurrencyDto currency = currencyService.findByCode(code);
        String currencyJson = objectMapper.writeValueAsString(currency);

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(currencyJson);
        }
    }
}
