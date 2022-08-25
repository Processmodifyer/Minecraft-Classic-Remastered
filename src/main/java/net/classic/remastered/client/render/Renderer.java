/*
 * 
 */
package net.classic.remastered.client.render;

import net.classic.remastered.client.main.Minecraft;
import net.classic.remastered.client.model.Vec3D;
import net.classic.remastered.client.render.HeldBlock;
import net.classic.remastered.client.settings.GameSettings;
import net.classic.remastered.client.settings.ThirdPersonMode;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.phys.MovingObjectPosition;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.liquid.LiquidType;
import net.classic.remastered.game.world.tile.Block;
import net.dungland.util.MathHelper;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.Marshaller.Listener;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class Renderer {
    public Minecraft minecraft;
    public float fogColorMultiplier = 1.0f;
    public boolean displayActive = false;
    public float fogEnd = 0.0f;
    public HeldBlock heldBlock;
    public int levelTicks;
    public Entity entity = null;
    public Random random = new Random();
    private volatile int unused1 = 0;
    private volatile int unused2 = 0;
    private FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    public float fogRed;
    public float fogBlue;
    public float fogGreen;
	private World world;
	private TextureManager texturemanager;
	private int glSkyList;
	private float float2;
	private float n2;
    private transient ArrayList<LevelRenderer> listeners;
	private int var1;

    public Renderer(Minecraft var1) {
        this.minecraft = var1;
        this.heldBlock = new HeldBlock(var1);
    }
    public Renderer(Object a) {
    }

    public Vec3D getPlayerVector(float var1) {
        Player var4 = this.minecraft.player;
        float var2 = var4.xo + (var4.x - var4.xo) * var1;
        float var3 = var4.yo + (var4.y - var4.yo) * var1;
        float var5 = var4.zo + (var4.z - var4.zo) * var1;
        return new Vec3D(var2, var3, var5);
    }

    public void hurtEffect(float var1) {
        Player var3 = this.minecraft.player;
        float var2 = (float)var3.hurtTime - var1;
        if (var3.health <= 0) {
            GL11.glRotatef(40.0f - 8000.0f / ((var1 += (float)var3.deathTime) + 200.0f), 0.0f, 0.0f, 1.0f);
        }
        if (var2 >= 0.0f) {
            var2 /= (float)var3.hurtDuration;
            var2 = MathHelper.sin(var2 * var2 * var2 * var2 * (float)Math.PI);
            var1 = var3.hurtDir;
            GL11.glRotatef(-var3.hurtDir, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var2 * 24.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(var1, 0.0f, 1.0f, 0.0f);
        }
    }

    public void applyBobbing(float var1) {
        Player var4 = this.minecraft.player;
        float var2 = var4.walkDist - var4.walkDistO;
        var2 = var4.walkDist + var2 * var1;
        float var3 = var4.oBob + (var4.bob - var4.oBob) * var1;
        float var5 = var4.oTilt + (var4.tilt - var4.oTilt) * var1;
        GL11.glTranslatef(MathHelper.sin(var2 * (float)Math.PI) * var3 * 0.5f, -Math.abs(MathHelper.cos(var2 * (float)Math.PI) * var3), 0.0f);
        GL11.glRotatef(MathHelper.sin(var2 * (float)Math.PI) * var3 * 3.0f, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(Math.abs(MathHelper.cos(var2 * (float)Math.PI + 0.2f) * var3) * 5.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(var5, 1.0f, 0.0f, 0.0f);
    }

    public final void setLighting(boolean var1) {
        if (!var1) {
            GL11.glDisable(2896);
            GL11.glDisable(16384);
        } else {
            GL11.glEnable(2896);
            GL11.glEnable(16384);
            GL11.glEnable(2903);
            GL11.glColorMaterial(1032, 5634);
            float var4 = 0.7f;
            float var2 = 0.3f;
            Vec3D var3 = new Vec3D(0.0f, -1.0f, 0.5f).normalize();
            GL11.glLight(16384, 4611, this.createBuffer(var3.x, var3.y, var3.z, 0.0f));
            GL11.glLight(16384, 4609, this.createBuffer(var2, var2, var2, 1.0f));
            GL11.glLight(16384, 4608, this.createBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glLightModel(2899, this.createBuffer(var4, var4, var4, 1.0f));
        }
    }

    public final void enableGuiMode() {
        int var1 = this.minecraft.width * 240 / this.minecraft.height;
        int var2 = this.minecraft.height * 240 / this.minecraft.height;
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, var1, var2, 0.0, 100.0, 300.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -200.0f);
    }

    public void updateFog() {
        World var1 = this.minecraft.level;
        Player var2 = this.minecraft.player;
        GL11.glFog(2918, this.createBuffer(this.fogRed, this.fogBlue, this.fogGreen, 1.0f));
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Block var5 = Block.blocks[var1.getTile((int)var2.x, (int)(var2.y + 0.12f), (int)var2.z)];
        if (var5 != null && var5.getLiquidType() != LiquidType.NOT_LIQUID) {
            LiquidType var6 = var5.getLiquidType();
            GL11.glFogi(2917, 2048);
            if (var6 == LiquidType.WATER) {
                GL11.glFogf(2914, 0.1f);
                float var7 = 0.4f;
                float var8 = 0.4f;
                float var3 = 0.9f;
                if (this.minecraft.settings.anaglyph) {
                    float var4 = (var7 * 30.0f + var8 * 59.0f + var3 * 11.0f) / 100.0f;
                    var8 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
                    var3 = (var7 * 30.0f + var3 * 70.0f) / 100.0f;
                    var7 = var4;
                }
                GL11.glLightModel(2899, this.createBuffer(var7, var8, var3, 1.0f));
            } else if (var6 == LiquidType.LAVA) {
                GL11.glFogf(2914, 2.0f);
                float var7 = 0.4f;
                float var8 = 0.3f;
                float var3 = 0.3f;
                if (this.minecraft.settings.anaglyph) {
                    float var4 = (var7 * 30.0f + var8 * 59.0f + var3 * 11.0f) / 100.0f;
                    var8 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
                    var3 = (var7 * 30.0f + var3 * 70.0f) / 100.0f;
                    var7 = var4;
                }
                GL11.glLightModel(2899, this.createBuffer(var7, var8, var3, 1.0f));
            }
        } else {
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, 0.0f);
            GL11.glFogf(2916, this.fogEnd);
            GL11.glLightModel(2899, this.createBuffer(1.0f, 1.0f, 1.0f, 1.0f));
        }
        GL11.glEnable(2903);
        GL11.glColorMaterial(1028, 4608);
    }

    public void setCamera(float delta, MovingObjectPosition selected) {
        GameSettings settings = minecraft.settings;
        Player player = minecraft.player;
        applyBobbing(delta);

        float cameraDistance = -5.1F;
        if (selected != null && settings.thirdPersonMode == ThirdPersonMode.FRONT_FACING) {
            cameraDistance = -(selected.vec.distance(getPlayerVector(delta)) - 0.51F);
            if (cameraDistance < -5.1F) {
                cameraDistance = -5.1F;
            }
        }

        if (settings.thirdPersonMode == ThirdPersonMode.NONE) {
            GL11.glTranslatef(0F, 0F, -0.1F);
        } else {
            GL11.glTranslatef(0F, 0F, cameraDistance);
        }
        if (settings.thirdPersonMode == ThirdPersonMode.FRONT_FACING) {
            GL11.glRotatef(-player.xRotO + (player.xRot - player.xRotO) * delta, 1F, 0F, 0F);
            GL11.glRotatef(player.yRotO + (player.yRot - player.yRotO) * delta + 180, 0F, 1F, 0F);
        } else {
            GL11.glRotatef(player.xRotO + (player.xRot - player.xRotO) * delta, 1F, 0F, 0F);
            GL11.glRotatef(player.yRotO + (player.yRot - player.yRotO) * delta, 0F, 1F, 0F);
        }
        float cameraX = player.xo + (player.x - player.xo) * delta;
        float cameraY = player.yo + (player.y - player.yo) * delta;
        float cameraZ = player.zo + (player.z - player.zo) * delta;
    }
    
    private FloatBuffer createBuffer(float var1, float var2, float var3, float var4) {
        this.buffer.clear();
        this.buffer.put(var1).put(var2).put(var3).put(var4);
        this.buffer.flip();
        return this.buffer;
    }
}

