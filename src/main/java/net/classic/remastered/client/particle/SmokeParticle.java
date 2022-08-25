/*
 * 
 */
package net.classic.remastered.client.particle;

import net.classic.remastered.client.particle.Particle;
import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.game.world.World;

public class SmokeParticle
extends Particle {
    private static final long serialVersionUID = 1L;

    public SmokeParticle(World var1, float var2, float var3, float var4) {
        super(var1, var2, var3, var4, 0.0f, 0.0f, 0.0f);
        this.xd *= 0.1f;
        this.yd *= 0.1f;
        this.zd *= 0.1f;
        this.gCol = this.bCol = (float)(Math.random() * (double)0.3f);
        this.rCol = this.bCol;
        this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.noPhysics = true;
    }

    @Override
    public void render(ShapeRenderer var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        super.render(var1, var2, var3, var4, var5, var6, var7);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
        this.tex = 7 - (this.age << 3) / this.lifetime;
        this.yd = (float)((double)this.yd + 0.004);
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.96f;
        this.yd *= 0.96f;
        this.zd *= 0.96f;
        if (this.onGround) {
            this.xd *= 0.7f;
            this.zd *= 0.7f;
        }
    }
}

