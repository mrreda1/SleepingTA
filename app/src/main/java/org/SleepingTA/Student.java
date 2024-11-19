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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                App.getChairs().release();
                randomWait(5, 8); // Stay with a TA for a while (make it realistic)
                App.getTAs().release();
                return;
            } else {
                randomWait(3, 8); // Try again after random period of time
            }
        }
    }

    private void randomWait(int min, int max) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(min, max + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
