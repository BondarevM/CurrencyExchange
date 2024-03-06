package by.mikhail.currencyexchange.servlet;

import by.mikhail.currencyexchange.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>Список валют</h1>");

            currencyService.findAll().forEach(currencyDto -> {
                writer.write("""
                        <li>
                            ID: %s  Code: %s  FullName: %s  Sign: %s
                        </li>
                        """.formatted(currencyDto.getID(),currencyDto.getCode(),currencyDto.getFullName(),currencyDto.getSign()));

            });

        }
    }
}
