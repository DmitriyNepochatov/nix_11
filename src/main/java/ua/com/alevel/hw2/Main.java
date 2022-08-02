package ua.com.alevel.hw2;

import ua.com.alevel.hw2.controller.Controller;
import ua.com.alevel.hw2.model.Fighter;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.TypeOfFighter;
import ua.com.alevel.hw2.service.FighterService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //Controller.run();

        test();
    }

    public static void test() {
        FighterService fighterService = FighterService.getInstance();
        for (int i = 0; i < 5; i++) {
            fighterService.save(fighterService.createPlane());
        }
        fighterService.findAll().forEach(System.out::println);
        int price = 2000;
        System.out.println("\nPrice: " + price);
        fighterService.findPlanesMoreExpensiveThanPrice(price);

        System.out.println("\nsum for planes (Count*Price): " + fighterService.calculatePrice());

        System.out.println("\nPlanes to Map");
        Map<String, String> map = fighterService.sortedOfModelDistinctsPlanesToMap();
        map.forEach((key, value) -> System.out.println("Key: " + key + " Value: " + value));

        fighterService.findById("0").get().setDetails(List.of("Engine", "fender", "chassis", "cab"));
        System.out.println("\nCheck details :" + fighterService.isConcreteDetailExists("Engine"));

        System.out.println("\nStat: " + fighterService.getSummaryPriceStatistics());

        //fighterService.save(new Fighter(null, null, null, 0,0, null, 0));
        System.out.println("\nAll planes have price? : " + fighterService.isAllPlanesHavePrice());

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("PlaneBrand", "BOEING");
        objectMap.put("model", "Plane123-48");
        objectMap.put("price", 123);
        objectMap.put("count", 48);
        objectMap.put("TypeOfFighter", "BOMBER");
        objectMap.put("bombLoad", 12);
        Fighter fighter = fighterService.createPlaneFromMap(objectMap);
        System.out.println("\n" + fighter);
    }
}
