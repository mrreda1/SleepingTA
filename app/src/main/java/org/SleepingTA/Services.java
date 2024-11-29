package org.SleepingTA;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Services {
    private static int numChairs, numTAs, numStudents;
    private static Semaphore Chairs, TAs;
    /**
     * A thread-safe integer counter that can be used for atomic operations.
     * This counter is initialized to 0,
     * and indicates the number of running students' threads
     */
    protected static AtomicInteger atomicInt = new AtomicInteger(0);

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

    /**
     * Takes user input and initializes the number of students, chairs, and TAs.
     * P.S. This method is used by the GUI.
     *
     * @param students
     * @param chairs
     * @param tas
     */
    public static void takeUserInput(int students, int chairs, int tas) {
        numStudents = students;
        numChairs = chairs;
        numTAs = tas;

        Chairs = new Semaphore(numChairs);
        TAs = new Semaphore(numTAs);
    }

    public static void createStudentThreads() {
        Services.createStudentThreads(0);
    }

    public static void createStudentThreads(int taWaitInterval) {
        for (int i = 1; i <= numStudents; i++) {
            Student student = new Student(i, taWaitInterval);
            student.start();
        }
    }

    public static Map<String, Integer> getInfo(boolean display) {
        int sleepingTA, waitingStudents, workingTA, laterStudents;

        sleepingTA = TAs.availablePermits();
        waitingStudents = numChairs - Chairs.availablePermits();
        workingTA = numTAs - TAs.availablePermits();
        laterStudents = atomicInt.get();

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
        System.out.println(Services.atomicInt.get());
    }

    public static Semaphore getChairs() {
        return Chairs;
    }

    public static Semaphore getTAs() {
        return TAs;
    }
}
