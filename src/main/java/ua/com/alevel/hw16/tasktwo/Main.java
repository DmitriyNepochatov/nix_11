package ua.com.alevel.hw16.tasktwo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = createList();
        printList(list);

        PrimeNumber primeNumberFirst = new PrimeNumber(new ArrayList<>(list.subList(0, (list.size() + 1)/2)));
        PrimeNumber primeNumberSecond = new PrimeNumber(new ArrayList<>(list.subList((list.size() + 1)/2, list.size())));

        primeNumberFirst.start();
        primeNumberSecond.start();

        while (primeNumberFirst.getWorked().get() || primeNumberSecond.getWorked().get());

        int total = primeNumberFirst.getCount() + primeNumberSecond.getCount();
        System.out.println("Current " + Thread.currentThread().getName() + " total = " + total);
    }

    private static List<Integer> createList() {
        try {
            System.out.print("Size for list > ");
            int size = Integer.parseInt(BUFFERED_READER.readLine());
            List<Integer> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                list.add(RANDOM.nextInt(100));
            }

            return list;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printList(List<Integer> list) {
        list.forEach(element -> System.out.print(element + " "));
        System.out.println();
    }
}
