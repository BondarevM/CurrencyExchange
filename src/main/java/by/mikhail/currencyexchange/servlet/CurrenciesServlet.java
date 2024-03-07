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


//            currencyService.findAll().forEach(currencyDto -> {
//                writer.write("""
//                        <li>
//                            ID: %s  Code: %s  FullName: %s  Sign: %s
//                        </li>
//                        """.formatted(currencyDto.getID(),currencyDto.getCode(),currencyDto.getFullName(),currencyDto.getSign()));
//
//            });

        }
    }
}
