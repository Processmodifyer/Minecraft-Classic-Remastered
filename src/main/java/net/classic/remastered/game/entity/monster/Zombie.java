/*
 * 
 */
package net.classic.remastered.game.entity.monster;

import net.classic.remastered.game.entity.Entity;
import net.classic.remastered.game.entity.creature.HumanoidMob;
import net.classic.remastered.game.entity.monster.ai.base.BasicAttackAI;
import net.classic.remastered.game.entity.other.NonLivingEntity;
import net.classic.remastered.game.player.InputHandlerImpl;
import net.classic.remastered.game.world.World;
import net.classic.remastered.game.world.item.Item;
import net.classic.remastered.game.world.tile.Block;

public class Zombie
extends HumanoidMob {

    public InputHandlerImpl inputpl;

    public Zombie(World var1, float var2, float var3, float var4) {
        super(var1, var2, var3, var4);
        this.modelName = "zombie";
        this.textureName = "/mob/zombie.png";
        this.heightOffset = 1.62f;
        BasicAttackAI var5 = new BasicAttackAI();
        this.deathScore = 80;
        var5.defaultLookAngle = 30;
        var5.runSpeed = 1.0f;
        this.ai = var5;
    }

    @Override
    public void hurt(Entity var1, int var2) {
        if (this.health <= 0) {
            this.level.playSound("mob.zombiedeath", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        if (this.health >= 0) {
            this.level.playSound("mob.zombiehurt", this.x, this.y, this.z, 0.8f + (float)Math.random() / 4.0f, 1.0f);
        }
        super.hurt(var1, var2);
    }

    @Override
    public void die(Entity var1) {
        int var2 = (int)(Math.random() + Math.random() + 1.0);
        for (int var3 = 0; var3 < var2; ++var3) {
            this.level.addEntity(new NonLivingEntity(this.level, this.x, this.y, this.z, Item.FEATHER.id));
        }
        super.die(var1);
    }
}

