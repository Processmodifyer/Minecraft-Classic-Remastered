/*
 * 
 */
package net.classic.remastered.game.entity.creature;

import java.io.DataInput;
import java.io.IOException;
import org.lwjgl.opengl.GL11;

import net.classic.remastered.client.model.AnimalModel;
import net.classic.remastered.client.render.TextureManager;
import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.creature.QuadrupedMob;
import net.classic.remastered.game.entity.monster.ai.base.BasicAI;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.player.Player;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;
import net.dungland.util.MathHelper;

public class Sheep
extends QuadrupedMob {

    public boolean hasFur = false;
    public boolean grazing = false;
    public int grazingTime = 0;
    public float graze;
    public float grazeO;

    public Sheep(World var1, float var2, float var3, float var4) {
        super(var1, var2, var3, var4);
        this.setSize(1.4f, 1.72f);
        this.setPos(var2, var3, var4);
        this.heightOffset = 1.72f;
        this.modelName = "sheep";
        this.textureName = "/mob/sheep.png";
        this.ai = new SheepAI(this);
        this.health = 10;

    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.grazeO = this.graze;
        this.graze = this.grazing ? (this.graze += 0.2f) : (this.graze -= 0.2f);
        if (this.graze < 0.0f) {
            this.graze = 0.0f;
        }
        if (this.graze > 1.0f) {
            this.graze = 1.0f;
        }
    }

    @Override
    public void die(Entity var1) {
        if (var1 != null) {
            var1.awardKillScore(this, 10);
        }
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Block.BROWN_MUSHROOM.id));
        }
        super.die(var1);
    }

    @Override
    public void hurt(Entity var1, int var2) {
        this.level.playSound("mob.sheep", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        if (this.hasFur && var1 instanceof Player) {
            this.hasFur = false;
            int var3 = (int)(Math.random() * 3.0 + 1.0);
            for (var2 = 0; var2 < var3; ++var2) {
                this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Block.WHITE_WOOL.id));
            }
        } else {
            if (this.health <= 0) {
                this.level.playSound("mob.sheep", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
            }
            if (this.health >= 0) {
                this.level.playSound("mob.sheep", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
            }
            super.hurt(var1, var2);
        }
    }

    public void read(DataInput in) throws IOException {
    }

    @Override
    public void renderModel(TextureManager var1, float var2, float var3, float var4, float var5, float var6, float var7) {
        AnimalModel var8 = (AnimalModel)modelCache.getModel(this.modelName);
        float var9 = var8.head.y;
        float var10 = var8.head.z;
        var8.head.y += (this.grazeO + (this.graze - this.grazeO) * var3) * 8.0f;
        var8.head.z -= this.grazeO + (this.graze - this.grazeO) * var3;
        super.renderModel(var1, var2, var3, var4, var5, var6, var7);
        if (this.hasFur) {
            GL11.glBindTexture(3553, var1.load("/mob/sheep_fur.png"));
            GL11.glDisable(2884);
            AnimalModel var11 = (AnimalModel)modelCache.getModel("sheep.fur");
            ((AnimalModel)modelCache.getModel("sheep.fur")).head.yaw = var8.head.yaw;
            var11.head.pitch = var8.head.pitch;
            var11.head.y = var8.head.y;
            var11.head.x = var8.head.x;
            var11.body.yaw = var8.body.yaw;
            var11.body.pitch = var8.body.pitch;
            var11.leg1.pitch = var8.leg1.pitch;
            var11.leg2.pitch = var8.leg2.pitch;
            var11.leg3.pitch = var8.leg3.pitch;
            var11.leg4.pitch = var8.leg4.pitch;
            var11.head.render(var7);
            var11.body.render(var7);
            var11.leg1.render(var7);
            var11.leg2.render(var7);
            var11.leg3.render(var7);
            var11.leg4.render(var7);
        }
        var8.head.y = var9;
        var8.head.z = var10;
    }
    final class SheepAI
    extends BasicAI {
        private static final long serialVersionUID = 1L;
        final Sheep sheep;

        SheepAI(Sheep var1) {
            this.sheep = var1;
        }

        @Override
        protected final void update() {
            float var1 = MathHelper.sin(this.sheep.yRot * (float)Math.PI / 180.0f);
            float var2 = MathHelper.cos(this.sheep.yRot * (float)Math.PI / 180.0f);
            int var4 = (int)(this.mob.x + (var1 *= -0.7f));
            int var3 = (int)(this.mob.y - 2.0f);
            int var5 = (int)(this.mob.z + (var2 *= 0.7f));
            if (this.sheep.grazing) {
                if (this.level.getTile(var4, var3, var5) != Block.GRASS.id) {
                    this.sheep.grazing = false;
                } else {
                    if (++this.sheep.grazingTime == 60) {
                        this.level.setTile(var4, var3, var5, Block.DIRT.id);
                        if (this.random.nextInt(5) == 0) {
                            this.sheep.hasFur = true;
                        }
                    }
                    this.xxa = 0.0f;
                    this.yya = 0.0f;
                    this.mob.xRot = 40 + this.sheep.grazingTime / 2 % 2 * 10;
                }
            } else {
                if (this.level.getTile(var4, var3, var5) == Block.GRASS.id) {
                    this.sheep.grazing = true;
                    this.sheep.grazingTime = 0;
                }
                super.update();
            }
        }
    }
}

