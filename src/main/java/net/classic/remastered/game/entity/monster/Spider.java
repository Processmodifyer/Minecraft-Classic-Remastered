/*
 * 
 */
package net.classic.remastered.game.entity.monster;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.creature.QuadrupedMob;
import net.classic.remastered.game.entity.monster.ai.base.goal.JumpAttackAI;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;

public class Spider
extends QuadrupedMob {

    private int firstrun;

    public Spider(World var1, float var2, float var3, float var4) {
        super(var1, var2, var3, var4);
        this.heightOffset = 0.72f;
        this.modelName = "spider";
        this.textureName = "/mob/spider.png";
        this.setSize(1.4f, 0.9f);
        this.setPos(var2, var3, var4);
        this.deathScore = 105;
        this.bobStrength = 0.0f;
        this.ai = new JumpAttackAI();
    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (this.health <= 0) {
            this.level.playSound("mob.spiderdeath", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        if (this.health >= 0) {
            this.level.playSound("mob.spiderhurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
            super.hurt(var1, var2);
        }
    }

    @Override
    public void die(Entity var1) {
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
            this.level.setTile((int)this.x, (int)this.y + this.firstrun, (int)this.z, Block.WEB.id);
            ++this.firstrun;
        }
        super.die(var1);
    }
}

