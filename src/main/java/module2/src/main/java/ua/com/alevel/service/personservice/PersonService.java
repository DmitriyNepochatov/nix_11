package ua.com.alevel.service.personservice;

import ua.com.alevel.idgenerator.IDGenerator;
import ua.com.alevel.model.customer.Customer;
import java.util.Random;

public final class PersonService {
    private static final Random RANDOM = new Random();
    private static PersonService instance;

    private PersonService() {
    }

    public static PersonService getInstance() {
        if (instance == null) {
            instance = new PersonService();
        }

        return instance;
    }

    public Customer createRandomCustomer() {
        return new Customer(IDGenerator.createID(), createRandomEmail(), RANDOM.nextInt(60) + 13);
    }

    private String createRandomEmail() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < RANDOM.nextInt(15) + 6; i++) {
            builder.append(alphabet.charAt(RANDOM.nextInt(alphabet.length())));
        }

        return builder.append("@mail.com").toString();
    }
}
