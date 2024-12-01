package org.SleepingTA;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Student
 */
public class Student extends Thread {
    @SuppressWarnings("unused")
    private int id;
    private int taWaitInterval;
    private volatile boolean running = true;

    public Student(int id, int waitInterval) {
        super("Student-" + id);
        this.id = id;
        if (waitInterval < 0)
            throw new IllegalArgumentException("Wait interval must be a positive integer.");

        this.taWaitInterval = waitInterval;
    }

    public Student(int id) {
        super("Student-" + id);
        this.id = id;
        this.taWaitInterval = 0;
    }

    @Override
    public void run() {
        while (running) {
            if (Services.getChairs().tryAcquire()) {
                try {
                    Services.getTAs().acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Services.getChairs().release();

                // Stay with a TA for a while (make it realistic)
                if (taWaitInterval > 0)
                    wait(taWaitInterval);
                else
                    randomWait(1, 3);

                Services.getTAs().release();

                return;
            } else {
                randomWait(1, 3); // Try again after random period of time
            }
        }
    }

    protected void terminate() {
        running = false;
    }

    protected static int countRunning() {
        int count = 0;
        for (Thread thread : Thread.getAllStackTraces().keySet())
            if (thread.getName().startsWith("Student-") && thread instanceof Student)
                count++;

        return count;
    }

    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void randomWait(int minSeconds, int maxSeconds) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(minSeconds, maxSeconds + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
