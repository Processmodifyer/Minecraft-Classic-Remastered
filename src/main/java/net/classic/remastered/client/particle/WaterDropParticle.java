/*
 * 
 */
package net.classic.remastered.client.particle;

import net.classic.remastered.client.particle.Particle;
import net.classic.remastered.client.render.ShapeRenderer;
import net.classic.remastered.game.world.World;

public class WaterDropParticle
extends Particle {
    private static final long serialVersionUID = 1L;

    public WaterDropParticle(World var1, float var2, float var3, float var4) {
        super(var1, var2, var3, var4, 0.0f, 0.0f, 0.0f);
        this.xd *= 0.3f;
        this.yd = (float)Math.random() * 0.2f + 0.1f;
        this.zd *= 0.3f;
        this.rCol = 1.0f;
        this.gCol = 1.0f;
        this.bCol = 1.0f;
        this.tex = 16;
        this.setSize(0.01f, 0.01f);
        this.lifetime = (int)(8.0 / (Math.random() * 0.8 + 0.2));
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
        this.yd = (float)((double)this.yd - 0.06);
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.98f;
        this.yd *= 0.98f;
        this.zd *= 0.98f;
        if (this.lifetime-- <= 0) {
            this.remove();
        }
        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.remove();
            }
            this.xd *= 0.7f;
            this.zd *= 0.7f;
        }
    }
}

