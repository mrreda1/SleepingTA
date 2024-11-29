package org.SleepingTA;

import org.SleepingTA.GUI.Main;

public class App {
    public static void main(String[] args) {
        Thread guiThread = new Thread(() -> Main.launch(Main.class));
        guiThread.setDaemon(true);
        guiThread.start();

        try {
            guiThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Services.takeUserInput();
        // Services.createStudentThreads(0);

        // while (true) {
        // Services.getInfo(true);

        // // Set menu's update time interval to one second.
        // try {
        // Thread.sleep(1000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }
    }
}
