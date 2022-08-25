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

public class Endermen
extends LivingEntity {
    public Endermen(World var1, float var2, float var3, float var4) {
        super(var1);
        this.modelName = "endermen";
        this.textureName = "/mob/enderman.png";
        this.heightOffset = 1.62f;
        this.setPos(var2, var3, var4);
        NeutralAI var5 = new NeutralAI();
        this.deathScore = 80;
        var5.defaultLookAngle = 30;
        var5.runSpeed = 1.6f;
        this.ai = var5;
        this.health = 40;
    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (this.health <= 0) {
            this.level.playSound("mob.endermen.death", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        if (this.health >= 0) {
            this.level.playSound("mob.endermen.hit", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        super.hurt(var1, var2);
    }

    @Override
    public void die(Entity var1) {
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Item.FEATHER.id));
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Block.GOLD_BLOCK.id));
        }
        super.die(var1);
    }
}

