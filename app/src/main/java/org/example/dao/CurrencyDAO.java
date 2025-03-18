package org.example.dao;

import org.example.db.DBConnection;
import org.example.model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
    public List<Currency> getAllCurrencies() {
        List<Currency> currencies = new ArrayList<Currency>();
        String query = "select * from currencies";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                currencies.add(new Currency(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("full_name"),
                        rs.getString("sign")
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return currencies;
    }
}
