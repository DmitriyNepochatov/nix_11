package ua.com.alevel.hw2.config;

import lombok.SneakyThrows;
import java.sql.Connection;
import java.sql.DriverManager;

public final class JDBCConfig {
    private static final String URL = "jdbc:postgresql://ec2-34-247-72-29.eu-west-1.compute.amazonaws.com:5432/dfvea792kece9j";
    private static final String USER = "khwmscwlrzvnjl";
    private static final String PASSWORD = "3da62380e3c79bbcac5ca9dc0f5936b03b8ebf238a8507516b39a5886ea17725";

    private JDBCConfig() {
    }

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
