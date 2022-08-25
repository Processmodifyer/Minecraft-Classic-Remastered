/*
 * 
 */
package net.classic.remastered.client.gui;

import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.gui.FontRenderer;
import net.classic.remastered.client.render.ShapeRenderer;

public class Screen {
    protected float imgZ = 0.0f;

    protected static void drawBox(int var0, int var1, int var2, int var3, int var4) {
        float var5 = (float)(var4 >>> 24) / 255.0f;
        float var6 = (float)(var4 >> 16 & 0xFF) / 255.0f;
        float var7 = (float)(var4 >> 8 & 0xFF) / 255.0f;
        float var9 = (float)(var4 & 0xFF) / 255.0f;
        ShapeRenderer var8 = ShapeRenderer.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(var6, var7, var9, var5);
        var8.begin();
        var8.vertex(var0, var3, 0.0f);
        var8.vertex(var2, var3, 0.0f);
        var8.vertex(var2, var1, 0.0f);
        var8.vertex(var0, var1, 0.0f);
        var8.end();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    protected static void drawFadingBox(int var0, int var1, int var2, int var3, int var4, int var5) {
        float var6 = (float)(var4 >>> 24) / 255.0f;
        float var7 = (float)(var4 >> 16 & 0xFF) / 255.0f;
        float var8 = (float)(var4 >> 8 & 0xFF) / 255.0f;
        float var12 = (float)(var4 & 0xFF) / 255.0f;
        float var9 = (float)(var5 >>> 24) / 255.0f;
        float var10 = (float)(var5 >> 16 & 0xFF) / 255.0f;
        float var11 = (float)(var5 >> 8 & 0xFF) / 255.0f;
        float var13 = (float)(var5 & 0xFF) / 255.0f;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glBegin(7);
        GL11.glColor4f(var7, var8, var12, var6);
        GL11.glVertex2f(var2, var1);
        GL11.glVertex2f(var0, var1);
        GL11.glColor4f(var10, var11, var13, var9);
        GL11.glVertex2f(var0, var3);
        GL11.glVertex2f(var2, var3);
        GL11.glEnd();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
    }

    public static void drawCenteredString(FontRenderer var0, String var1, int var2, int var3, int var4) {
        var0.render(var1, var2 - var0.getWidth(var1) / 2, var3, var4);
    }

    public static void drawString(FontRenderer var0, String var1, int var2, int var3, int var4) {
        var0.render(var1, var2, var3, var4);
    }

    public final void drawImage(int var1, int var2, int var3, int var4, int var5, int var6) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        ShapeRenderer var9 = ShapeRenderer.instance;
        ShapeRenderer.instance.begin();
        var9.vertexUV(var1, var2 + var6, this.imgZ, (float)var3 * var7, (float)(var4 + var6) * var8);
        var9.vertexUV(var1 + var5, var2 + var6, this.imgZ, (float)(var3 + var5) * var7, (float)(var4 + var6) * var8);
        var9.vertexUV(var1 + var5, var2, this.imgZ, (float)(var3 + var5) * var7, (float)var4 * var8);
        var9.vertexUV(var1, var2, this.imgZ, (float)var3 * var7, (float)var4 * var8);
        var9.end();
    }
}

