package com.example.retea_gui.repository.database;

import com.example.retea_gui.Main;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private String url = "jdbc:postgresql://localhost:5432/retea";
    private String user = "postgres";
    private String password = "postgres";

    public JDBCUtils() {
        loadDBCredentials();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error getting connection " + e);
        }
        return connection;
    }

    private void loadDBCredentials() {
        Properties properties = new Properties();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
//            properties.load(inputStream);

//            url = properties.getProperty("jdbc.url");
//            user = properties.getProperty("jdbc.user");
//            password = properties.getProperty("jdbc.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
