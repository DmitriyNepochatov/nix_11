package ua.com.alevel.service.personservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.model.customer.Customer;

class PersonServiceTest {
    private PersonService target;

    @BeforeEach
    void setUp() {
        target = PersonService.getInstance();
    }

    @Test
    void createRandomCustomer() {
        Customer customer = target.createRandomCustomer();

        Assertions.assertNotNull(customer);
    }
}