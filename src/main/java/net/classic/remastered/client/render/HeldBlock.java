/*
 * 
 */
package net.classic.remastered.client.render;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.game.world.tile.Block;

public class HeldBlock {
    public Minecraft minecraft;
    public Block block = null;
    public float pos = 0.0f;
    public float lastPos = 0.0f;
    public int offset = 0;
    public boolean moving = true;

    public HeldBlock(Minecraft minecraft) {
        this.minecraft = minecraft;
    }
}

