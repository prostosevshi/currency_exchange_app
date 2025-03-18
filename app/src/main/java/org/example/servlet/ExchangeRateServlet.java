package org.example.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.ExchangeRate;
import org.example.service.ExchangeRateService;

import java.math.BigDecimal;
import java.util.List;

import java.io.IOException;

@WebServlet("/exchangeRates")
@ServletSecurity
public class ExchangeRateServlet extends HttpServlet {
    private ExchangeRateService exchangeRateService;

    @Override
    public void init() throws ServletException {
        exchangeRateService = new ExchangeRateService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ExchangeRate> exchangeRates = exchangeRateService.getAllExchangeRates();
        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(exchangeRates));
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCurrencyCode = req.getParameter("baseCurrencyCode");
        String targetCurrencyCode = req.getParameter("targetCurrencyCode");
        BigDecimal rate = new BigDecimal(req.getParameter("rate"));
        ExchangeRate exchangeRate = exchangeRateService.addExchangeRate(baseCurrencyCode, targetCurrencyCode, rate);
        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(exchangeRate));
    }
}
