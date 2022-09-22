package ua.com.alevel.hw17;

import ua.com.alevel.hw17.factory.RobotsFactory;
import ua.com.alevel.hw17.robot.RobotFive;
import ua.com.alevel.hw17.robot.RobotFour;
import ua.com.alevel.hw17.robot.RobotOne;
import ua.com.alevel.hw17.robot.RobotTwoAndThree;

public class Main {
    public static void main(String[] args) {
        RobotOne robotOne = new RobotOne();
        RobotTwoAndThree robotTwoAndThree = new RobotTwoAndThree(2);
        RobotTwoAndThree robotTwoAndThree1 = new RobotTwoAndThree(3);
        RobotFour robotFour = new RobotFour();
        RobotFive robotFive = new RobotFive();

        robotOne.setDaemon(true);
        robotTwoAndThree.setDaemon(true);
        robotTwoAndThree1.setDaemon(true);
        robotFour.setDaemon(true);
        robotFive.setDaemon(true);

        robotOne.start();
        robotTwoAndThree.start();
        robotTwoAndThree1.start();
        robotFour.start();
        robotFive.start();

        while(RobotsFactory.getInstance().getFinalDetail().get() < 100);

        System.out.println("One detail was created!");
    }
}
