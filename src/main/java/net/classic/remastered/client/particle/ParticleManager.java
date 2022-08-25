/*
 * 
 */
package net.classic.remastered.client.particle;

import java.util.ArrayList;
import java.util.List;

import net.classic.remastered.client.particle.Particle;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.world.World;

public final class ParticleManager {
    public List[] particles = new List[2];
    public TextureManager textureManager;

    public ParticleManager(World var1, TextureManager var2) {
        if (var1 != null) {
            var1.particleEngine = this;
        }
        this.textureManager = var2;
        for (int var3 = 0; var3 < 2; ++var3) {
            this.particles[var3] = new ArrayList();
        }
    }

    public final void spawnParticle(Entity var1) {
        Particle var3 = (Particle)var1;
        int var2 = var3.getParticleTexture();
        this.particles[var2].add(var3);
    }

    public final void tick() {
        for (int var1 = 0; var1 < 2; ++var1) {
            for (int var2 = 0; var2 < this.particles[var1].size(); ++var2) {
                Particle var3 = (Particle)this.particles[var1].get(var2);
                var3.tick();
                if (!var3.removed) continue;
                this.particles[var1].remove(var2--);
            }
        }
    }
}

