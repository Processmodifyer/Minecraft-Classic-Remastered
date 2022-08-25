/*
 * 
 */
package net.classic.remastered.game.entity.monster;

import net.classic.remastered.client.particle.TerrainParticle;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.entity.monster.ai.base.goal.NeutralAI;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.entity.other.PrimedTnt;
import net.classic.remastered.game.player.InputHandlerImpl;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;
import net.dungland.util.MathHelper;

public class Speer
extends LivingEntity {

    public InputHandlerImpl inputpl;
    public boolean grazing = false;
    public int grazingTime = 0;
    public float graze;
    public float grazeO;

    public Speer(World var1, float var2, float var3, float var4) {
        super(var1);
        this.modelName = "speer";
        this.textureName = "/mob/tntyatter.png";
        this.heightOffset = 1.62f;
        SpeerAI var5 = new SpeerAI(this);
        this.deathScore = 500;
        var5.defaultLookAngle = 30;
        var5.runSpeed = 1.0f;
        this.ai = var5;
        this.setPos(var2, var3, var4);
    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (this.health <= 0) {
            this.level.playSound("random.hurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.2f);
        }
        if (this.health >= 0) {
            this.level.playSound("random.hurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.2f);
            super.hurt(var1, var2);
        }
    }

    @Override
    public void die(Entity var1) {
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Block.TNT.id));
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Block.GOLD_BLOCK.id));
            this.level.addEntity(new PrimedTnt(this.level, this.x + 0.5f, this.y + 0.5f, this.z + 0.5f));
        }
        super.die(var1);
    }
    public final class SpeerAI
    extends NeutralAI {

        final Speer Speer;
        private Player player;

        SpeerAI(Speer var1) {
            this.Speer = var1;
        }

        public final boolean attack(Entity var1, Player var2) {
            if (!super.attack(var1)) {
                return false;
            }
            this.player.awardKillScore(var2, 10);
            return true;
        }

        @Override
        public final void beforeRemove() {
            float var1 = 4.0f;
            for (int var2 = 0; var2 < 500; ++var2) {
                float var3 = (float)this.random.nextGaussian() * var1 / 4.0f;
                float var4 = (float)this.random.nextGaussian() * var1 / 4.0f;
                float var5 = (float)this.random.nextGaussian() * var1 / 4.0f;
                float var6 = MathHelper.sqrt(var3 * var3 + var4 * var4 + var5 * var5);
                float var7 = var3 / var6 / var6;
                float var8 = var4 / var6 / var6;
                var6 = var5 / var6 / var6;
                this.level.particleEngine.spawnParticle(new TerrainParticle(this.level, this.mob.x + var3, this.mob.y + var4, this.mob.z + var5, var7, var8, var6, Block.STONE));
                this.level.particleEngine.spawnParticle(new TerrainParticle(this.level, this.mob.x + var3, this.mob.y + var4, this.mob.z + var5, var7, var8, var6, Block.STONE));
            }
        }

        @Override
        protected final void update() {
            float var1 = MathHelper.sin(this.Speer.yRot * 9.141593f / 280.0f);
            float var2 = MathHelper.cos(this.Speer.yRot * 9.141593f / 280.0f);
            int var4 = (int)(this.mob.x + (var1 *= -0.7f));
            int var3 = (int)(this.mob.y - 2.0f);
            int var5 = (int)(this.mob.z + (var2 *= 0.7f));
            if (this.Speer.grazing) {
                if (this.level.getTile(var4, var3, var5) != Block.GRASS.id) {
                    this.Speer.grazing = false;
                } else {
                    if (++this.Speer.grazingTime == 60) {
                        this.level.setTile(var4, var3, var5, Block.TNT.id);
                        this.random.nextInt(5);
                    }
                    this.xxa = 0.0f;
                    this.yya = 0.0f;
                    this.mob.xRot = 40 + this.Speer.grazingTime / 2 % 2 * 20;
                }
            } else {
                if (this.level.getTile(var4, var3, var5) == Block.GRASS.id) {
                    this.Speer.grazing = true;
                    this.Speer.grazingTime = 0;
                }
                super.update();
            }
        }
    }


    
}

