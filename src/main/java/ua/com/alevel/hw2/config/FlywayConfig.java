package ua.com.alevel.hw2.config;

import org.flywaydb.core.Flyway;

public final class FlywayConfig {
    private static final String URL = "jdbc:postgresql://khwmscwlrzvnjl:3da62380e3c79bbcac5ca9dc0f5936b03b8ebf238a8507516b39a5886ea17725@ec2-34-247-72-29.eu-west-1.compute.amazonaws.com:5432/dfvea792kece9j";
    private static final String USER = "khwmscwlrzvnjl";
    private static final String PASSWORD = "3da62380e3c79bbcac5ca9dc0f5936b03b8ebf238a8507516b39a5886ea17725";
    private static final String SCHEMA = "public";
    private static final String LOCATION = "db/migration";

    private FlywayConfig() {
    }

    public static void initialize() {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .baselineOnMigrate(true)
                .schemas(SCHEMA)
                .locations(LOCATION)
                .load();

        flyway.migrate();
    }
}
