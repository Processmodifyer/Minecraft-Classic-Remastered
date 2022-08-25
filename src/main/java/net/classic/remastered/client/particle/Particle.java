/*
 * 
 */
package net.classic.remastered.client.particle;

import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.world.World;
import net.dungland.util.MathHelper;

public class Particle
extends Entity {
    private static final long serialVersionUID = 1L;
    protected float xd;
    protected float yd;
    protected float zd;
    protected int tex;
    protected float uo;
    protected float vo;
    protected int age = 0;
    protected int lifetime = 0;
    protected float size;
    protected float gravity;
    protected float rCol;
    protected float gCol;
    protected float bCol;

    public Particle(World var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        super(var1);
        this.setSize(0.2f, 0.2f);
        this.heightOffset = this.bbHeight / 2.0f;
        this.setPos(var2, var3, var4);
        this.bCol = 1.0f;
        this.gCol = 1.0f;
        this.rCol = 1.0f;
        this.xd = var5 + (float)(Math.random() * 2.0 - 1.0) * 0.4f;
        this.yd = var6 + (float)(Math.random() * 2.0 - 1.0) * 0.4f;
        this.zd = var7 + (float)(Math.random() * 2.0 - 1.0) * 0.4f;
        float var8 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        var2 = MathHelper.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
        this.xd = this.xd / var2 * var8 * 0.4f;
        this.yd = this.yd / var2 * var8 * 0.4f + 0.1f;
        this.zd = this.zd / var2 * var8 * 0.4f;
        this.uo = (float)Math.random() * 3.0f;
        this.vo = (float)Math.random() * 3.0f;
        this.size = (float)(Math.random() * 0.5 + 0.5);
        this.lifetime = (int)(4.0 / (Math.random() * 0.9 + 0.1));
        this.age = 0;
        this.makeStepSound = false;
    }

    public Particle setPower(float var1) {
        this.xd *= var1;
        this.yd = (this.yd - 0.1f) * var1 + 0.1f;
        this.zd *= var1;
        return this;
    }

    public Particle scale(float var1) {
        this.setSize(0.2f * var1, 0.2f * var1);
        this.size *= var1;
        return this;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
        this.yd = (float)((double)this.yd - 0.04 * (double)this.gravity);
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.98f;
        this.yd *= 0.98f;
        this.zd *= 0.98f;
        if (this.onGround) {
            this.xd *= 0.7f;
            this.zd *= 0.7f;
        }
    }

    public void render(ShapeRenderer var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        float var8 = (float)(this.tex % 16) / 16.0f;
        float var9 = var8 + 0.0624375f;
        float var10 = (float)(this.tex / 16) / 16.0f;
        float var11 = var10 + 0.0624375f;
        float var12 = 0.1f * this.size;
        float var13 = this.xo + (this.x - this.xo) * var2;
        float var14 = this.yo + (this.y - this.yo) * var2;
        float var15 = this.zo + (this.z - this.zo) * var2;
        var2 = this.getBrightness(var2);
        var1.color(this.rCol * var2, this.gCol * var2, this.bCol * var2);
        var1.vertexUV(var13 - var3 * var12 - var6 * var12, var14 - var4 * var12, var15 - var5 * var12 - var7 * var12, var8, var11);
        var1.vertexUV(var13 - var3 * var12 + var6 * var12, var14 + var4 * var12, var15 - var5 * var12 + var7 * var12, var8, var10);
        var1.vertexUV(var13 + var3 * var12 + var6 * var12, var14 + var4 * var12, var15 + var5 * var12 + var7 * var12, var9, var10);
        var1.vertexUV(var13 + var3 * var12 - var6 * var12, var14 - var4 * var12, var15 + var5 * var12 - var7 * var12, var9, var11);
    }

    public int getParticleTexture() {
        return 0;
    }
}

