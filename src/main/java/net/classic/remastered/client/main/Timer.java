/*
 * 
 */
package net.classic.remastered.client.main;

public class Timer {
    public float tps;
    public double lastHR;
    public int elapsedTicks;
    public float delta;
    public float speed = 1.0f;
    public float elapsedDelta = 0.0f;
    public long lastSysClock;
    public long lastHRClock;
    public double adjustment = 1.0;

    public Timer(float tps) {
        this.tps = tps;
        this.lastSysClock = System.currentTimeMillis();
        this.lastHRClock = System.nanoTime() / 1000000L;
    }
}

