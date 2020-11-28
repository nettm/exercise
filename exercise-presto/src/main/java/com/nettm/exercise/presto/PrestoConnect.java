package com.nettm.exercise.presto;

import java.sql.*;
import java.util.Properties;

public class PrestoConnect {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        Class.forName("io.prestosql.jdbc.PrestoDriver");

        // URL parameters
        String url = "jdbc:presto://CNSHJQVI0254:7778/mysql/dw_metadata";
        Properties properties = new Properties();
        properties.setProperty("user", "test");
        properties.setProperty("password", "test123");
        properties.setProperty("SSL", "true");
        properties.setProperty("SSLKeyStorePassword", "presto123");
        properties.setProperty("SSLKeyStorePath", "C:\\Develop\\presto.jks");
        Connection connection = DriverManager.getConnection(url, properties);
        Statement stmt = connection.createStatement();

        String sql = "select * from t_server";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String code = rs.getString("code");
            System.out.println(code);
        }

        rs.close();
        stmt.close();
        connection.close();
    }
}
