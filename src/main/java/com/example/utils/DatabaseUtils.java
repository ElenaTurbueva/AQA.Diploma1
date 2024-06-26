package com.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Config.DB_URL);
    }

    public static boolean isCompanyDeleted(String companyId) {
        String query = "SELECT deletedAt FROM companies WHERE id = '" + companyId + "'";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getTimestamp("deletedAt") != null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
