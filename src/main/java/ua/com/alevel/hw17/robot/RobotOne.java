package ua.com.alevel.hw17.robot;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw17.factory.RobotsFactory;
import java.util.Random;

@Getter
@Setter
public class RobotOne extends Thread {
    private static final int MIN_COUNT_OF_FUEL = 500;
    private static final int MAX_COUNT_OF_FUEL = 1001;
    private static final int SUPPLY_TIME = 3000;
    private static final Random RANDOM = new Random();

    public RobotOne() {
    }

    @Override
    public void run() {
        while (true) {
            int fuelCount = RANDOM.nextInt(MIN_COUNT_OF_FUEL, MAX_COUNT_OF_FUEL);
            System.out.println("Robot one received fuel: " + fuelCount + " and he started transporting");
            try {
                Thread.sleep(SUPPLY_TIME);
                int totalFuel = RobotsFactory.getInstance().getFuelCount().get() + fuelCount;
                System.out.println("Robot one delivered fuel in the amount of " + fuelCount + " gallons, the total fuel supply at the moment: " + totalFuel);
                RobotsFactory.getInstance().setFuelCount(totalFuel);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
