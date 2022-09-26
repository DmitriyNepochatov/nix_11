package ua.com.alevel.hw16.tasktwo;

import lombok.Getter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public final class PrimeNumber extends Thread {
    private Iterator<Integer> iterator;
    private int count;
    private AtomicBoolean worked;

    public PrimeNumber(List<Integer> list) {
        this.iterator = list.iterator();
        worked = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            if (isPrimeNumber(value)) {
                count++;
            }
            System.out.println("Current " + Thread.currentThread().getName() + " has count= " + count + " value= " + value);
            if (!iterator.hasNext()) {
                worked.set(false);
            }
        }
    }

    private boolean isPrimeNumber(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
