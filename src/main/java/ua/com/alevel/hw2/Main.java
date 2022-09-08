package ua.com.alevel.hw2;

import ua.com.alevel.hw2.config.FlywayConfig;
import ua.com.alevel.hw2.controller.Controller;

public class Main {
    public static void main(String[] args) {
        FlywayConfig.initialize();

        Controller.run();
    }
}
