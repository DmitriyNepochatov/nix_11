package ua.com.alevel.hw2.command;

public class Exit implements Command{
    @Override
    public void execute() {
        System.exit(0);
    }
}
