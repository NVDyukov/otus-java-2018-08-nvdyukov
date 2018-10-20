package ru.otus;


import java.util.*;

public class MyClass {
    public static final long DELAY = 60000;
    public static final long PERIOD = 60000;
    public static final int SIZE = 1_000_000;

    public static void main(String[] args) {
        ListenerGC listenerGC = new ListenerGC();
        ArrayList<Object> objects = new ArrayList<>();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                listenerGC.writeToLog();
                System.out.println();
            }
        };

        timer.schedule(timerTask, DELAY, PERIOD);

        try {
            while (true) {
                objects.addAll(Collections.nCopies(SIZE, new Object()));
                objects.subList(0, SIZE >> 2).clear();
            }
        } catch (OutOfMemoryError e) {
            timerTask.run();
            e.printStackTrace();
        } finally {
            timerTask.cancel();
            timer.purge();
            timer.cancel();
        }
    }
}
