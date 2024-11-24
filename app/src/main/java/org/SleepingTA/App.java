package org.SleepingTA;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class App {
    private static int numChairs, numTAs, numStudents;
    private static Semaphore Chairs, TAs;

    public static void main(String[] args) {
        takeUserInput();
        createStudentThreads();

        while (true) {
            displayInfo();

            // Set menu's update time interval to one second.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void takeUserInput() {
        Scanner input = new Scanner(System.in);
        System.out.print("Number of Students: ");
        numStudents = input.nextInt();
        System.out.print("Number of chairs: ");
        numChairs = input.nextInt();
        System.out.print("Number of TAs: ");
        numTAs = input.nextInt();
        input.close();
        Chairs = new Semaphore(numChairs);
        TAs = new Semaphore(numTAs);

    }

    private static void createStudentThreads() {
        for (int i = 1; i <= numStudents; i++) {
            Student student = new Student(i);
            student.start();
        }
    }

    private static void displayInfo() {
        int sleep, waiting, working, later;
        sleep = TAs.availablePermits();
        waiting = numChairs - Chairs.availablePermits();
        working = numTAs - TAs.availablePermits();

        // exclude completed, on chairs and working threads
        later = Thread.activeCount() - waiting - working - 1;

        System.out.print("\033[H\033[2J");
        System.out.println("TAs working: " + working);
        System.out.println("TAs sleeping: " + sleep);
        System.out.println("Students waiting on chairs: " + waiting);
        System.out.println("Students that will come later: " + later);
    }

    public static Semaphore getChairs() {
        return Chairs;
    }

    public static Semaphore getTAs() {
        return TAs;
    }
}
