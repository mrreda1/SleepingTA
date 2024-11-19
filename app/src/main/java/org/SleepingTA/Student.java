package org.SleepingTA;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Student
 */
public class Student extends Thread {
    @SuppressWarnings("unused")
    private int id;

    public Student(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            if (App.getChairs().tryAcquire()) {
                try {
                    App.getTAs().acquire();
                    App.getChairs().release();
                    Thread.sleep(ThreadLocalRandom.current().nextInt(5, 8) * 1000);
                    App.getTAs().release();
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                // Try again after random period of time
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(3, 8) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
