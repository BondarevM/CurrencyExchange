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

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
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
            if(!exchangeRateService.foundExchangeRate(codes)){
                resp.sendError(404,"Exchange rate not found");
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
}
