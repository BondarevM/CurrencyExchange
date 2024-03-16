package by.mikhail.currencyexchange.servlet;

import by.mikhail.currencyexchange.entity.ExchangeResponse;
import by.mikhail.currencyexchange.service.ExchangeService;
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
import java.util.Optional;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ExchangeService exchangeService = ExchangeService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amount = req.getParameter("amount");


        try {
            Optional<ExchangeResponse> exchangeRateResponse = exchangeService.makeConversion(baseCurrencyCode, targetCurrencyCode, amount);
            if (!exchangeRateResponse.isPresent()){
                resp.sendError(404,"Conversion not possible");
                return;
            }
            String exchangeResponseJson = objectMapper.writeValueAsString(exchangeRateResponse.get());

            try (PrintWriter writer = resp.getWriter()) {
                writer.write(exchangeResponseJson);
            }

        } catch (SQLException e) {
            resp.sendError(500, "Something wrong with database");
        }



    }
}
