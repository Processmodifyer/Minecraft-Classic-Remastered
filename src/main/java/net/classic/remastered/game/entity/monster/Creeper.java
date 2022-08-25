/*
 * 
 */
package net.classic.remastered.game.entity.monster;

import net.classic.remastered.client.particle.TerrainParticle;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.entity.monster.ai.base.BasicAttackAI;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;
import net.dungland.util.MathHelper;

public class Creeper
extends LivingEntity {


    public Creeper(World var1, float var2, float var3, float var4) {
        super(var1);
        this.heightOffset = 1.62f;
        this.modelName = "creeper";
        this.textureName = "/mob/creeper.png";
        this.ai = new CreeperAI(this);
        this.ai.defaultLookAngle = 45;
        this.deathScore = 200;
        this.setPos(var2, var3, var4);
    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (this.health <= 0) {
            this.level.playSound("mob.creeperdeath", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        if (this.health >= 0) {
            this.level.playSound("mob.creeper", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        super.hurt(var1, var2);
    }

	public void fuse() {
		
	}

final class CreeperAI
extends BasicAttackAI {

    final Creeper creeper;

    CreeperAI(Creeper var1) {
        this.creeper = var1;
    }

    @Override
    public final boolean attack(Entity var1) {
        if (!super.attack(var1)) {
            return false;
        }
        		
        return true;
    }

    @Override
    public final void beforeRemove() {
        float var1 = 4.0f;
        this.level.explode(this.mob, this.mob.x, this.mob.y, this.mob.z, var1);
        this.level.playSound("random.explode", this.mob.x, this.mob.y, this.mob.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        for (int var2 = 0; var2 < 500; ++var2) {
            float var3 = (float)this.random.nextGaussian() * var1 / 4.0f;
            float var4 = (float)this.random.nextGaussian() * var1 / 4.0f;
            float var5 = (float)this.random.nextGaussian() * var1 / 4.0f;
            float var6 = MathHelper.sqrt(var3 * var3 + var4 * var4 + var5 * var5);
            float var7 = var3 / var6 / var6;
            float var8 = var4 / var6 / var6;
            var6 = var5 / var6 / var6;
            this.level.particleEngine.spawnParticle(new TerrainParticle(this.level, this.mob.x + var3, this.mob.y + var4, this.mob.z + var5, var7, var8, var6, Block.LEAVES));
        }
    }
}


}

