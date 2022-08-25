/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.game.phys.AABB;

public class ChatScreenData {
    public float width;
    public float height;
    public float x;
    public float y;
    public String string;
    public AABB bounds;

    public ChatScreenData(float width, float height, float x, float y, String message) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.string = message;
        this.bounds = new AABB(x, y, 0.0f, width, y + height, 0.0f);
    }
}

