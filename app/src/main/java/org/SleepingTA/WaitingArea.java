package org.SleepingTA;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Shared Area
 **/
class WaitingArea {
    private Semaphore chairs;
    private ReentrantLock mutex = new ReentrantLock();
    private int totalChairs;

    public WaitingArea(int numChairs) {
        this.chairs = new Semaphore(numChairs);
        this.totalChairs = numChairs;
    }

    // Method to simulate a student trying to enter the TA's office
    public boolean tryToEnterOffice() {
        if (chairs.tryAcquire()) {
            mutex.lock();
            return true;
        }
        return false;
    }

    // Method for a student to leave after being helped
    public void leaveOffice() {
        mutex.unlock();
        chairs.release();
    }

    // TA's behavior of assisting students
    public void assistStudent() {
        try {
            // Check if all chairs are free
            if (chairs.availablePermits() == totalChairs) {
                System.out.println("TA is sleeping...");
                Thread.sleep(2000); // Take a nap
            } else {
                mutex.lock();
                try {
                    System.out.println("TA is helping a student...");
                    Thread.sleep(3000); // Help student
                } finally {
                    mutex.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
