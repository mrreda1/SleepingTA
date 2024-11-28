package org.SleepingTA;

import org.SleepingTA.GUI.Main;

public class App {
    public static void main(String[] args) {
        Thread guiThread = new Thread(() -> Main.launch(Main.class));
        guiThread.setDaemon(true);
        guiThread.start();

        Services.takeUserInput();
        Services.createStudentThreads();

        while (true) {
            var info = Services.getInfo(true);

            for (var entry : info.entrySet()) {
                // System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            // Set menu's update time interval to one second.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
