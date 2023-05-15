package org.example;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger count1 = new AtomicInteger();
    public static AtomicInteger count2 = new AtomicInteger();
    public static AtomicInteger count3 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palidrom = new Thread(() -> {
            for (String text : texts) {
                if (isPoleandrom(text) && !isSameChar(text)) {
                    numberOfCounter(text.length());
                }
            }
        });
        palidrom.start();

        Thread sameChars = new Thread(() -> {
            for (String text : texts) {
                if (isSameChar(text)) {
                    numberOfCounter(text.length());
                }
            }
        });
        sameChars.start();

        Thread order = new Thread(() -> {
            for (String text : texts) {
                if (!isSameChar(text) && isAscendingOrder(text)) {
                    numberOfCounter(text.length());
                }
            }
        });
        order.start();
        sameChars.join();
        palidrom.join();
        order.join();
        info();

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isSameChar(String text) {

        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void numberOfCounter(int length) {
        if (length == 3) {
            count1.getAndIncrement();
        }
        if (length == 4) {
            count2.getAndIncrement();
        }
        if (length == 5) {
            count3.getAndIncrement();
        }
    }

    public static boolean isPoleandrom(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isAscendingOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static void info() {
        System.out.println("Кол-во никнеймов с длиной 3 - " + count1.get());
        System.out.println("Кол-во никнеймов с длиной 4 - " + count2.get());
        System.out.println("Кол-во никнеймов с длиной 5 - " + count3.get());
    }
}