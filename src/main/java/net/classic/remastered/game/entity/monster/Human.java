/*
 * 
 */
package net.classic.remastered.game.entity.monster;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.creature.HumanoidMob;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.tile.Block;

public class Human
extends HumanoidMob {
    public Human(World var1, float var2, float var3, float var4) {
        super(var1, var2, var3, var4);
        this.health = 20;
    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (this.health <= 0) {
            this.level.playSound("random.hurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        if (this.health >= 0) {
            this.level.playSound("random.hurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        super.hurt(var1, var2);
    }

    @Override
    public void die(Entity var1) {
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
        }
        super.die(var1);
    }
}

