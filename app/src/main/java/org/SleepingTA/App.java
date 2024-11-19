package org.SleepingTA;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Number of students: ");
        int numStudents = input.nextInt();
        System.out.print("Number of chairs: ");
        int numChairs = input.nextInt();
        System.out.print("Number of Teaching assistants: ");
        int numTAs = input.nextInt();
        input.close();

        WaitingArea waitingArea = new WaitingArea(numChairs);

        // Start the TA thread
        for (int i = 1; i <= numTAs; i++) {
            TA ta = new TA(waitingArea);
            ta.start();
        }

        // Start student threads
        for (int i = 1; i <= numStudents; i++) {
            Student student = new Student(i, waitingArea);
            student.start();

            try {
                Thread.sleep(500); // Stagger student arrivals
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
