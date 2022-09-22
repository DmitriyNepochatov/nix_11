package ua.com.alevel.hw17.robot;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw17.factory.RobotsFactory;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class RobotFour extends Thread {
    private static final int MIN_COUNT = 25;
    private static final int MAX_COUNT = 36;
    private static final Random RANDOM = new Random();
    private static final int RESTART_TIME = 1000;

    public RobotFour() {
    }

    @Override
    public void run() {
        while (true) {
            AtomicInteger processOfCreatingDetail = RobotsFactory.getInstance().getProcessOfCreatingDetail();
            AtomicInteger processOfProgrammingSchema = RobotsFactory.getInstance().getProcessOfProgrammingSchema();
            if (processOfProgrammingSchema.get() == 0 && processOfCreatingDetail.get() >= 100) {
                System.out.println("Robot 4 started work");
            }

            while (processOfProgrammingSchema.get() < 100 && processOfCreatingDetail.get() >= 100) {
                int programmingOfProgrammingSchema = RANDOM.nextInt(MIN_COUNT, MAX_COUNT);
                System.out.println("Robot 4 made " + programmingOfProgrammingSchema + " points in programming of schema");

                double failChance = Math.random();
                int total;

                if (failChance > 0.3) {
                    try {
                        Thread.sleep(RESTART_TIME);
                        total = programmingOfProgrammingSchema + processOfProgrammingSchema.get();
                        System.out.println("Robot four successfully made " + programmingOfProgrammingSchema + " points in programming of schema, total points: " + total);
                        RobotsFactory.getInstance().setProcessOfProgrammingSchema(total);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        Thread.sleep(RESTART_TIME);
                        System.out.println("FAILED");
                        RobotsFactory.getInstance().setProcessOfProgrammingSchema(0);
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
