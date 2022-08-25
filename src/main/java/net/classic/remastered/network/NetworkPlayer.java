/*
 * 
 */
package net.classic.remastered.network;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.gui.FontRenderer;
import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.creature.HumanoidMob;
import net.classic.remastered.network.PositionUpdate;
import net.classic.remastered.network.SkinDownloadThread;

public class NetworkPlayer
extends HumanoidMob {
    public static final long serialVersionUID = 77479605454997290L;
    private List moveQueue = new LinkedList();
    private Minecraft minecraft;
    private int xp;
    private int yp;
    private int zp;
    private transient int a = -1;
    public transient BufferedImage newTexture = null;
    public String name;
    public String displayName;
    int tickCount = 0;
    private TextureManager textures;

    public NetworkPlayer(Minecraft var1, int var2, String var3, int var4, int var5, int var6, float var7, float var8) {
        super(var1.level, var4, var5, var6);
        this.minecraft = var1;
        this.displayName = var3;
        this.name = var3 = FontRenderer.stripColor(var3);
        this.xp = var4;
        this.yp = var5;
        this.zp = var6;
        this.heightOffset = 0.0f;
        this.pushthrough = 0.8f;
        this.setPos((float)var4 / 32.0f, (float)var5 / 32.0f, (float)var6 / 32.0f);
        this.xRot = var8;
        this.yRot = var7;
        this.renderOffset = 0.6875f;
        new SkinDownloadThread(this).start();
        this.allowAlpha = false;
    }

    @Override
    public void aiStep() {
        int var1 = 5;
        do {
            if (this.moveQueue.size() <= 0) continue;
            this.setPos((PositionUpdate)this.moveQueue.remove(0));
        } while (var1-- > 0 && this.moveQueue.size() > 10);
        this.onGround = true;
    }

    @Override
    public void bindTexture(TextureManager var1) {
        this.textures = var1;
        if (this.newTexture != null) {
            boolean var10001;
            BufferedImage var2 = this.newTexture;
            int[] var3 = new int[512];
            var2.getRGB(32, 0, 32, 16, var3, 0, 32);
            int var5 = 0;
            while (true) {
                if (var5 >= var3.length) {
                    var10001 = false;
                    break;
                }
                if (var3[var5] >>> 24 < 128) {
                    var10001 = true;
                    break;
                }
                ++var5;
            }
            this.hasHair = var10001;
            this.a = var1.load(this.newTexture);
            this.newTexture = null;
        }
        if (this.a < 0) {
            GL11.glBindTexture(3553, var1.load("/char.png"));
        } else {
            GL11.glBindTexture(3553, this.a);
        }
    }

    @Override
    public void renderHover(TextureManager var1, float var2) {
        FontRenderer var3 = this.minecraft.fontRenderer;
        GL11.glPushMatrix();
        GL11.glTranslatef(this.xo + (this.x - this.xo) * var2, this.yo + (this.y - this.yo) * var2 + 0.8f + this.renderOffset, this.zo + (this.z - this.zo) * var2);
        GL11.glRotatef(-this.minecraft.player.yRot, 0.0f, 1.0f, 0.0f);
        var2 = 0.05f;
        GL11.glScalef(0.05f, -var2, var2);
        GL11.glTranslatef((float)(-var3.getWidth(this.displayName)) / 2.0f, 0.0f, 0.0f);
        GL11.glNormal3f(1.0f, -1.0f, 1.0f);
        GL11.glDisable(2896);
        GL11.glDisable(16384);
        if (this.name.equalsIgnoreCase("Notch")) {
            var3.renderNoShadow(this.displayName, 0, 0, 0xFFFF00);
        } else {
            var3.renderNoShadow(this.displayName, 0, 0, 0xFFFFFF);
        }
        GL11.glDepthFunc(516);
        GL11.glDepthMask(false);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        var3.renderNoShadow(this.displayName, 0, 0, 0xFFFFFF);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glDepthFunc(515);
        GL11.glTranslatef(1.0f, 1.0f, -0.05f);
        var3.renderNoShadow(this.name, 0, 0, 0x505050);
        GL11.glEnable(16384);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
    }

    public void queue(byte var1, byte var2, byte var3, float var4, float var5) {
        float var6;
        float var7 = var5 - this.xRot;
        for (var6 = var4 - this.yRot; var6 >= 180.0f; var6 -= 360.0f) {
        }
        while (var6 < -180.0f) {
            var6 += 360.0f;
        }
        while (var7 >= 180.0f) {
            var7 -= 360.0f;
        }
        while (var7 < -180.0f) {
            var7 += 360.0f;
        }
        var6 = this.yRot + var6 * 0.5f;
        var7 = this.xRot + var7 * 0.5f;
        this.moveQueue.add(new PositionUpdate(((float)this.xp + (float)var1 / 2.0f) / 32.0f, ((float)this.yp + (float)var2 / 2.0f) / 32.0f, ((float)this.zp + (float)var3 / 2.0f) / 32.0f, var6, var7));
        this.xp += var1;
        this.yp += var2;
        this.zp += var3;
        this.moveQueue.add(new PositionUpdate((float)this.xp / 32.0f, (float)this.yp / 32.0f, (float)this.zp / 32.0f, var4, var5));
    }

    public void teleport(short var1, short var2, short var3, float var4, float var5) {
        float var6;
        float var7 = var5 - this.xRot;
        for (var6 = var4 - this.yRot; var6 >= 180.0f; var6 -= 360.0f) {
        }
        while (var6 < -180.0f) {
            var6 += 360.0f;
        }
        while (var7 >= 180.0f) {
            var7 -= 360.0f;
        }
        while (var7 < -180.0f) {
            var7 += 360.0f;
        }
        var6 = this.yRot + var6 * 0.5f;
        var7 = this.xRot + var7 * 0.5f;
        this.moveQueue.add(new PositionUpdate((float)(this.xp + var1) / 64.0f, (float)(this.yp + var2) / 64.0f, (float)(this.zp + var3) / 64.0f, var6, var7));
        this.xp = var1;
        this.yp = var2;
        this.zp = var3;
        this.moveQueue.add(new PositionUpdate((float)this.xp / 32.0f, (float)this.yp / 32.0f, (float)this.zp / 32.0f, var4, var5));
    }

    public void queue(byte var1, byte var2, byte var3) {
        this.moveQueue.add(new PositionUpdate(((float)this.xp + (float)var1 / 2.0f) / 32.0f, ((float)this.yp + (float)var2 / 2.0f) / 32.0f, ((float)this.zp + (float)var3 / 2.0f) / 32.0f));
        this.xp += var1;
        this.yp += var2;
        this.zp += var3;
        this.moveQueue.add(new PositionUpdate((float)this.xp / 32.0f, (float)this.yp / 32.0f, (float)this.zp / 32.0f));
    }

    public void queue(float var1, float var2) {
        float var3;
        float var4 = var2 - this.xRot;
        for (var3 = var1 - this.yRot; var3 >= 180.0f; var3 -= 360.0f) {
        }
        while (var3 < -180.0f) {
            var3 += 360.0f;
        }
        while (var4 >= 180.0f) {
            var4 -= 360.0f;
        }
        while (var4 < -180.0f) {
            var4 += 360.0f;
        }
        var3 = this.yRot + var3 * 0.5f;
        var4 = this.xRot + var4 * 0.5f;
        this.moveQueue.add(new PositionUpdate(var3, var4));
        this.moveQueue.add(new PositionUpdate(var1, var2));
    }

    public void clear() {
        if (this.a >= 0 && this.textures != null) {
            TextureManager var10000 = this.textures;
            int var1 = this.a;
            TextureManager var2 = this.textures;
            var10000.textureImages.remove(var1);
            var2.idBuffer.clear();
            var2.idBuffer.put(var1);
            var2.idBuffer.flip();
            GL11.glDeleteTextures(var2.idBuffer);
        }
    }
}

