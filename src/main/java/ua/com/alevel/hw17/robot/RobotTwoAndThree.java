package ua.com.alevel.hw17.robot;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw17.factory.RobotsFactory;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class RobotTwoAndThree extends Thread {
    private static final int MIN_COUNT = 10;
    private static final int MAX_COUNT = 21;
    private static final Random RANDOM = new Random();
    private static final int RESTART_TIME = 2000;
    private final int robotNumber;

    public RobotTwoAndThree(int robotNumber) {
        this.robotNumber = robotNumber;
    }

    @Override
    public void run() {
        while (true) {
            AtomicInteger processOfCreatingDetail = RobotsFactory.getInstance().getProcessOfCreatingDetail();
            while (processOfCreatingDetail.get() < 100) {
                int creatingPartOfDetail = RANDOM.nextInt(MIN_COUNT, MAX_COUNT);
                System.out.println("Robot " + robotNumber + " did " + creatingPartOfDetail + " points of detail");
                try {
                    Thread.sleep(RESTART_TIME);
                    int totalProgressOnCreatingOfDetail = processOfCreatingDetail.get() + creatingPartOfDetail;
                    System.out.println("Robot "+robotNumber+" created a total detail for " + totalProgressOnCreatingOfDetail + " points");
                    RobotsFactory.getInstance().setProcessOfCreatingDetail(totalProgressOnCreatingOfDetail);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
