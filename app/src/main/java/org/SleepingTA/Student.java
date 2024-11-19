package org.SleepingTA;

class Student extends Thread {
    private int id;
    private WaitingArea waitingArea;

    public Student(int id, WaitingArea waitingArea) {
        this.id = id;
        this.waitingArea = waitingArea;
    }

    @Override
    public void run() {
        while (true) {
            if (waitingArea.tryToEnterOffice()) {
                System.out.println("Student " + id + " is being helped by the TA.");
                waitingArea.leaveOffice();
                break;
            } else {
                System.out.println("Student " + id + " found no chair, will retry later.");
                try {
                    Thread.sleep(1000); // Retry after a while
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
