package org.SleepingTA;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int numChairs, numTAs, numStudents;
        System.out.print("Number of Students: ");
        numStudents = input.nextInt();
        System.out.print("Number of chairs: ");
        numChairs = input.nextInt();
        System.out.print("Number of TAs: ");
        numTAs = input.nextInt();
        input.close();
    }
}
