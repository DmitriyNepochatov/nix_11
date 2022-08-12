package ua.com.alevel.service.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.model.product.Product;
import ua.com.alevel.model.product.telephone.Telephone;
import ua.com.alevel.model.product.television.Television;
import java.util.ArrayList;
import java.util.List;

class DataParserTest {
    private DataParser target;

    @BeforeEach
    void setUp() {
        target = DataParser.getInstance();
    }

    @Test
    void parse_positive() {
        List<String> list = new ArrayList<>();
        list.add("type,series,model,diagonal,screen type,country,price");
        list.add("Telephone,9R,OnePlus,none,OLED,none,500");
        list.add("Television,50UN265,none,35,LED,China,1500");
        Telephone telephone = new Telephone("9R", "OLED", 500, "OnePlus");
        Television television = new Television("50UN265", "LED", 1500, 35, "China");

        List<Product> productList = target.parse(list);
        Assertions.assertEquals(telephone, productList.get(0));
        Assertions.assertEquals(television, productList.get(1));
    }

    @Test
    void parse_negative(){
        List<String> list = new ArrayList<>();
        list.add("type,series,model,diagonal,screen type,country,price");
        list.add("Telephone,9R,OnePlus,none,,none,500");
        list.add(",50UN265,none,35,LED,China,1500");

        List<Product> productList = target.parse(list);
        Assertions.assertTrue(productList.isEmpty());
    }
}