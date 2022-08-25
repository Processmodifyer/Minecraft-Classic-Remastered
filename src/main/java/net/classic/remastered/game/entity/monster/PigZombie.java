/*
 * 
 */
package net.classic.remastered.game.entity.monster;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.LivingEntity;
import net.classic.remastered.game.entity.monster.ai.base.goal.NeutralAI;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.tile.Block;

public class PigZombie
extends LivingEntity {
    public PigZombie(World var1, float var2, float var3, float var4) {
        super(var1);
        this.setPos(var2, var3, var4);
        this.modelName = "zombie";
        this.textureName = "/mob/zombiepigman.png";
        this.heightOffset = 1.62f;
        NeutralAI var5 = new NeutralAI();
        this.deathScore = 80;
        var5.defaultLookAngle = 30;
        var5.runSpeed = 1.0f;
        this.ai = var5;
    }

    @Override
    public void hurt(Entity var1, int var2) {
        this.level.playSound("mob.zombiepig.zpighurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        super.hurt(var1, var2);
    }

    @Override
    public void die(Entity var1) {
        this.level.playSound("random.zpigdeath", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Item.FEATHER.id));
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Block.GOLD_BLOCK.id));
        }
        super.die(var1);
    }
}

