/*
 * 
 */
package net.classic.remastered.game.world;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.main.StopGameException;
import net.classic.remastered.client.render.ShapeRenderer;

public final class ProgressBarDisplay {
    private String text = "";
    private Minecraft minecraft;
    public static String title = "";
    private long start = System.currentTimeMillis();

    public ProgressBarDisplay(Minecraft var1) {
        this.minecraft = var1;
    }

    public final void setTitle(String var1) {
        if (!this.minecraft.running) {
            throw new StopGameException();
        }
        title = var1;
        int var3 = this.minecraft.width * 240 / this.minecraft.height;
        int var2 = this.minecraft.height * 240 / this.minecraft.height;
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, var3, var2, 0.0, 100.0, 300.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -200.0f);
    }

    public final void setText(String var1) {
        if (!this.minecraft.running) {
            throw new StopGameException();
        }
        this.text = var1;
        this.setProgress(-1);
    }

    public final void setProgress(int var1) {
        if (!this.minecraft.running) {
            throw new StopGameException();
        }
        long var2 = System.currentTimeMillis();
        if (var2 - this.start < 0L || var2 - this.start >= 20L) {
            this.start = var2;
            int var4 = this.minecraft.width * 240 / this.minecraft.height;
            int var5 = this.minecraft.height * 240 / this.minecraft.height;
            GL11.glClear(16640);
            ShapeRenderer var6 = ShapeRenderer.instance;
            int var7 = this.minecraft.textureManager.load("/dirt.png");
            GL11.glBindTexture(3553, var7);
            float var10 = 32.0f;
            var6.begin();
            var6.color(0x404040);
            var6.vertexUV(0.0f, var5, 0.0f, 0.0f, (float)var5 / var10);
            var6.vertexUV(var4, var5, 0.0f, (float)var4 / var10, (float)var5 / var10);
            var6.vertexUV(var4, 0.0f, 0.0f, (float)var4 / var10, 0.0f);
            var6.vertexUV(0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            var6.end();
            if (var1 >= 0) {
                var7 = var4 / 2 - 50;
                int var8 = var5 / 2 + 16;
                GL11.glDisable(3553);
                var6.begin();
                var6.color(0x808080);
                var6.vertex(var7, var8, 0.0f);
                var6.vertex(var7, var8 + 2, 0.0f);
                var6.vertex(var7 + 100, var8 + 2, 0.0f);
                var6.vertex(var7 + 100, var8, 0.0f);
                var6.color(0x80FF80);
                var6.vertex(var7, var8, 0.0f);
                var6.vertex(var7, var8 + 2, 0.0f);
                var6.vertex(var7 + var1, var8 + 2, 0.0f);
                var6.vertex(var7 + var1, var8, 0.0f);
                var6.end();
                GL11.glEnable(3553);
            }
            this.minecraft.fontRenderer.render(title, (var4 - this.minecraft.fontRenderer.getWidth(title)) / 2, var5 / 2 - 4 - 16, 0xFFFFFF);
            this.minecraft.fontRenderer.render(this.text, (var4 - this.minecraft.fontRenderer.getWidth(this.text)) / 2, var5 / 2 - 4 + 8, 0xFFFFFF);
            Display.update();
            try {
                Thread.yield();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

