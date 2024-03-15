package by.mikhail.currencyexchange.servlet;

import by.mikhail.currencyexchange.dao.ExchangeRateDao;
import by.mikhail.currencyexchange.dto.ExchangeRateDto;
import by.mikhail.currencyexchange.entity.ExchangeRate;
import by.mikhail.currencyexchange.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if (req.getPathInfo().equals("/")) {
            resp.sendError(400, "The exchange rate codes is missing from the address");
            return;
        }

        String codes = req.getPathInfo().substring(1);

        try {
            if (!exchangeRateService.checkExchangeRateExists(codes)) {
                resp.sendError(404, "Exchange rate not found");
                return;
            }
        } catch (SQLException e) {
            resp.sendError(500, "Something is wrong with the database");
            return;
        }

        ExchangeRateDto exchangeRate = exchangeRateService.findByCode(codes);
        String exchangeRateJson = objectMapper.writeValueAsString(exchangeRate);

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(exchangeRateJson);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String requestBody = req.getReader().readLine();
        if (requestBody == null) {
            resp.sendError(400, "A required form field is missing");
            return;
        }
        String[] requestParts = requestBody.split("=");
        String param = requestParts[0];
        String rateValue = requestParts[1];

        if (!param.equals("rate")) {
            resp.sendError(400, "A required form field is missing");
            return;
        }
        String codes = req.getPathInfo().substring(1);

        if (exchangeRateDao.findByCode(codes).equals(Optional.empty())) {
            resp.sendError(404, "Exchange rate not found");
            return;
        }

        ExchangeRate exchangeRate = exchangeRateDao.findByCode(codes).get();
        exchangeRate.setRate(new BigDecimal(rateValue));

        try {
            exchangeRateDao.update(exchangeRate);
        } catch (SQLException e) {
            resp.sendError(500, "Something is wrong with the database");
            return;
        }

        ExchangeRateDto updatedExchangeRate = exchangeRateService.findByCode(codes);
        String updateExchangeRateJson = objectMapper.writeValueAsString(updatedExchangeRate);

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(updateExchangeRateJson);
        }

    }


}
