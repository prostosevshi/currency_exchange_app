package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.ExchangeRateService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService;

    public ExchangeServlet() {
        this.exchangeRateService = new ExchangeRateService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fromCurrencyCode = req.getParameter("from");
        String toCurrencyCode = req.getParameter("to");
        BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        BigDecimal rate = exchangeRateService.getExchangeRate(fromCurrencyCode, toCurrencyCode);
        BigDecimal convertedAmount = rate.multiply(amount);

        ExchangeResponse exchangeResponse = new ExchangeResponse(fromCurrencyCode, toCurrencyCode, amount, rate, convertedAmount);

        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(exchangeResponse));
    }

    private static class ExchangeResponse {
        private final String baseCurrency;
        private final String targetCurrency;
        private final BigDecimal amount;
        private final BigDecimal rate;
        private final BigDecimal convertedAmount;

        public ExchangeResponse(String baseCurrency, String targetCurrency, BigDecimal amount, BigDecimal rate, BigDecimal convertedAmount) {
            this.baseCurrency = baseCurrency;
            this.targetCurrency = targetCurrency;
            this.amount = amount;
            this.rate = rate;
            this.convertedAmount = convertedAmount;
        }
    }
}
