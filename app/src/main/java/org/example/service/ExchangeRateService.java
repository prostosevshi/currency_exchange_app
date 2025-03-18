package org.example.service;

import org.example.db.DBConnection;
import org.example.model.ExchangeRate;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateService {
    private Connection connection;

    public ExchangeRateService() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ExchangeRate> getAllExchangeRates() {
        List<ExchangeRate> rates = new ArrayList<>();
        String query = "SELECT * FROM ExchangeRates";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int baseCurrencyId = rs.getInt("baseCurrencyId");
                int targetCurrencyId = rs.getInt("targetCurrencyId");
                String baseCurrencyCode = getCurrencyCodeById(baseCurrencyId);
                String targetCurrencyCode = getCurrencyCodeById(targetCurrencyId);

                ExchangeRate rate = new ExchangeRate(rs.getInt("id"), baseCurrencyCode, targetCurrencyCode, rs.getBigDecimal("rate"));
                rates.add(rate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rates;
    }

    public ExchangeRate addExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) {
        String query = "INSERT INTO ExchangeRates (baseCurrencyId, targetCurrencyId, rate) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int baseCurrencyId = getCurrencyIdByCode(baseCurrencyCode);
            int targetCurrencyId = getCurrencyIdByCode(targetCurrencyCode);

            if (baseCurrencyId == -1 || targetCurrencyId == -1) {
                System.out.println("Ошибка: Одна из валют не найдена в базе.");
                return null;
            }

            pstmt.setInt(1, baseCurrencyId);
            pstmt.setInt(2, targetCurrencyId);
            pstmt.setBigDecimal(3, rate);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Не удалось вставить запись, 0 затронутых строк.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new ExchangeRate(id, baseCurrencyCode, targetCurrencyCode, rate);
                } else {
                    throw new SQLException("Не удалось получить сгенерированный ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigDecimal getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {

        if ("USD".equals(fromCurrencyCode) && "EUR".equals(toCurrencyCode)) {
            return new BigDecimal("0.85");
        }
        if ("EUR".equals(fromCurrencyCode) && "USD".equals(toCurrencyCode)) {
            return new BigDecimal("1.18");
        }

        return BigDecimal.ONE;
    }

    private int getCurrencyIdByCode(String currencyCode) {
        String query = "SELECT id FROM currencies WHERE code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1, currencyCode);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getCurrencyCodeById(int currencyId) {
        String query = "SELECT code FROM currencies WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, currencyId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
