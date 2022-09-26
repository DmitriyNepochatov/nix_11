package ua.com.alevel.hw16.taskone;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("Hello from " + Thread.currentThread().getName());

        List<Thread> threads = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            threads.add(new Thread(runnable));
        }

        for (int i = threads.size() - 1; i >= 0; i--) {
            Thread thread = threads.get(i);
            thread.start();
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
