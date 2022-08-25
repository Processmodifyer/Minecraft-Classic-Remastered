/*
 * 
 */
package net.classic.remastered.network;

public class PositionUpdate {
    public float x;
    public float y;
    public float z;
    public float yaw;
    public float pitch;
    public boolean rotation = false;
    public boolean position = false;

    public PositionUpdate(float x, float y, float z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.rotation = true;
        this.position = true;
    }

    public PositionUpdate(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.position = true;
        this.rotation = false;
    }

    public PositionUpdate(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.rotation = true;
        this.position = false;
    }
}

