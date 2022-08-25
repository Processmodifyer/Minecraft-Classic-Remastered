/*
 * 
 */
package net.classic.remastered.game.entity.creature;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.world.World;

public class Cow
extends QuadrupedMob {
    public Cow(World var1, float var2, float var3, float var4) {
        super(var1, var2, var3, var4);
        this.heightOffset = 1.72f;
        this.modelName = "cow";
        this.textureName = "/mob/cow.png";
        this.health = 10;

    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (this.health <= 0) {
            this.level.playSound("mob.cow", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        if (this.health >= 0) {
            this.level.playSound("mob.cowhurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
            super.hurt(var1, var2);
        }
    }
}

