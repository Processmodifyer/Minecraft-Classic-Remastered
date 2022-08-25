/*
 * 
 */
package net.classic.remastered.client.gui;

import net.classic.remastered.client.gui.Screen;

public class Button
extends Screen {
    int width = 200;
    int height = 20;
    public int x;
    public int y;
    public String text;
    public int id;
    public boolean active = true;
    public boolean visible = true;

    public Button(int var1, int var2, int var3, String var4) {
        this(var1, var2, var3, 200, 20, var4);
    }

    protected Button(int var1, int var2, int var3, int var4, int var5, String var6) {
        this.id = var1;
        this.x = var2;
        this.y = var3;
        this.width = var4;
        this.height = 20;
        this.text = var6;
    }
}

