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

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;


@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (req.getPathInfo().equals("/")) {
            resp.sendError(400, "The currency code is missing from the address");
            return;
        }

        String code = req.getPathInfo().substring(1);

        try {
            if (!currencyService.foundCurrency(code)) {
                resp.sendError(404, "Currency not found");
                return;
            }
        } catch (SQLException e) {
            resp.sendError(500, "Something is wrong with the database");
        }

        CurrencyDto currency = currencyService.findByCode(code);
        String currencyJson = objectMapper.writeValueAsString(currency);

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(currencyJson);
        }


    }
}
