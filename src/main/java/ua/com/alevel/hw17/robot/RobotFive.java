package ua.com.alevel.hw17.robot;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw17.factory.RobotsFactory;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class RobotFive extends Thread {
    private static final int POINTS = 10;
    private static final int MIN_FUEL = 350;
    private static final int MAX_FUEL = 701;
    private static final Random RANDOM = new Random();
    private static final int RESTART_TIME = 1000;

    public RobotFive() {
    }

    @Override
    public void run() {
        while (true) {
            AtomicInteger finalDetail = RobotsFactory.getInstance().getFinalDetail();
            AtomicInteger processOfProgrammingSchema = RobotsFactory.getInstance().getProcessOfProgrammingSchema();
            AtomicInteger fuelTotal = RobotsFactory.getInstance().getFuelCount();
            if (processOfProgrammingSchema.get() >= 100) {
                System.out.println("Robot 5 started work");
            }

            while (processOfProgrammingSchema.get() >= 100 && finalDetail.get() < 100) {
                int fuelRequirement = RANDOM.nextInt(MIN_FUEL, MAX_FUEL);
                System.out.println("Robot 5 needs fuel in quantity: " + fuelRequirement);
                System.out.println("Fuel present " + fuelTotal.get() + " gallons");
                while (fuelRequirement > fuelTotal.get()) ;

                int totalFuel = fuelTotal.get() - fuelRequirement;
                int total = finalDetail.get() + POINTS;
                System.out.println("Robot 5 points of detail: " + total);
                try {
                    Thread.sleep(RESTART_TIME);
                    RobotsFactory.getInstance().setFuelCount(totalFuel);
                    RobotsFactory.getInstance().setFinalDetail(total);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (total == 100) {
                    RobotsFactory.getInstance().setProcessOfProgrammingSchema(0);
                    RobotsFactory.getInstance().setProcessOfCreatingDetail(0);
                }
            }
        }
    }
}
