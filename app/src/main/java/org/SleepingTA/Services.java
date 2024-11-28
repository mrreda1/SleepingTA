package org.SleepingTA;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import org.checkerframework.checker.units.qual.t;

public class Services {
    private static int numChairs, numTAs, numStudents;
    private static Semaphore Chairs, TAs;

    protected static void takeUserInput() {
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

    protected static void createStudentThreads() {
        for (int i = 1; i <= numStudents; i++) {
            Student student = new Student(i);
            student.start();
        }
    }

    public static Map<String, Integer> getInfo(boolean display) {
        int sleepingTA, waitingStudents, workingTA, laterStudents;

        sleepingTA = TAs.availablePermits();
        waitingStudents = numChairs - Chairs.availablePermits();
        workingTA = numTAs - TAs.availablePermits();

        // exclude completed, on chairs and working threads
        laterStudents = Thread.activeCount() - waitingStudents - workingTA - 1;

        if (display)
            Services.displayInfo(sleepingTA, waitingStudents, workingTA, laterStudents);

        return Map.of(
                "sleepingTA", sleepingTA,
                "workingTA", workingTA,
                "waitingStudents", waitingStudents,
                "laterStudents", laterStudents);
    }

    private static void displayInfo(int sleep, int waiting, int working, int later) {
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
