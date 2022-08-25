/*
 * 
 */
package net.classic.remastered.client.main;

public class SleepForeverThread
extends Thread {
    public SleepForeverThread(Minecraft minecraft) {
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    Thread.sleep(Integer.MAX_VALUE);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}

