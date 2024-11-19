package org.SleepingTA;

class TA extends Thread {
    private WaitingArea waitingArea;

    public TA(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    @Override
    public void run() {
        while (true) {
            waitingArea.assistStudent();
        }
    }
}
